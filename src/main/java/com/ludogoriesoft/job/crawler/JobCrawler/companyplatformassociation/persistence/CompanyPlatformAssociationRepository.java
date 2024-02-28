package com.ludogoriesoft.job.crawler.JobCrawler.companyplatformassociation.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyPlatformAssociationRepository extends JpaRepository<CompanyPlatformAssociation, Long> {
    @Query("SELECT ca FROM  CompanyPlatformAssociation  ca WHERE ca.platformUrl = :platformUrl AND ca.company.id = :companyId")

    Optional<CompanyPlatformAssociation> findByPlatformUrlAndCompanyId(String platformUrl, Long companyId);

    @Query("SELECT ca FROM  CompanyPlatformAssociation ca WHERE ca.company.id = :companyId")
    List<CompanyPlatformAssociation> findByCompanyId(Long companyId);
}
