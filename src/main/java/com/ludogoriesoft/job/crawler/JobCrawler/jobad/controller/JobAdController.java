package com.ludogoriesoft.job.crawler.JobCrawler.jobad.controller;

import com.ludogoriesoft.job.crawler.JobCrawler.crawlers.CrawlerService;
import com.ludogoriesoft.job.crawler.JobCrawler.jobad.persistance.JobAdStatus;
import com.ludogoriesoft.job.crawler.JobCrawler.jobad.service.JobAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/job-ad")
@RequiredArgsConstructor
public class JobAdController {

    private final JobAdService service;
    private final CrawlerService crawlerService;

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("ads", service.list());
        model.addAttribute("statuses", JobAdStatus.values());
        return "job-ads";
    }

    @PostMapping("/update/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam("newStatus") String newStatus) {
        service.updateStatus(id, newStatus);
        return "redirect:/job-ad/all";
    }

    @PostMapping("/manualCrawl")
    public String crawl(@RequestParam("site") String site) {
        if ("devbg".equals(site)) {
            crawlerService.crawlDevBg();
        } else if ("dice".equals(site)) {
            crawlerService.crawlDice();
        } else if ("freelancermap".equals(site)) {
            crawlerService.crawlFreelancerMap();
        }
        return "redirect:/job-position/all";
    }
}
