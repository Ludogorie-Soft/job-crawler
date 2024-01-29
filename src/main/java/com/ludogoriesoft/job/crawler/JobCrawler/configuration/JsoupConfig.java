package com.ludogoriesoft.job.crawler.JobCrawler.configuration;

import org.jsoup.parser.Parser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsoupConfig {

    @Bean
    public Parser jsoupParser() {
        return Parser.xmlParser();
    }

}
