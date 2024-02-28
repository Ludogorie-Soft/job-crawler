package com.ludogoriesoft.job.crawler.JobCrawler.crawlers.devBG;

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
public class DevBGExtractor implements Extractor {
    @Override
    public String extractCompanyName(String html) {
        Document document = Jsoup.parse(html);

        Elements companyNameElements = document.select(".company-name");

        if (!companyNameElements.isEmpty()) {
            return companyNameElements.get(0).text();
        }
        return null;
    }

    public String extractCompanyUrl(String html) {
        Document doc = Jsoup.parse(html);

        // Select anchor elements with class "company-logo-link"
        Elements anchors = doc.select("a.company-logo-link");
        if (!anchors.isEmpty()) {
            return anchors.get(0).attr("href");
        }
        return null;
    }

    @Override
    public List<String> extractTechStack(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select(".component-square-badge.has-image");

        List<String> techStack = new ArrayList<>();
        // Iterate through the selected elements and extract titles
        for (Element element : elements) {
            // Extract title attribute from the image tag
            String title = element.select("img").attr("title");
            techStack.add(title);
        }

        return techStack;
    }

    @Override
    public String extractDatePosted(String html) {
        Document document = Jsoup.parse(html);
        Element timeElement = document.select("li.date-posted time").first();

        // Extract the value of the datetime attribute
        if (timeElement != null) {
            return timeElement.attr("datetime");
        }
        return null;
    }
}