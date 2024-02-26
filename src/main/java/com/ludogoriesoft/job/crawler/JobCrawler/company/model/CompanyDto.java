package com.ludogoriesoft.job.crawler.JobCrawler.company.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.CompanyStatus;
import com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.persistence.CompanyPlatformAssociation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull
    private String name;
    private String websiteUrl;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> platformAssociations;
    private CompanyStatus companyStatus;
}
