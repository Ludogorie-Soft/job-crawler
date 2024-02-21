package com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.service;

import com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.service.DetailedFilterService;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.model.JobPositionDto;
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
    public void save(JobPositionDto dto){
        if(getJobPositionByName(dto.getName()).isEmpty()){
            JobPosition jobPosition = new JobPosition();
            jobPosition.setName(dto.getName());
            repository.save(jobPosition);
            if(dto.getFilters() != null && !dto.getFilters().isEmpty()){
                detailedFilterService.save(dto.getFilters(), jobPosition);
            }
            log.info("Job position {} was created in the db", dto.getName());
        }
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
    public Optional<JobPosition> getJobPositionByName(String name){
        return repository.findByName(name);
    }
}
