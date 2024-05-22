package com.ludogoriesoft.job.crawler.JobCrawler.crawlers;

import com.ludogoriesoft.job.crawler.JobCrawler.crawlers.devBG.DevBGCrawlerService;
import com.ludogoriesoft.job.crawler.JobCrawler.crawlers.dice.DiceCrawlerService;
import com.ludogoriesoft.job.crawler.JobCrawler.crawlers.freelancermap.FreelancermapCrawlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class CrawlerService {
    private final DevBGCrawlerService devBgCrawler4JService;
    private final DiceCrawlerService diceCrawlerService;
    private final FreelancermapCrawlerService freelancermapCrawlerService;

    // @Scheduled(cron = "0 0 9 ? * MON") // For running a task once a week - every Monday at 9:00 AM
//    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Every 24 hours
//    @Async
    public void crawl() {
        crawlDevBg();
        crawlDice();
        crawlFreelancerMap();
    }

    @Async
    public void crawlDevBg() {
        try {
            devBgCrawler4JService.crawl();
        } catch (Exception e) {
            log.error("Cannot crawl dev.bg");
        }
    }

    @Async
    public void crawlDice() {
        try {
            diceCrawlerService.crawl();
        } catch (Exception e) {
            log.error("Cannot crawl dice.com");
        }
    }

    @Async
    public void crawlFreelancerMap() {
        try {
            freelancermapCrawlerService.crawl();
        } catch (Exception e) {
            log.error("Cannot crawl freelancermap.ie");
        }
    }
}