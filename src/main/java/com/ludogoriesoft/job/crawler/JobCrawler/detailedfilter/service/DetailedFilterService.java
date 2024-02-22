package com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.service;

import com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.persistance.DetailedFilter;
import com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.persistance.DetailedFilterRepository;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPosition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DetailedFilterService {
    private final DetailedFilterRepository repository;

    public void save(List<String> detailedFilters, JobPosition jobPosition) {
        for (String detailedFilter : detailedFilters) {
            if (getDetailedFilterByFilterUrlAndJobPositionId(detailedFilter, jobPosition.getId()).isEmpty()) {
                repository.save(createDetailedFilter(detailedFilter, jobPosition));
                log.info("Detailed filter= {} was created in the db, related to job position -> \nid:{} \nname:{}",detailedFilter, jobPosition.getId(), jobPosition.getName());
            }
        }
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    private Optional<DetailedFilter> getDetailedFilterByFilterUrlAndJobPositionId(String filter, Long jobPositionId) {
        return repository.findByFilterUrlAndJobPositionId(filter, jobPositionId);
    }

    private DetailedFilter createDetailedFilter(String filter, JobPosition jobPosition) {
        DetailedFilter detailedFilter = new DetailedFilter();
        detailedFilter.setFilter(filter);
        detailedFilter.setJobPositionId(jobPosition);
        return detailedFilter;
    }
}
