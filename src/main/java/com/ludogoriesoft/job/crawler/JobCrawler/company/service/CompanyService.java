package com.ludogoriesoft.job.crawler.JobCrawler.company.service;

import com.ludogoriesoft.job.crawler.JobCrawler.company.model.CompanyDto;
import com.ludogoriesoft.job.crawler.JobCrawler.company.model.CompanyMapper;
import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.Company;
import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.CompanyRepository;
import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.CompanyStatus;
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
    private final CompanyMapper mapper;

    public void save(String name, String websiteUrl){
        if(getCompanyByName(name).isEmpty()){
            repository.save(createCompany(name, websiteUrl));
            log.info("Company with name {} was created in the db", name);
        }
    }

    public void delete (Long id){
        repository.deleteById(id);
        log.info("Company with id= {} was deleted from the db",id);
    }

    public void updateCompanyStatusById(Long id, CompanyStatus status){
        Optional<Company> company = repository.findById(id);
        if(company.isPresent()){
            company.get().setCompanyStatus(status);
            repository.save(company.get());
            log.info("The status of company {} was updated to {}",company.get().getName(), status);
        }
    }

    public List<CompanyDto> list(){
        return repository.findAll()
                .stream()
                .map(mapper::entityToDto)
                .toList();
    }
    public Optional<Company> getCompanyByName(String name){
        return repository.findByName(name);
    }

    private Company createCompany(String name,String websiteUrl){

        Company company = new Company();
        company.setName(name);
        company.setWebsiteUrl(websiteUrl);
        return  company;
    }
}
