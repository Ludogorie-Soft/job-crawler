package com.ludogoriesoft.job.crawler.JobCrawler.company.model;

import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.Company;
import com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.persistence.CompanyPlatformAssociation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyMapperImpl implements CompanyMapper {
    @Override
    public CompanyDto entityToDto(Company entity) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(entity.getId());
        companyDto.setCompanyStatus(entity.getCompanyStatus());
        companyDto.setName(entity.getName());
        companyDto.setWebsiteUrl(entity.getWebsiteUrl());

        List<String> platformAssociations = entity.getPlatformAssociations()
                .stream()
                .map(CompanyPlatformAssociation::getPlatformUrl)
                .toList();

        companyDto.setPlatformAssociations(platformAssociations);
        return companyDto;
    }

    @Override
    public Company dtoToEntity(CompanyDto dto) {
        Company company = new Company();
        company.setWebsiteUrl(dto.getWebsiteUrl());
        company.setCompanyStatus(dto.getCompanyStatus());
        company.setName(dto.getName());

        return company;
    }
}
