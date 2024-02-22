package com.ludogoriesoft.job.crawler.JobCrawler.company.model;

import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.Company;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CompanyMapper {
    CompanyDto entityToDto(Company entity);

    Company dtoToEntity(CompanyDto dto);
}
