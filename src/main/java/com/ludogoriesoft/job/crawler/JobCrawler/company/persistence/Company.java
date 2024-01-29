package com.ludogoriesoft.job.crawler.JobCrawler.company.persistence;

import com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.persistence.CompanyPlatformAssociation;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "companies")
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String websiteUrl;
    @OneToMany
    private List<CompanyPlatformAssociation> platformAssociations;
    @Enumerated(EnumType.STRING)
    private CompanyStatus status;
}
