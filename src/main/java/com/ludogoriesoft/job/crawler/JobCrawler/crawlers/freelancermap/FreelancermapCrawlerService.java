package com.ludogoriesoft.job.crawler.JobCrawler.crawlers.freelancermap;

import com.ludogoriesoft.job.crawler.JobCrawler.company.service.CompanyService;
import com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.service.CompanyPlatformAssociationService;
import com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.persistance.DetailedFilter;
import com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.persistance.DetailedFilterRepository;
import com.ludogoriesoft.job.crawler.JobCrawler.jobad.service.JobAdService;
import com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance.JobFilter;
import com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance.JobFilterRepository;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPosition;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FreelancermapCrawlerService {
    private final FreelancermapExtractor freelancermapExtractor;
    private final JobFilterRepository jobFilterRepository;
    private final DetailedFilterRepository detailedFilterRepository;
    private final CompanyService companyService;
    private final CompanyPlatformAssociationService companyPlatformAssociationService;
    private final JobAdService jobAdService;
    private static final String JOB_SITE = "freelancermap.com";

    public void crawl() throws Exception {
        List<JobFilter> jobFilterList = jobFilterRepository.findAllByJobSite(JOB_SITE);
        if (!jobFilterList.isEmpty()) {
            log.info("Start crawling freelancermap.com");
            for (JobFilter jobFilter : jobFilterList) {
                String baseUrl = jobFilter.getFilterUrl();
                log.info("BASE URL " + baseUrl);
                JobPosition jobPosition = jobFilter.getJobPositionId();
                List<DetailedFilter> detailedFilterList = detailedFilterRepository.findAllByJobPositionId(jobPosition.getId());
                processCrawl(jobFilter, baseUrl, jobPosition, detailedFilterList);
            }
            log.info("End crawling freelancermap.com");
        }
    }

    private void processCrawl(JobFilter jobFilter, String baseUrl,
                              JobPosition jobPosition,
                              List<DetailedFilter> detailedFilterList) throws Exception {
        File crawlStorage = new File("src/test/resources/crawler4j");
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorage.getAbsolutePath());

        int numCrawlers = 1;
        int maxDepthOfCrawling = 2; // Set the maximum depth of crawling
        config.setMaxDepthOfCrawling(maxDepthOfCrawling);

        CrawlController controller = getCrawlController(baseUrl, config);


        controller.start(
                new FreelancermapFactory(jobFilter,
                        freelancermapExtractor,
                        companyService,
                        jobAdService,
                        companyPlatformAssociationService,
                        jobPosition,
                        detailedFilterList), numCrawlers);
    }

    private static CrawlController getCrawlController(String baseUrl, CrawlConfig config) throws Exception {
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        int totalPages = 1; // Set the total number of pages you want to crawl

//        for (int i = 1; i <= totalPages; i++) {
//            String seedUrl = appendPageNumber(baseUrl, i);
            controller.addSeed(baseUrl);
            log.info(baseUrl);
       // }

        return controller;
    }

    // Helper method to append page number to base URL
    private static String appendPageNumber(String baseUrl, int pageNumber) {
        // Replace the existing page parameter value with the new page number
        return baseUrl + "?page=" + pageNumber;
    }
}
