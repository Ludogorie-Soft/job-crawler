package com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.persistance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPosition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "detailed_filters")
@Getter
@Setter
public class DetailedFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "job_position_id")
    @JsonIgnore
    private JobPosition jobPositionId;
    private String filter;
    private Boolean isWhitelist;

    @Override
    public String toString() {
        return "DetailedFilter{" +
                "id=" + id +
                ", filter='" + filter + '\'' +
                ", isWhitelist=" + isWhitelist +
                '}';
    }
}
