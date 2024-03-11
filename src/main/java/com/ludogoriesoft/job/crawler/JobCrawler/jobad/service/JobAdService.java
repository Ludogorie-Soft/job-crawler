package com.ludogoriesoft.job.crawler.JobCrawler.jobad.service;

import com.ludogoriesoft.job.crawler.JobCrawler.jobad.persistance.JobAd;
import com.ludogoriesoft.job.crawler.JobCrawler.jobad.persistance.JobAdRepository;
import com.ludogoriesoft.job.crawler.JobCrawler.jobad.persistance.JobAdStatus;
import com.ludogoriesoft.job.crawler.JobCrawler.jobfilter.persistance.JobFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobAdService {
    private final JobAdRepository repository;

    public List<JobAd> list(){
        return repository.findAll();
    }

    public void save(JobAdStatus status, JobFilter jobFilter, String jobAdUrl, String source, String region, String postDate){
        if(getJobAdByAdUrl(jobAdUrl).isEmpty()){
            repository.save(createJobAd(status,jobFilter, jobAdUrl, source, region, postDate));
            log.info("Job ad was created in the db with url -> " + jobAdUrl);
        }
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public Optional<JobAd> getJobAdByAdUrl(String jobAdUrl){
        return repository.findByJobAdUrl(jobAdUrl);
    }

    public void updateStatus(Long id, String status){
        Optional<JobAd> ad = repository.findById(id);
        if(ad.isPresent()){
            log.info("Change job ad with id-{} status to {}", ad.get().getId(), status);
            ad.get().setStatus(JobAdStatus.valueOf(status));
            repository.save(ad.get());
        }
    }
    private JobAd createJobAd(JobAdStatus status, JobFilter jobFilter, String jobAdUrl, String source, String region, String postDate){
        JobAd jobAd = new JobAd();
        jobAd.setJobFilterId(jobFilter);
        jobAd.setJobAdUrl(jobAdUrl);
        jobAd.setSource(source);
        jobAd.setRegion(region);
        jobAd.setPostDate(postDate);
        jobAd.setStatus(status);
        return jobAd;
    }
}
