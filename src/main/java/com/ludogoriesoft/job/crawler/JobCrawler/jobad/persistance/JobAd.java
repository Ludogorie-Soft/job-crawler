package com.ludogoriesoft.job.crawler.JobCrawler.jobad.persistance;

import com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance.JobFilter;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_ads")
@Data
public class JobAd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "job_filter_id")
    private JobFilter jobFilterId;
    private String jobAdUrl;
    private String source;
    private String region;
    private String postDate;
    private String lastUpdated;
    @Enumerated(EnumType.STRING)
    private JobAdStatus status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
