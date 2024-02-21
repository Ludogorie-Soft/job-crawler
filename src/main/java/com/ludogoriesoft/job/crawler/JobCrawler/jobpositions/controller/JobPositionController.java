package com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.controller;

import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.model.JobPositionDto;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.service.JobPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class JobPositionController {

    private final JobPositionService service;

    @PostMapping("/create/job-position")
    public void create(@Validated @RequestBody JobPositionDto dto){
        service.save(dto);
    }

    @DeleteMapping("/job-position/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}
