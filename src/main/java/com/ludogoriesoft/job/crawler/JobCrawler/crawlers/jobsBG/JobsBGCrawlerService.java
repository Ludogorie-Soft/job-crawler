package com.ludogoriesoft.job.crawler.JobCrawler.crawlers.jobsBG;

import com.ludogoriesoft.job.crawler.JobCrawler.chromedriver.ChromeDriverBuilder;
import com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.persistance.DetailedFilter;
import com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.persistance.DetailedFilterRepository;
import com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance.JobFilter;
import com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance.JobFilterRepository;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPosition;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class JobsBGCrawlerService {
    private final JobFilterRepository jobFilterRepository;
    private final DetailedFilterRepository detailedFilterRepository;
//    private final ChromeDriver chromeDriver;
    private static final String JOB_SITE = "jobs.bg";

    public void crawl(){
        List<JobFilter> jobFilterList = jobFilterRepository.findAllByJobSite(JOB_SITE);
        if (!jobFilterList.isEmpty()) {
            log.info("Start crawling jobs.bg");
            for (JobFilter jobFilter : jobFilterList) {
                String baseUrl = jobFilter.getFilterUrl();
                JobPosition jobPosition = jobFilter.getJobPositionId();
                List<DetailedFilter> detailedFilterList = detailedFilterRepository.findAllByJobPositionId(jobPosition.getId());
                processCrawl(jobFilter, baseUrl, jobPosition, detailedFilterList);
            }
            log.info("End crawling jobs.bg");
        }
    }

    private void processCrawl(JobFilter jobFilter, String baseURL, JobPosition jobPosition, List<DetailedFilter> detailedFilter){
        // todo: add the chrome driver in root directory
        String driver_home = "C:\\Users\\Acer\\Downloads\\chrome-win64\\chrome-win64\\chrome.exe";

        // 1  if use chromeOptions, recommend use this
        // ChromeDriverBuilder could throw RuntimeError, you can catch it, *catch it is unnecessary
        ChromeOptions chrome_options = new ChromeOptions();
        chrome_options.addArguments("--window-size=1920,1080");
        //chrome_options.addArguments("--headless=new"); when chromedriver > 108.x.x.x
//        chrome_options.addArguments("--headless=chrome"); //when chromedriver <= 108.x.x.x

        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(driver_home))
                .usingAnyFreePort()
                .build();

        //ChromeDriver chromeDriver1 = new ChromeDriver(service);
        ChromeDriver chromeDriver = new ChromeDriverBuilder()
                .build(chrome_options,driver_home);

        chromeDriver.get("https://www.jobs.bg/");

        return;
    }

}
