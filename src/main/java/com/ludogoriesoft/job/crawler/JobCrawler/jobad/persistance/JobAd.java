package com.ludogoriesoft.job.crawler.JobCrawler.jobad.persistance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance.JobFilter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import static com.ludogoriesoft.job.crawler.JobCrawler.constants.DataJsonFormat.DATA_FORMAT;

@Entity
@Table(name = "job_ads")
@Getter
@Setter
public class JobAd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "job_filter_id")
    @JsonIgnore
    private JobFilter jobFilterId;
    private String jobAdUrl;
    private String source;
    private String region;
    private String postDate;
    private String lastUpdated;
    @Enumerated(EnumType.STRING)
    private JobAdStatus status;
    @CreationTimestamp
    @JsonFormat(pattern =DATA_FORMAT)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @JsonFormat(pattern = DATA_FORMAT)
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "JobAd{" +
                "id=" + id +
                ", jobAdUrl='" + jobAdUrl + '\'' +
                ", source='" + source + '\'' +
                ", region='" + region + '\'' +
                ", postDate='" + postDate + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
