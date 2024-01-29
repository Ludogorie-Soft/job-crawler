package com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobFilterRepository extends JpaRepository<JobFilter, Long> {
}
