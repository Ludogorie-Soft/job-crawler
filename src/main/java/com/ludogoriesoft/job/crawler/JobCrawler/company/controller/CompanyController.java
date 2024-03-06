package com.ludogoriesoft.job.crawler.JobCrawler.company.controller;

import com.ludogoriesoft.job.crawler.JobCrawler.company.model.CompanyDto;
import com.ludogoriesoft.job.crawler.JobCrawler.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;

    //    list
    @GetMapping("/all")
    public String listCompanies(Model model) {
        model.addAttribute("companies", companyService.list());
        return "companies";
    }

    // update
    @GetMapping("/{id}")
    public String getCompanyById(@PathVariable Long id, Model model) {
        Optional<CompanyDto> companyDto = companyService.getCompanyById(id);
        model.addAttribute("companyDto", companyDto.get());
        return "company";
    }

    @PostMapping("/update")
    public String updateCompany(@ModelAttribute CompanyDto companyDto) {
        companyService.updateCompanyById(companyDto);

        return "redirect:/company/all";
    }

    // create
    @GetMapping("/create")
    public String createJobPosition(Model model) {
        model.addAttribute("company", new CompanyDto());
        return "company-create";
    }

    @PostMapping("/save")
    public String create(@Validated @ModelAttribute CompanyDto dto) {
        companyService.save(dto);
        return "redirect:/company/all";
    }

    //delete
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
       companyService.delete(id);
       return "redirect:/company/all";
    }

}
