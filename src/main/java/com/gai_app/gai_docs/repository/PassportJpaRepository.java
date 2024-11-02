package com.gai_app.gai_docs.repository;
import com.gai_app.gai_docs.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassportJpaRepository extends PassportRepository, JpaRepository<Passport, Long> {

    List<Passport> findAll();

    List<Passport> findAllByOwnersIdContains(Long id);

    Optional<Passport> findById(Long id);

    Optional<Passport> findByCarId(Long carId);

    Passport save(Passport Passport);

    void deleteById(Long id);
}
