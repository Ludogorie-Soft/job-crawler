package com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {
    Optional<JobPosition> findByName(String name);
}
