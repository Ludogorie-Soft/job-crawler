package com.ludogoriesoft.job.crawler.JobCrawler.crawlers.freelancermap;

import com.ludogoriesoft.job.crawler.JobCrawler.crawlers.Extractor;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FreelancermapExtractor implements Extractor {
    @Override
    public String extractCompanyName(String html) {
        Document document = Jsoup.parse(html);
        Element companyNameElement = document.selectFirst("dl.m-t-1 dt:contains(From) + dd span[itemprop=name]");


        if (companyNameElement != null) {
            return companyNameElement.text();
        }

        return null;
    }

    public List<String> extractTechStack(String html) {
        Document doc = Jsoup.parse(html);
        Elements keywordElements = doc.select("div.keywords-container.content span.keyword");

        List<String> techStack = new ArrayList<>();

        for (Element keywordElement : keywordElements) {
            techStack.add(keywordElement.text());
        }

        return techStack;
    }

    public String extractDatePosted(String html) {
        Document document = Jsoup.parse(html);
        Element dateElement = document.selectFirst(".project-detail-description[itemprop=datePosted]");

        if (dateElement != null) {
            return dateElement.text();
        }

        return null;
    }

    public String extractRegion(String html) {
        Document doc = Jsoup.parse(html);
        Element locationElement = doc.selectFirst("div.location span.address span");

        if (locationElement != null) {
            return locationElement.text();
        }

        return null;
    }

    public String extractCompanyUrl(String html) {
        Document document = Jsoup.parse(html);
        Element companyNameElement = document.selectFirst("#jobdetails a[data-cy='companyNameLink']");

        if (companyNameElement != null) {
            return companyNameElement.attr("href");
        }

        return null;
    }
}