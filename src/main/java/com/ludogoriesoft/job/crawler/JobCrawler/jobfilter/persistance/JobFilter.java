package com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ludogoriesoft.job.crawler.JobCrawler.jobad.persistance.JobAd;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPosition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "job_filters")
@Getter
@Setter
@NoArgsConstructor
public class JobFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "jobFilterId", cascade = CascadeType.REMOVE)
    private List<JobAd> jobAds;
    @ManyToOne
    @JoinColumn(name = "job_position_id")
    @JsonIgnore
    private JobPosition jobPositionId;
    private String jobSite;
    private String filterUrl;

    @Override
    public String toString() {
        return jobPositionId.getName();
    }
}
