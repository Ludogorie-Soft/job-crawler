package com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.persistance;

import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPosition;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "detailed_filters")
@Data
public class DetailedFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "job_position_id")
    private JobPosition jobPositionId;
    private String filter;
}
