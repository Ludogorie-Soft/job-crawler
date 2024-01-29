package com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "job_positions")
@Data
public class JobPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
