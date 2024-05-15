package com.ludogoriesoft.job.crawler.JobCrawler.crawlers;

import com.ludogoriesoft.job.crawler.JobCrawler.crawlers.devBG.DevBGCrawlerService;
import com.ludogoriesoft.job.crawler.JobCrawler.crawlers.dice.DiceCrawlerService;
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
    private final DiceCrawlerService diceCrawlerService;

    @Scheduled(cron = "0 0 9 ? * MON") // For running a task once a week - every Monday at 9:00 AM
    public void crawl() {
       crawlDevBg();
       crawlDice();
    }

    private void crawlDevBg() {
        try {
            devBgCrawler4JService.crawl();
        } catch (Exception e) {
            log.error("Cannot crawl dev.bg");
        }
    }

    private void crawlDice() {
        try {
            diceCrawlerService.crawl();
        } catch (Exception e) {
            log.error("Cannot crawl dice.com");
        }
    }
}
