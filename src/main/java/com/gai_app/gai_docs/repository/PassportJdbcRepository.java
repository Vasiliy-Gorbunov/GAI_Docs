package com.gai_app.gai_docs.repository;

import com.gai_app.gai_docs.entity.Passport;
import com.gai_app.gai_docs.mapper.PassportRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PassportJdbcRepository implements PassportRepository {

    private final JdbcTemplate jdbcTemplate;

    public PassportJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Passport> findAll() {
        String sql = "SELECT * FROM passport";
        List<Passport> passports = jdbcTemplate.query(sql, new PassportRowMapper());
        passports.forEach(this::loadOwnersId); // загрузка ownersId для каждого паспорта
        return passports;
    }

    public List<Passport> findAllByOwnersIdContains(Long id) {
        String sql = "SELECT * FROM passport " +
                "JOIN passport_owners_id ON passport.id = passport_owners_id.passport_id " +
                "WHERE passport_owners_id.owner_id = ?";
        List<Passport> passports = jdbcTemplate.query(sql, new PassportRowMapper());
        passports.forEach(this::loadOwnersId); // загрузка ownersId для каждого паспорта
        return passports;
    }


    public Optional<Passport> findById(Long id) {
        String sql = "SELECT * FROM passport WHERE id = ?";
        Passport passport = jdbcTemplate.queryForObject(sql, new PassportRowMapper(), id);
        if (passport != null) {
            loadOwnersId(passport); // загрузка ownersId для паспорта
        }
        return Optional.ofNullable(passport);
    }

    public Optional<Passport> findByCarId(Long carId) {
        String sql = "SELECT * FROM passport WHERE car_id = ?";
        Passport passport = jdbcTemplate.queryForObject(sql, new PassportRowMapper(), carId);
        if (passport != null) {
            loadOwnersId(passport); // загрузка ownersId для паспорта
        }
        return Optional.ofNullable(passport);
    }


    public Passport save(Passport passport) {
        if (passport.getId() == null) {
            // Используем RETURNING id для получения идентификатора новой записи
            String sql = "INSERT INTO passport (car_id, date) VALUES (?, ?) RETURNING id";
            Long generatedId = jdbcTemplate.queryForObject(sql, Long.class, passport.getCarId(), passport.getDate());
            passport.setId(generatedId);
        } else {
            String sql = "UPDATE passport SET car_id = ?, date = ? WHERE id = ?";
            jdbcTemplate.update(sql, passport.getCarId(), passport.getDate(), passport.getId());
        }
        saveOwnersId(passport); // сохранение ownersId для паспорта
        return passport;
    }


    public void deleteById(Long id) {
        String sql = "DELETE FROM passport WHERE id = ?";
        jdbcTemplate.update(sql, id);
        deleteOwnersId(id); // удаление связей ownersId
    }

    // Вспомогательные методы для работы с ownersId

    private void loadOwnersId(Passport passport) {
        String sql = "SELECT owner_id FROM passport_owners_id WHERE passport_id = ?";
        List<Long> ownersId = jdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getLong("owner_id"),
                passport.getId()
        );
        passport.setOwnersId(ownersId);
    }

    private void saveOwnersId(Passport passport) {
        deleteOwnersId(passport.getId()); // очистка старых ownersId
        String sql = "INSERT INTO passport_owners_id (passport_id, owner_id) VALUES (?, ?)";
        for (Long ownerId : passport.getOwnersId()) {
            jdbcTemplate.update(sql, passport.getId(), ownerId);
        }
    }

    private void deleteOwnersId(Long passportId) {
        String sql = "DELETE FROM passport_owners_id WHERE passport_id = ?";
        jdbcTemplate.update(sql, passportId);
    }
}
