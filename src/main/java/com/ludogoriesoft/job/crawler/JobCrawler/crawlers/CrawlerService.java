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

//    @Scheduled(cron = "* 42 * * * *")
//    public void crawlDevBG() {
//        crawlDevBg();
//    }
//
    private void crawlDevBg() {
        try {
            devBgCrawler4JService.crawl();
        } catch (Exception e) {
            log.error("Cannot crawl dev.bg");
        }
    }
    @Scheduled(cron = "* 4 * * * *")
    public void crawl() {
        crawlDevBg();
        crawlDice();
    }

    private void crawlDice() {
        try {
            diceCrawlerService.crawl();
        } catch (Exception e) {
            log.error("Cannot crawl dice.com");
        }
    }
}