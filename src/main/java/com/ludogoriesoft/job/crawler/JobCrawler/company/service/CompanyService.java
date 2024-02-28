package com.ludogoriesoft.job.crawler.JobCrawler.company.service;

import com.ludogoriesoft.job.crawler.JobCrawler.company.model.CompanyDto;
import com.ludogoriesoft.job.crawler.JobCrawler.company.model.CompanyMapper;
import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.Company;
import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.CompanyRepository;
import com.ludogoriesoft.job.crawler.JobCrawler.company.persistence.CompanyStatus;
import com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.persistence.CompanyPlatformAssociation;
import com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.service.CompanyPlatformAssociationService;
import com.ludogoriesoft.job.crawler.JobCrawler.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {
    private final CompanyRepository repository;
    private final CompanyMapper mapper;
    private final CompanyPlatformAssociationService platformAssociationService;

    public void save(CompanyDto companyDto) {
        String name = companyDto.getName();
        if (repository.findByName(name).isEmpty()) {
            // create company
            Company company = mapper.dtoToEntity(companyDto);
            // save company
            repository.save(company);
            log.info("Company with name {} was created in the db", name);

            List<String> platformAssociations = companyDto.getPlatformAssociations();
            if (platformAssociations != null && !platformAssociations.isEmpty()) {
                platformAssociations.forEach(pa -> platformAssociationService.save(pa, company));
            }
        }
    }

    public void delete(Long id) {
        Optional<Company> company = repository.findById(id);
        if (company.isPresent()) {
            repository.deleteById(id);
            log.info("Company with id = {} was deleted from the db", id);
        }
    }

    public void updateCompanyById(CompanyDto dto) {
        Optional<Company> company = repository.findById(dto.getId());
        if (company.isPresent()) {
            company.get().setCompanyStatus(dto.getCompanyStatus());
            company.get().setWebsiteUrl(dto.getWebsiteUrl());
            repository.save(company.get());
            log.info("The status of company {} was updated to {}", company.get().getName(), dto.getCompanyStatus());
            log.info("The websiteUrl of company {} was updated to {}", company.get().getName(), dto.getWebsiteUrl());

            // update company platforms
            platformAssociationService.update(dto.getPlatformAssociations(), company.get());
        }
    }

    public List<CompanyDto> list() {
        return repository.findAll()
                .stream()
                .map(mapper::entityToDto)
                .toList();
    }

    public CompanyDto getCompanyById(Long id){
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow(() -> new NotFoundException("No such company."));
    }

}
