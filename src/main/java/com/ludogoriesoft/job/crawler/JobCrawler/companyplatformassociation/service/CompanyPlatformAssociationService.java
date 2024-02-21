package com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.service;

import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.Company;
import com.ludogoriesoft.job.crawler.JobCrawler.company.service.CompanyService;
import com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.persistence.CompanyPlatformAssociation;
import com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.persistence.CompanyPlatformAssociationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyPlatformAssociationService {

    private final CompanyPlatformAssociationRepository repository;
    private final CompanyService companyService;
    public void save(String platformAssociationUrl, String companyName){
        Optional<Company> company = companyService.getCompanyByName(companyName);
        if(company.isPresent()) {
            if (getByPlatformUrl(platformAssociationUrl, company.get().getId()).isEmpty()) {
                repository.save(createCompanyPlatformAssociation(platformAssociationUrl, company.get()));
                log.info("Platform association with url -> {} related with company {} was created in the db", platformAssociationUrl, company.get().getName());
            }
        }
    }

    public void update(Long id, String platformUrl){
        Optional<CompanyPlatformAssociation> cpa = repository.findById(id);
        if (cpa.isPresent()){
            cpa.get().setPlatformUrl(platformUrl);
            repository.save(cpa.get());
            log.info("Platform association url related with company {} was updated to {} ",cpa.get().getCompany().getName(), platformUrl);
        }
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    private Optional<CompanyPlatformAssociation> getByPlatformUrl(String platformUrl, Long companyId){
        return repository.findByPlatformUrlAndCompanyId(platformUrl, companyId);
    }

    private CompanyPlatformAssociation createCompanyPlatformAssociation(String platformUrl, Company company){
        CompanyPlatformAssociation companyPlatformAssociation = new CompanyPlatformAssociation();
        companyPlatformAssociation.setPlatformUrl(platformUrl);
        companyPlatformAssociation.setCompany(company);
        return companyPlatformAssociation;
    }
}
