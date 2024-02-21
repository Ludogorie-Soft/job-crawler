package com.ludogoriesoft.job.crawler.JobCrawler.selenium;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;




@RestController
@RequiredArgsConstructor
public class WebScrapperController {

    private final WebScraper webScraper;

    @GetMapping("/scrap")
    public String scrapSite() throws IOException, InterruptedException {
        webScraper.scrapDiceSite();
        return "Scrapped ?";
    }

//    @GetMapping("/contains")
//    public boolean testContains(){
//        return testIt();
//    }
}
