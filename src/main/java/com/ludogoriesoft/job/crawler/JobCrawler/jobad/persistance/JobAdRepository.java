package com.ludogoriesoft.job.crawler.JobCrawler.jobad.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobAdRepository extends JpaRepository<JobAd, Long> {
    Optional<JobAd> findByJobAdUrl(String jobAdUrl);
}
