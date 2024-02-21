package com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPosition;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "job_filters")
@Data
public class JobFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    @ManyToOne
    @JoinColumn(name = "job_position_id")
    private JobPosition jobPositionId;
    private String jobSite;
    private String filterUrl;
}
