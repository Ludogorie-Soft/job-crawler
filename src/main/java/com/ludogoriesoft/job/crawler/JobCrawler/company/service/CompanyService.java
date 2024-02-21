package com.ludogoriesoft.job.crawler.JobCrawler.company.service;

import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.Company;
import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.CompanyRepository;
import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.CompanyStatus;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository repository;

    public void save(String name, String websiteUrl, CompanyStatus satus){
        if(getCompanyByName(name).isEmpty()){
            repository.save(createCompany(name, websiteUrl, satus));
            log.info("Company with name {} was created in the db", name);
        }
    }

    public void delete (Long id){
        repository.deleteById(id);
        log.info("Company with id= {} was deleted from the db",id);
    }

    public void updateCompanyStatus(String companyName, CompanyStatus status){
        Optional<Company> company = getCompanyByName(companyName);
        if(company.isPresent()){
            company.get().setCompanyStatus(status);
            repository.save(company.get());
            log.info("The status of company {} was updated to {}",companyName, status);
        }
    }

    public List<Company> list(){
        return repository.findAll();
    }
    public Optional<Company> getCompanyByName(String name){
        return repository.findByName(name);
    }

    private Company createCompany(String name,String websiteUrl,CompanyStatus companyStatus){
        Company company = new Company();
        company.setName(name);
        company.setWebsiteUrl(websiteUrl);
        company.setCompanyStatus(companyStatus);
        return  company;
    }
}
