package com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.controller;

import com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.service.DetailedFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/detailed-filter")
@RequiredArgsConstructor
public class DetailedFilterController {
    private final DetailedFilterService service;

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByID(@PathVariable Long id){
        service.delete(id);
    }

}
