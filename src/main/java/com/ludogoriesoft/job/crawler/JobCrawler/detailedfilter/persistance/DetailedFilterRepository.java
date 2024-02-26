package com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DetailedFilterRepository extends JpaRepository<DetailedFilter, Long> {
    @Query("SELECT df FROM  DetailedFilter df WHERE df.filter = :filterUrl AND df.jobPositionId.id = :jobPositionId")
    Optional<DetailedFilter> findByFilterUrlAndJobPositionId(String filterUrl, Long jobPositionId);

    @Query("SELECT df FROM DetailedFilter df WHERE df.jobPositionId.id = :jobPositionId")
    List<DetailedFilter> findAllByJobPositionId(Long jobPositionId);
}
