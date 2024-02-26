package com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.service;

import com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance.JobFilter;
import com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance.JobFilterRepository;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPosition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobFilterService {
    private final JobFilterRepository repository;

    public void save(JobPosition jobPosition, String jobSite, String filterUrl) {
        if (getJobFilter(jobPosition, jobSite, filterUrl).isEmpty()) {
            repository.save(createJobFilter(jobPosition, jobSite, filterUrl));
            log.info("Job filter was created in the db with url -> " + filterUrl);
        }
    }

    public List<JobFilter> list() {
        return repository.findAll();
    }

    public Optional<JobFilter> getJobFilter(JobPosition jobPosition, String jobSite, String filterUrl) {
        return repository.findJobFilterByJobPositionIdAndJobSiteAndFilterUrl(jobPosition.getId(), jobSite, filterUrl);
    }

    public void update(List<JobFilter> jobFilterList, JobPosition jobPosition) {
        for (JobFilter jf : jobFilterList) {
            if (jf.getId() != null) {
                repository.findById(jf.getId()).ifPresent(existingFilter -> {
                    String jobSite = jf.getJobSite();
                    String filterUrl = jf.getFilterUrl();
                    if (isNotBlank(jobSite) && isNotBlank(filterUrl)) {
                        existingFilter.setJobSite(jobSite);
                        existingFilter.setFilterUrl(filterUrl);
                        repository.save(existingFilter);
                        log.info("Job filter for position {} was updated from {}:{} to {}:{}",jobPosition.getName(), existingFilter.getJobSite(), existingFilter.getFilterUrl(), jf.getJobSite(), jf.getFilterUrl());
                    } else {
                        repository.deleteById(jf.getId());
                        log.info("Job filter {}:{} for position {} was deleted",jf.getJobSite(), jf.getFilterUrl(), jobPosition.getName());
                    }
                });
            } else {
                String jobSite = jf.getJobSite();
                String filterUrl = jf.getFilterUrl();
                if (isNotBlank(jobSite) && isNotBlank(filterUrl)) {
                    save(jobPosition, jobSite, filterUrl);
                }
            }
        }
    }

    private JobFilter createJobFilter(JobPosition jobPosition, String jobSite, String filterUrl) {
        JobFilter jobFilter = new JobFilter();
        jobFilter.setJobPositionId(jobPosition);
        jobFilter.setJobSite(jobSite);
        jobFilter.setFilterUrl(filterUrl);
        return jobFilter;
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.isEmpty();
    }

}
