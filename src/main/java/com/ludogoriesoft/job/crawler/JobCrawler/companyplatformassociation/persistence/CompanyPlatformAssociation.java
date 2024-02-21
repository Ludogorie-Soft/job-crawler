package com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.Company;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "platform_association")
@Data
public class CompanyPlatformAssociation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String platformUrl;
    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private Company company;
}
