package com.ludogoriesoft.job.crawler.JobCrawler.company.controller;

import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.CompanyStatus;
import com.ludogoriesoft.job.crawler.JobCrawler.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;

    @PatchMapping("/{id}")
    public void updateCompanyStatus(@PathVariable Long id, @RequestParam CompanyStatus status){
        companyService.updateCompanyStatusById(id, status);
    }

    @GetMapping("/all")
    public String listCompanies(Model model){
        model.addAttribute("companies", companyService.list());
        return "home";
    }
}
