package com.ludogoriesoft.job.crawler.JobCrawler.jobad.controller;

import com.ludogoriesoft.job.crawler.JobCrawler.jobad.service.JobAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/job-ad")
@RequiredArgsConstructor
public class JobAdController {

    private final JobAdService service;

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("ads", service.list());
        return "job-ads";
    }
}
