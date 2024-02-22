package com.ludogoriesoft.job.crawler.JobCrawler.jobad.controller;

import com.ludogoriesoft.job.crawler.JobCrawler.jobad.service.JobAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/job-ad")
@RequiredArgsConstructor
public class JobAdController {

    private final JobAdService service;

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.deleteById(id);
    }
}
