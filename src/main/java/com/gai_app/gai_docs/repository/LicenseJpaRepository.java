package com.gai_app.gai_docs.repository;
import com.gai_app.gai_docs.entity.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LicenseJpaRepository extends LicenseRepository, JpaRepository<License, Long> {

    List<License> findAll();

    Optional<License> findById(Long id);

    License save(License License);

    void deleteById(Long id);
}
