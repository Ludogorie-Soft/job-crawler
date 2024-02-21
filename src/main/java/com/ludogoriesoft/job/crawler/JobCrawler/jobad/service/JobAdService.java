package com.ludogoriesoft.job.crawler.JobCrawler.jobad.service;

import com.ludogoriesoft.job.crawler.JobCrawler.jobad.persistance.JobAd;
import com.ludogoriesoft.job.crawler.JobCrawler.jobad.persistance.JobAdRepository;
import com.ludogoriesoft.job.crawler.JobCrawler.jobad.persistance.JobAdStatus;
import com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance.JobFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobAdService {
    private final JobAdRepository repository;

    public void save(JobFilter jobFilter, String jobAdUrl, String source, String region, String postDate, String lastUpdated){
        if(getJobAdByAdUrl(jobAdUrl).isEmpty()){
            repository.save(createJobAd(jobFilter, jobAdUrl, source, region, postDate, lastUpdated));
            log.info("Job ad was created in the db with url -> " + jobAdUrl);
        }
    }

    public Optional<JobAd> getJobAdByAdUrl(String jobAdUrl){
        return repository.findByJobAdUrl(jobAdUrl);
    }
    private JobAd createJobAd(JobFilter jobFilter, String jobAdUrl, String source, String region, String postDate,String lastUpdated){
        JobAd jobAd = new JobAd();
        jobAd.setJobFilterId(jobFilter);
        jobAd.setJobAdUrl(jobAdUrl);
        jobAd.setSource(source);
        jobAd.setRegion(region);
        jobAd.setPostDate(postDate);
        jobAd.setLastUpdated(lastUpdated);
        jobAd.setStatus(JobAdStatus.ACTIVE);
        return jobAd;
    }
}
