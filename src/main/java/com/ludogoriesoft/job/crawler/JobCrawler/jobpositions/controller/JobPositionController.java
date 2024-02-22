package com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.controller;

import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.model.JobPositionDto;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.service.JobPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/job-position")
@RequiredArgsConstructor
public class JobPositionController {

    private final JobPositionService service;

    @PostMapping("/add")
    public void create(@Validated @RequestBody JobPositionDto dto){
        service.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @GetMapping("/all")
    public String list(Model model){
        model.addAttribute("jobPositions", service.list());
        return "home";
    }
}
