package com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.model;

import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPosition;

public interface JobPositionMapper {
    JobPositionDto entityToDto(JobPosition entity);

    JobPosition dtoToEntity(JobPositionDto dto);
}
