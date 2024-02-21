package com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.model;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class JobPositionDto {
    @NotNull(message = "Field must not be null.")
    private String name;
    @Nullable
    private List<String> filters;
}
