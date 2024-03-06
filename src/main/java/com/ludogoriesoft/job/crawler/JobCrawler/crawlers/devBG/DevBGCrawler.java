package com.ludogoriesoft.job.crawler.JobCrawler.crawlers.devBG;

import com.ludogoriesoft.job.crawler.JobCrawler.company.model.CompanyDto;
import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.Company;
import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.CompanyStatus;
import com.ludogoriesoft.job.crawler.JobCrawler.company.service.CompanyService;
import com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.service.CompanyPlatformAssociationService;
import com.ludogoriesoft.job.crawler.JobCrawler.crawlers.Extractor;
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
import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class DevBGCrawler extends WebCrawler {
    private final JobFilter jobFilter;
    private final JobPosition jobPosition;
    private final List<DetailedFilter> detailedFilterList;
    private final Extractor devBGExtractor;
    private final CompanyService companyService;
    private final JobAdService jobAdService;
    private final CompanyPlatformAssociationService companyPlatformAssociationService;

    private final static String STARTS_WITH_PATTERN = "https://dev.bg/company/jobads/";

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp4|zip|gz))$");
    private final static String REGION = "BULGARIA";


    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String urlString = url.getURL();
        // Check if this ad is already processed
        if (shouldAdProcessed(urlString)) {
            return false;
        }
        // Check for an exact match with the STARTS_WITH_PATTERN and FILTERS
        return !FILTERS.matcher(urlString).matches() && urlString.startsWith(STARTS_WITH_PATTERN);
    }

    private boolean shouldAdProcessed(String urlString) {
        // Retrieve all processed ads
        List<JobAd> allAds = jobAdService.list();

        // Check for an exact match with the desired URL
        return allAds.stream().anyMatch(ja -> urlString.equals(ja.getJobAdUrl()));
    }

    @Override
    public void visit(Page page) {
        if (page.getParseData() instanceof HtmlParseData htmlParseData && !page.getWebURL().toString().contains("paged=")) {
            String html = htmlParseData.getHtml();
            String url = String.valueOf(page.getWebURL());
//            create company if it doesn't exist or add platform association if it exists
            processCompany(html);
//            process add
            processAd(html, url);

        }
    }

    private void processAd(String html, String url) {
        String companyName = devBGExtractor.extractCompanyName(html);
        List<String> techStack = devBGExtractor.extractTechStack(html);
        String postDate = devBGExtractor.extractDatePosted(html);

        Optional<CompanyDto> companyDto = companyService.getCompanyByName(companyName);
        CompanyStatus companyStatus = companyDto.get().getCompanyStatus();

        JobAdStatus jobAdStatus = JobAdStatus.ACTIVE;

        if (companyStatus != null && companyStatus.equals(CompanyStatus.NO_B2B)) {
            logger.info("Invalid ad {}, because company status", url);
            jobAdStatus = JobAdStatus.INVALID;
            // save job ad
            jobAdService.save(jobAdStatus, jobFilter, url, jobFilter.getJobSite(), REGION, postDate);
            return;
        }

        // check detailed filters blacklist
        for (String technology : techStack) {
            for (DetailedFilter df : detailedFilterList) {
                if (!df.getIsWhitelist()) {
                    if (df.getFilter().equalsIgnoreCase(technology)) {
                        logger.info("Invalid ad {}, because ad tech stack contains {}", url, df.getFilter());
                        jobAdStatus = JobAdStatus.INVALID;
                        jobAdService.save(jobAdStatus, jobFilter, url, jobFilter.getJobSite(), REGION, postDate);
                        return;
                    }
                }
            }
        }

        // save job ad
        jobAdService.save(jobAdStatus, jobFilter, url, jobFilter.getJobSite(), REGION, postDate);

    }

    private void processCompany(String html) {
        String companyName = devBGExtractor.extractCompanyName(html);
        String companyUrl = devBGExtractor.extractCompanyUrl(html);

        // if company does not exist -> save it
        if (companyService.getCompanyByName(companyName).isEmpty()) {
            // save company
            CompanyDto companyDto = new CompanyDto();
            companyDto.setName(companyName);
            companyService.save(companyDto);
        }

        // add platform association
        if (companyUrl != null) {
            Optional<CompanyDto> companyDto = companyService.getCompanyByName(companyName);
            if (companyDto.isPresent() && !companyDto.get().getPlatformAssociations().contains(companyUrl)) {
                companyDto.get().getPlatformAssociations().add(companyUrl);
                companyService.updateCompanyById(companyDto.get());
            }
        }

    }
}
