package com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.model;

import com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.persistance.DetailedFilter;
import com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance.JobFilter;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPosition;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPositionStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobPositionMapperImpl implements JobPositionMapper {
    @Override
    public JobPositionDto entityToDto(JobPosition entity) {
        JobPositionDto jobPositionDto = new JobPositionDto();
        jobPositionDto.setId(entity.getId());
        jobPositionDto.setName(entity.getName());
        jobPositionDto.setStatus(entity.getStatus());

        List<String> whitelist = entity.getDetailedFilters()
                .stream()
                .filter(DetailedFilter::getIsWhitelist)
                .map(DetailedFilter::getFilter)
                .toList();

        List<String> blacklist = entity.getDetailedFilters()
                .stream()
                .filter(detailedFilter -> !detailedFilter.getIsWhitelist())
                .map(DetailedFilter::getFilter)
                .toList();

        jobPositionDto.setWhitelistFilters(whitelist);
        jobPositionDto.setBlacklistFilters(blacklist);

        List<JobFilter> jobFilters = entity.getJobFilters();
        jobPositionDto.setJobFilterList(jobFilters);
        return jobPositionDto;
    }

    @Override
    public JobPosition dtoToEntity(JobPositionDto dto) {
        JobPosition jobPosition = new JobPosition();
        jobPosition.setName(dto.getName());

        // by default job status is ACTIVE
        jobPosition.setStatus(dto.getStatus() == null ? JobPositionStatus.ACTIVE : dto.getStatus());
        return jobPosition;
    }
}
