package com.gai_app.gai_docs.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class RepositoryConfig {

    @Value("${repository.type}")
    private String repositoryType;

    @Autowired
    private LicenseJpaRepository licenseJpaRepository;
    @Autowired
    private LicenseJdbcRepository licenseJdbcRepository;

    @Autowired
    private PassportJpaRepository passportJpaRepository;
    @Autowired
    private PassportJdbcRepository passportJdbcRepository;

    @Bean
    @Primary
    public LicenseRepository licenseRepository() {
        if ("jdbc".equalsIgnoreCase(repositoryType)) {
            System.out.println("JDBC License repository selected.");
            return licenseJdbcRepository;
        } else {
            System.out.println("JPA License repository selected.");
            return licenseJpaRepository;
        }
    }
    @Bean
    @Primary
    public PassportRepository passportRepository() {
        if ("jdbc".equalsIgnoreCase(repositoryType)) {
            System.out.println("JDBC Passport repository selected.");
            return passportJdbcRepository;
        } else {
            System.out.println("JPA Passport repository selected.");
            return passportJpaRepository;
        }
    }
}