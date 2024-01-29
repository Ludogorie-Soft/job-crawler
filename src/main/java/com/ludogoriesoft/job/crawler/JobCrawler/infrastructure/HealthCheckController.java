package com.ludogoriesoft.job.crawler.JobCrawler.infrastructure;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/admin/healthcheck")
    public void get() {

    }
}
