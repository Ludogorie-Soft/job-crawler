package com.ludogoriesoft.job.crawler.JobCrawler.infrastructure;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class ApiDocController {

    @GetMapping(value = "/apidoc")
    public void get(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui/index.html");
    }

}
