package com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance;

import com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.persistance.DetailedFilter;
import com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance.JobFilter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "job_positions")
@Getter
@Setter
public class JobPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "jobPositionId", cascade = CascadeType.REMOVE)
    private List<DetailedFilter> detailedFilters;
    @OneToMany(mappedBy = "jobPositionId", cascade = CascadeType.REMOVE)
    private List<JobFilter> jobFilters;

    @Override
    public String toString() {
        return "JobPosition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", detailedFilters=" + detailedFilters +
                ", jobFilters=" + jobFilters +
                '}';
    }
}
