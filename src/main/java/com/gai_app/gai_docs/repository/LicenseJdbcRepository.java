package com.gai_app.gai_docs.repository;

import com.gai_app.gai_docs.entity.Category;
import com.gai_app.gai_docs.entity.License;
import com.gai_app.gai_docs.mapper.LicenseRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LicenseJdbcRepository implements LicenseRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LicenseJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<License> findAll() {
        String sql = "SELECT * FROM license";
        List<License> licenses = jdbcTemplate.query(sql, new LicenseRowMapper());
        licenses.forEach(this::loadCategories); // загрузка категорий для каждой лицензии
        return licenses;
    }

    @Override
    public Optional<License> findById(Long id) {
        String sql = "SELECT * FROM license WHERE id = ?";
        License license = jdbcTemplate.queryForObject(sql, new LicenseRowMapper(), id);
        if (license != null) {
            loadCategories(license); // загрузка категорий для лицензии
        }
        return Optional.ofNullable(license);
    }

    @Override
    public License save(License license) {
        if (license.getId() == null) {
            // Используем RETURNING id для получения идентификатора новой записи
            String sql = "INSERT INTO license (date, owner_id) VALUES (?, ?) RETURNING id";
            Long generatedId = jdbcTemplate.queryForObject(sql, Long.class, license.getDate(), license.getOwnerId());
            license.setId(generatedId);
        } else {
            String sql = "UPDATE license SET date = ?, owner_id = ? WHERE id = ?";
            jdbcTemplate.update(sql, license.getDate(), license.getOwnerId(), license.getId());
        }
        saveCategories(license); // сохранение категорий для лицензии
        return license;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM license WHERE id = ?";
        jdbcTemplate.update(sql, id);
        deleteCategories(id); // удаление категорий для лицензии
    }

    public Optional<License> findByOwnerId(Long ownerId) {
        String sql = "SELECT * FROM license WHERE owner_id = ?";

        Optional<License> license = Optional.ofNullable
                (jdbcTemplate.queryForObject
                        (sql, new LicenseRowMapper(), ownerId));
        // загрузка категорий для лицензии
        license.ifPresent(this::loadCategories);
        return license;

    }


    // Вспомогательные методы для работы с категориями

    private void loadCategories(License license) {
        String sql = "SELECT category FROM license_categories WHERE license_id = ?";
        List<Category> categories = jdbcTemplate.query(sql,
                (rs, rowNum) -> Category.valueOf(rs.getString("category")),
                license.getId()
        );
        license.setCategories(categories);
    }

    private void saveCategories(License license) {
        deleteCategories(license.getId()); // очистка старых категорий
        String sql = "INSERT INTO license_categories (license_id, category) VALUES (?, ?)";
        for (Category category : license.getCategories()) {
            jdbcTemplate.update(sql, license.getId(), category.name());
        }
    }

    private void deleteCategories(Long licenseId) {
        String sql = "DELETE FROM license_categories WHERE license_id = ?";
        jdbcTemplate.update(sql, licenseId);
    }
}
