package com.ludogoriesoft.job.crawler.JobCrawler.crawlers.dice;

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
public class DiceExtractor implements Extractor {
    @Override
    public String extractCompanyName(String html) {
        Document document = Jsoup.parse(html);
        Element companyNameElement = document.selectFirst("#jobdetails a[data-cy='companyNameLink']");

        if (companyNameElement != null) {
            return companyNameElement.text();
        }

        return null;
    }

    public boolean isRemote(String html) {
        Document document = Jsoup.parse(html);
        Elements locationDetailsElement = document.select("div[data-cy='locationDetails'] span");

        for (Element element : locationDetailsElement) {
            boolean isRemote = element.text().toLowerCase().contains("remote");

            if (isRemote) {
                System.out.println("REMOTE");
                return true;
            }
        }

        return false;
    }

    public boolean hasContract(String html) {
        Document document = Jsoup.parse(html);
        Elements employmentDetailsElements = document.select("div[data-cy='employmentDetails'] span");

        for (Element element : employmentDetailsElements) {
            boolean containsContract = element.text().toLowerCase().contains("contract");

            if (containsContract) {
                System.out.println("CONTRACT");
                return true;
            }
        }

        return false;
    }

    public String extractCompanyUrl(String html) {
        Document document = Jsoup.parse(html);

        // Select anchor elements with class "company-logo-link"
        Element companyNameElement = document.selectFirst("#jobdetails a[data-cy='companyNameLink']");

        if (companyNameElement != null) {
            return companyNameElement.attr("href");
        }

        return null;
    }

    @Override
    public List<String> extractTechStack(String html) {
        Document doc = Jsoup.parse(html);
        Elements techElements = doc.select("div[data-cy='skillsList'] div.chip_chip__cYJs6 span");

        List<String> techStack = new ArrayList<>();

        for (Element techElement : techElements) {
            techStack.add(techElement.text());
        }

        return techStack;
    }

    @Override
    public String extractDatePosted(String html) {
        Document document = Jsoup.parse(html);
        Element timeElement = document.selectFirst("#timeAgo");

        if (timeElement != null) {
            return timeElement.text();
        }

        return null;
    }
}