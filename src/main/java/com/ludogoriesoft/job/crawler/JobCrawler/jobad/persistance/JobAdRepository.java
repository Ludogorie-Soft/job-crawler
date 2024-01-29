package com.ludogoriesoft.job.crawler.JobCrawler.jobad.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobAdRepository extends JpaRepository<JobAd, Long> {
}
