package com.gai_app.gai_docs.repository;

import com.gai_app.gai_docs.entity.Category;
import com.gai_app.gai_docs.entity.License;
import com.gai_app.gai_docs.mapper.LicenseRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class LicenseJdbcRepository implements LicenseRepository {

    private final JdbcTemplate jdbcTemplate;

    public LicenseJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<License> findAll() {
        String sql = "SELECT * FROM License";
        return jdbcTemplate.query(sql, new LicenseRowMapper());
    }

    @Override
    public Optional<License> findById(Long id) {
        String sql = "SELECT * FROM License WHERE id = ?";
        List<License> Licenses = jdbcTemplate.query(sql, new Object[]{id}, new LicenseRowMapper());
        return Licenses.stream().findFirst();
    }

    @Override
    public License save(License license) {
        String sql = "INSERT INTO license (date, categories, owner_id) VALUES (?, ?, ?) RETURNING id";

        // Выполняем вставку и получаем сгенерированный ID
        Long generatedId = jdbcTemplate.queryForObject(sql, new Object[]{
                license.getDate(),
                license.getCategories()
                        .stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(",")),
                license.getOwnerId()
        }, Long.class);

        // Устанавливаем сгенерированный ID в объект License
        license.setId(generatedId);
        return license;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM License WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
