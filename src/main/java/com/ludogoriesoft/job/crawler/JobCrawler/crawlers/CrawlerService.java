package com.ludogoriesoft.job.crawler.JobCrawler.crawlers;

import com.ludogoriesoft.job.crawler.JobCrawler.crawlers.devBG.DevBGCrawlerService;
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

    @Scheduled(cron = "* 22 * * * *") // every 1 minute
    public void crawl() {
        crawlDevBg();
    }

    private void crawlDevBg() {
        try {
            devBgCrawler4JService.crawl();
        } catch (Exception e) {
            log.error("Cannot crawl dev.bg");
        }
    }
}