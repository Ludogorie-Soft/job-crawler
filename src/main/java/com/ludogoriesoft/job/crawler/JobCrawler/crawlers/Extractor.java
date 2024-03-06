package com.ludogoriesoft.job.crawler.JobCrawler.crawlers;

import org.apache.james.mime4j.dom.datetime.DateTime;

import java.util.List;

public interface Extractor {
    String extractCompanyName(String html);
    String extractCompanyUrl(String html);
    List<String> extractTechStack(String html);

    String extractDatePosted(String html);
}