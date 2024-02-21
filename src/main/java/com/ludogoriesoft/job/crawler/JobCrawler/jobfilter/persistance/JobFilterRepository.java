package com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance;

import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JobFilterRepository extends JpaRepository<JobFilter, Long> {
    @Query("SELECT jf FROM JobFilter jf WHERE jf.jobPositionId.id = :jobPositionId AND jf.jobSite = :jobSite AND jf.filterUrl = :filterUrl")
    Optional<JobFilter> findJobFilterByJobPositionIdAndJobSiteAndFilterUrl(Long jobPositionId, String jobSite, String filterUrl);
}
