package com.ludogoriesoft.job.crawler.JobCrawler.crawlers.dice;

import com.ludogoriesoft.job.crawler.JobCrawler.company.model.CompanyDto;
import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.CompanyStatus;
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
import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class DiceCrawler extends WebCrawler {
    private final JobFilter jobFilter;

    private final JobPosition jobPosition;
    private final List<DetailedFilter> detailedFilterList;
    private final DiceExtractor diceExtractor;
    private final CompanyService companyService;
    private final JobAdService jobAdService;

    private final CompanyPlatformAssociationService companyPlatformAssociationService;

    private final static String JOB_DETAIL_URL = "https://www.dice.com/job-detail/";

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp4|zip|gz))$");
    private final static String REGION = "USA";

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

            if (url.startsWith(JOB_DETAIL_URL) && passFilterRequirements(html)) {
                processCompany(html);
                processAd(html, url);
            }
        }
    }

    private boolean shouldAdProcessed(String urlString) {
        // Retrieve all processed ads
        List<JobAd> allAds = jobAdService.list();

        // Check for an exact match with the desired URL
        return allAds.stream().anyMatch(ja -> urlString.equals(ja.getJobAdUrl()));
    }

    private boolean passFilterRequirements(String html) {
        return diceExtractor.hasContract(html) && diceExtractor.isRemote(html);
    }

    private void processAd(String html, String url) {
        String companyName = diceExtractor.extractCompanyName(html);
        List<String> techStack = diceExtractor.extractTechStack(html);
        String postDate = diceExtractor.extractDatePosted(html);

        if (companyName == null) {
            return;
        }

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
        String companyName = diceExtractor.extractCompanyName(html);
        String companyUrl = diceExtractor.extractCompanyUrl(html);
        if (companyName == null) {
            return;
        }

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
