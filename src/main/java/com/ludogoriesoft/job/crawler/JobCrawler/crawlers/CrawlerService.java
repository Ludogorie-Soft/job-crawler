package com.ludogoriesoft.job.crawler.JobCrawler.crawlers;

import com.ludogoriesoft.job.crawler.JobCrawler.crawlers.devBG.DevBGCrawlerService;
import com.ludogoriesoft.job.crawler.JobCrawler.crawlers.jobsBG.JobsBGCrawlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class CrawlerService {
    private final DevBGCrawlerService devBgCrawler4JService;
    private final JobsBGCrawlerService jobsBGCrawlerService;

    @Scheduled(cron = "* 14 * * * *") // every 1 minute
    public void crawl() {
        crawlJobsBg();
//        crawlDevBg();
    }

    private void crawlDevBg() {
        try {
            devBgCrawler4JService.crawl();
        } catch (Exception e) {
            log.error("Cannot crawl dev.bg");
        }
    }

    private  void crawlJobsBg(){
        try{
            jobsBGCrawlerService.crawl();
        } catch (Exception e){
            log.error("Cannot crawl jobs.bg");
        }

    }
}