package com.gai_app.gai_docs.repository;

import com.gai_app.gai_docs.entity.Passport;

import java.util.List;
import java.util.Optional;

public interface PassportRepository {

    List<Passport> findAll();

    Optional<Passport> findById(Long id);

    Passport save(Passport Passport);

    void deleteById(Long id);
}