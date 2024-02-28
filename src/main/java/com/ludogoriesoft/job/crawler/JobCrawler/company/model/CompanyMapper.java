package com.ludogoriesoft.job.crawler.JobCrawler.company.model;

import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.Company;

public interface CompanyMapper {
    CompanyDto entityToDto(Company entity);

    Company dtoToEntity(CompanyDto dto);
}
