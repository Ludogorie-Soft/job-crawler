package com.ludogoriesoft.job.crawler.JobCrawler.crawlers.freelancermap;

import com.ludogoriesoft.job.crawler.JobCrawler.company.service.CompanyService;
import com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.service.CompanyPlatformAssociationService;
import com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.persistance.DetailedFilter;
import com.ludogoriesoft.job.crawler.JobCrawler.jobad.persistance.JobAd;
import com.ludogoriesoft.job.crawler.JobCrawler.jobad.persistance.JobAdStatus;
import com.ludogoriesoft.job.crawler.JobCrawler.jobad.service.JobAdService;
import com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance.JobFilter;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPosition;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class FreelancermapCrawler extends WebCrawler {
    private final JobFilter jobFilter;

    private final JobPosition jobPosition;
    private final List<DetailedFilter> detailedFilterList;
    private final FreelancermapExtractor freelancermapExtractor;
    private final CompanyService companyService;
    private final JobAdService jobAdService;

    private final CompanyPlatformAssociationService companyPlatformAssociationService;

    private final static String JOB_DETAIL_URL = "https://www.freelancermap.com/project/";

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp4|zip|gz))$");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String urlString = url.getURL();

        // Check if this ad is already processed
        if (shouldAdProcessed(urlString)) {
            return false;
        }

        return (!FILTERS.matcher(urlString).matches() && urlString.startsWith(JOB_DETAIL_URL));
    }

    @Override
    public void visit(Page page) {
        if (page.getParseData() instanceof HtmlParseData htmlParseData && !page.getWebURL().toString().contains("paged=")) {
            String html = htmlParseData.getHtml();
            String url = String.valueOf(page.getWebURL());

            System.out.println(url);

            processAd(html, url);
        }
    }

    private boolean shouldAdProcessed(String urlString) {
        // Retrieve all processed ads
        List<JobAd> allAds = jobAdService.list();

        // Check for an exact match with the desired URL
        return allAds.stream().anyMatch(ja -> urlString.equals(ja.getJobAdUrl()));
    }


    private void processAd(String html, String url) {
        List<String> techStack = freelancermapExtractor.extractTechStack(html);
        String postDate = freelancermapExtractor.extractDatePosted(html);
        String region = freelancermapExtractor.extractRegion(html).toUpperCase();

        JobAdStatus jobAdStatus = JobAdStatus.ACTIVE;

        // check detailed filters blacklist
        for (String technology : techStack) {
            String lowercaseTechnology = technology.toLowerCase();
            for (DetailedFilter df : detailedFilterList) {
                if (!df.getIsWhitelist()) {
                    String lowercaseFilter = df.getFilter().toLowerCase();
                    if (lowercaseFilter.equals(lowercaseTechnology) || lowercaseTechnology.contains(lowercaseFilter)) {
                        logger.info("Invalid ad {}, because ad tech stack contains {}", url, df.getFilter());
                        jobAdStatus = JobAdStatus.INVALID;
                        jobAdService.save(jobAdStatus, jobFilter, url, jobFilter.getJobSite(), region, postDate);
                        return;
                    }
                }
            }
        }

        // save job ad
        jobAdService.save(jobAdStatus, jobFilter, url, jobFilter.getJobSite(), region, postDate);
    }
}
