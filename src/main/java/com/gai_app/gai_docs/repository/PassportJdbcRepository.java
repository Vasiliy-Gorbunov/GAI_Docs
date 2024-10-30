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

    @Override
    public List<Passport> findAll() {
        String sql = "SELECT * FROM passport";
        return jdbcTemplate.query(sql, new PassportRowMapper());
    }

    @Override
    public Optional<Passport> findById(Long id) {
        String sql = "SELECT id, car_id, owners_id, date FROM passport WHERE id = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{id}, new PassportRowMapper()));
    }

    @Override
    public Passport save(Passport passport) {
        String sql = "INSERT INTO passport (car_id, owners_id, date) VALUES (?, ?, ?) RETURNING id";

        // Вставка данных и получение сгенерированного id
        Long generatedId = jdbcTemplate.queryForObject(sql, new Object[]{
                passport.getCarId(),
                passport.getOwnersId().toArray(), // Преобразуем список в массив
                passport.getDate()
        }, Long.class);

        // Установка сгенерированного id в объект Passport
        passport.setId(generatedId);
        return passport;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM passport WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
