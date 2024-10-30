package com.gai_app.gai_docs.repository;

import com.gai_app.gai_docs.entity.License;

import java.util.List;
import java.util.Optional;

public interface LicenseRepository {

    List<License> findAll();

    Optional<License> findById(Long id);

    License save(License License);

    void deleteById(Long id);

    Optional<License> findByOwnerId(Long ownerId);
}