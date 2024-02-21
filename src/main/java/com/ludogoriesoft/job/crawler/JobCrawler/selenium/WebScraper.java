package com.ludogoriesoft.job.crawler.JobCrawler.selenium;


import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ludogoriesoft.job.crawler.JobCrawler.util.JobOfferFilterUtil.isSuitableJobOffer;

@Service
@RequiredArgsConstructor
public class WebScraper {

    public void scrapDiceSite() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.dice.com/jobs?q=java&countryCode=US&radius=30&radiusUnit=mi&page=1&pageSize=100&filters.employmentType=CONTRACTS&filters.isRemote=true&language=en");

        Thread.sleep(6000);


        List<WebElement> jobElements =driver.findElements(By.xpath("/html/body/dhi-js-dice-client/div/dhi-search-page-container/dhi-search-page/div/dhi-search-page-results/div/div[3]/js-search-display/div/div[3]/dhi-search-cards-widget/div/dhi-search-card"));
        for (WebElement jobElement : jobElements) {
            String jobTitle = jobElement.getText();
            String jobIdLink = jobElement.findElement(By.tagName("a")).getAttribute("id");
            System.out.println(jobIdLink);

            String jobDetailLink= "https://www.dice.com/job-detail/"+jobIdLink+"?searchlink=search%2F%3Fq%3Djava%26countryCode%3DUS%26radius%3D30%26radiusUnit%3Dmi%26page%3D1%26pageSize%3D20%26filters.employmentType%3DCONTRACTS%26filters.isRemote%3Dtrue%26language%3Den";
            WebDriver jobDetail = new ChromeDriver();
            jobDetail.get(jobDetailLink);

            Thread.sleep(3);
//            List<WebElement> jobElements2 =jobDetail.findElements(By.xpath("/html/body/div[1]/div/main/div[3]/div/article/div[3]/div/section/div[1]/div"));
            WebElement jobElements2 =jobDetail.findElement(By.xpath("/html/body/div[1]/div/main/div[3]/div"));
//            for (WebElement webElement : jobElements2){
                WebElement overviewElement = jobElements2.findElement(By.xpath(".//div[contains(., 'Overview')]"));
                String overview = overviewElement.getText().toLowerCase();
                System.out.println(overview);
                WebElement jobDetailsElement = jobElements2.findElement(By.xpath(".//div[contains(., 'Job Details')]"));
                String description = jobDetailsElement.getText().toLowerCase();
                System.out.println(description);
                boolean suitable = isSuitableJobOffer(overview, description);
                if (suitable) {
                    System.out.println("WHOOREY THERE IS ONE SUITABLE JOB !!!");
                }
                System.out.println(suitable);
//            }
            jobDetail.quit();
//            for()
        }
        // Close the WebDriver instance
        driver.quit();

    }

}
