package com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance;

import com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.persistance.DetailedFilter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "job_positions")
@Data
public class JobPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "jobPositionId", cascade = CascadeType.REMOVE)
    private List<DetailedFilter> detailedFilters;
}
