package com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance.JobFilter;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPositionStatus;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPositionDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull(message = "Field must not be null.")
    private String name;
    @Nullable
    private List<String> whitelistFilters;
    @Nullable
    private List<String> blacklistFilters;
    private JobPositionStatus status;
    private List<JobFilter> jobFilterList;
}
