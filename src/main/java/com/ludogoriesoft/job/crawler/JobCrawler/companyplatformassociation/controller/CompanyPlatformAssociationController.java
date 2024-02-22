package com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.controller;

import com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.service.CompanyPlatformAssociationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/platform")
@RequiredArgsConstructor
public class CompanyPlatformAssociationController {
    private final CompanyPlatformAssociationService service;

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        service.delete(id);
    }
}
