package com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.service;

import com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.service.DetailedFilterService;
import com.ludogoriesoft.job.crawler.JobCrawler.exception.NotFoundException;
import com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.service.JobFilterService;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.model.JobPositionDto;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.model.JobPositionMapper;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPosition;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobPositionService {

    private final JobPositionRepository repository;
    private final DetailedFilterService detailedFilterService;
    private final JobFilterService jobFilterService;
    private final JobPositionMapper mapper;

    public void save(JobPositionDto dto) {
        if (repository.findByName(dto.getName()).isEmpty()) {
            JobPosition jobPosition = mapper.dtoToEntity(dto);
            repository.save(jobPosition);
            log.info("Job position {} was created in the db", dto.getName());
            // create detailed filters
            if (dto.getWhitelistFilters() != null && !dto.getWhitelistFilters().isEmpty() && dto.getBlacklistFilters() != null && !dto.getBlacklistFilters().isEmpty()) {
                detailedFilterService.save(dto.getWhitelistFilters(), dto.getBlacklistFilters(), jobPosition);
            }
            // create job filters
            if (dto.getJobFilterList() != null && !dto.getJobFilterList().isEmpty()) {
                dto.getJobFilterList().forEach(jf -> {
                    if (!jf.getFilterUrl().isEmpty() && !jf.getJobSite().isEmpty()) {
                        jobFilterService.save(jobPosition, jf.getJobSite(), jf.getFilterUrl());
                    }
                });
            }
        }
    }

    public List<JobPositionDto> list() {
        return repository.findAll()
                .stream()
                .map(mapper::entityToDto)
                .toList();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public JobPositionDto getJobPositionById(Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow(() -> new NotFoundException("No such job position."));
    }

    public void updateJobPosition(JobPositionDto jobPositionDto) {
        Optional<JobPosition> jobPosition = repository.findById(jobPositionDto.getId());
        if (jobPosition.isPresent()) {
            // update status
            jobPosition.get().setStatus(jobPositionDto.getStatus());
            repository.save(jobPosition.get());
            log.info("Job position {} status was updated to {}", jobPosition.get().getName(), jobPositionDto.getStatus());
            detailedFilterService.update(jobPositionDto.getWhitelistFilters(), jobPositionDto.getBlacklistFilters(), jobPosition.get());
            jobFilterService.update(jobPositionDto.getJobFilterList(), jobPosition.get());
        }

    }
}
