package com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.controller;

import com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance.JobFilter;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.model.JobPositionDto;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPosition;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.service.JobPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/job-position")
@RequiredArgsConstructor
public class JobPositionController {

    private final JobPositionService service;

    //    create
    @GetMapping("/create")
    public String createJobPosition(Model model) {
        JobPositionDto jobPositionDto = new JobPositionDto();
        List<JobFilter> jobFilterList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            jobFilterList.add(new JobFilter());
        }
        jobPositionDto.setJobFilterList(jobFilterList);
        model.addAttribute("job", jobPositionDto);
        return "job-position-create";

    }

    @PostMapping("/save")
    public String create(@ModelAttribute JobPositionDto dto) {
        service.save(dto);
        return "redirect:/job-position/all";
    }

    // list
    @GetMapping("/all")
    public String list(Model model) {
        model.addAttribute("jobPositions", service.list());
        return "job-positions";
    }

    //    delete
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/job-position/all";
    }

    // update - change status & add or delete detailed filter & add or delete job filter
    @GetMapping("/{id}")
    public String getJobPositionById(@PathVariable Long id, Model model) {
        JobPositionDto jobPositionDto = service.getJobPositionById(id);
        jobPositionDto.getJobFilterList().add(new JobFilter());
        model.addAttribute("jobPositionDto", jobPositionDto);
        return "job-position";
    }

    @PostMapping("/update")
    public String updateJobPosition(@ModelAttribute JobPositionDto jobPositionDto) {
        service.updateJobPosition(jobPositionDto);
        return "redirect:/job-position/all";
    }

//    todo: add, change jobFilter

}
