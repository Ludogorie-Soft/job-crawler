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

    public void save(List<String> whitelistFilters,
                     List<String> blacklistFilters,
                     JobPosition jobPosition) {
        // save whitelist filters
        for (String detailedFilter : whitelistFilters) {
            if (getDetailedFilterByFilterUrlAndJobPositionId(detailedFilter, jobPosition.getId()).isEmpty()) {
                repository.save(createDetailedFilter(detailedFilter, jobPosition, true));
                log.info("Detailed filter= {} was created in the db, related to job position -> \nid:{} \nname:{}", detailedFilter, jobPosition.getId(), jobPosition.getName());
            }
        }
        // save blacklist filters
        for (String detailedFilter : blacklistFilters) {
            if (getDetailedFilterByFilterUrlAndJobPositionId(detailedFilter, jobPosition.getId()).isEmpty()) {
                repository.save(createDetailedFilter(detailedFilter, jobPosition, false));
                log.info("Detailed filter= {} was created in the db, related to job position -> \nid:{} \nname:{}", detailedFilter, jobPosition.getId(), jobPosition.getName());
            }
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private Optional<DetailedFilter> getDetailedFilterByFilterUrlAndJobPositionId(String filter, Long jobPositionId) {
        return repository.findByFilterUrlAndJobPositionId(filter, jobPositionId);
    }

    private DetailedFilter createDetailedFilter(String filter, JobPosition jobPosition, boolean isWhitelist) {
        DetailedFilter detailedFilter = new DetailedFilter();
        detailedFilter.setFilter(filter);
        detailedFilter.setJobPositionId(jobPosition);
        detailedFilter.setIsWhitelist(isWhitelist);
        return detailedFilter;
    }

    public void update(List<String> whitelistFilters, List<String> blacklistFilters, JobPosition jobPosition) {
        // find all detailed filters for given job position
        List<DetailedFilter> detailedFilters = repository.findAllByJobPositionId(jobPosition.getId());
        //delete them
        detailedFilters.forEach(df -> delete(df.getId()));
        // create new one
        save(whitelistFilters, blacklistFilters, jobPosition);
    }
}
