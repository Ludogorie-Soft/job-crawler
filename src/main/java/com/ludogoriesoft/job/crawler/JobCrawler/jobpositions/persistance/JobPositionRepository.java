package com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {
}
