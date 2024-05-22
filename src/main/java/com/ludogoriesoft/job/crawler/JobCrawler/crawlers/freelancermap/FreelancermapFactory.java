package com.ludogoriesoft.job.crawler.JobCrawler.crawlers.freelancermap;

import com.ludogoriesoft.job.crawler.JobCrawler.company.service.CompanyService;
import com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.service.CompanyPlatformAssociationService;
import com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.persistance.DetailedFilter;
import com.ludogoriesoft.job.crawler.JobCrawler.jobad.service.JobAdService;
import com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance.JobFilter;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPosition;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FreelancermapFactory implements CrawlController.WebCrawlerFactory<WebCrawler> {
    private final JobFilter jobFilter;
    private final FreelancermapExtractor freelancermapExtractor;
    private final CompanyService companyService;
    private final JobAdService jobAdService;
    private final CompanyPlatformAssociationService companyPlatformAssociationService;
    private final JobPosition jobPosition;
    private final List<DetailedFilter> detailedFilterList;


    @Override
    public FreelancermapCrawler newInstance() {
        return new FreelancermapCrawler(jobFilter,
                jobPosition,
                detailedFilterList,
                freelancermapExtractor,
                companyService,
                jobAdService,
                companyPlatformAssociationService);
    }
}
