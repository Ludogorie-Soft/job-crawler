package com.ludogoriesoft.job.crawler.JobCrawler.security.service;


import com.ludogoriesoft.job.crawler.JobCrawler.security.persistance.MyUserDetails;
import com.ludogoriesoft.job.crawler.JobCrawler.security.persistance.UserRepository;
import com.ludogoriesoft.job.crawler.JobCrawler.security.persistance.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(user);
    }

}
