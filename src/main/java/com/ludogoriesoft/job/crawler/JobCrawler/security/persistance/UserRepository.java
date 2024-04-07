package com.ludogoriesoft.job.crawler.JobCrawler.security.persistance;


import com.ludogoriesoft.job.crawler.JobCrawler.security.persistance.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
    public User getUserByUsername(@Param("username") String username);
}