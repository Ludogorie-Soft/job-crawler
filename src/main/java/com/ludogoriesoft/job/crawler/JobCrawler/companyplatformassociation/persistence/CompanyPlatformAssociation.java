package com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "platform_association")
@Getter
@Setter
public class CompanyPlatformAssociation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String platformUrl;
    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private Company company;

    @Override
    public String toString() {
        return "CompanyPlatformAssociation{" +
                "id=" + id +
                ", platformUrl='" + platformUrl + '\'' +
                '}';
    }
}
