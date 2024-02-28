package com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.service;

import com.ludogoriesoft.job.crawler.JobCrawler.company.model.CompanyDto;
import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.Company;
import com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.persistence.CompanyPlatformAssociation;
import com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.persistence.CompanyPlatformAssociationRepository;
import com.ludogoriesoft.job.crawler.JobCrawler.detailedfilter.persistance.DetailedFilter;
import com.ludogoriesoft.job.crawler.JobCrawler.jobpositions.persistance.JobPosition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyPlatformAssociationService {

    private final CompanyPlatformAssociationRepository repository;

    public void save(String platformAssociationUrl, Company company) {
        repository.save(createCompanyPlatformAssociation(platformAssociationUrl, company));
    }

    public void update(List<String> companyPlatforms, Company company) {
        // find all platforms for given company
        List<CompanyPlatformAssociation> platforms = repository.findByCompanyId(company.getId());
        //delete them
        platforms.forEach(cp -> delete(cp.getId()));
        // create new ones
        companyPlatforms.forEach(cp -> save(cp, company));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private CompanyPlatformAssociation createCompanyPlatformAssociation(String platformUrl, Company company) {
        CompanyPlatformAssociation companyPlatformAssociation = new CompanyPlatformAssociation();
        companyPlatformAssociation.setPlatformUrl(platformUrl);
        companyPlatformAssociation.setCompany(company);
        return companyPlatformAssociation;
    }
}
