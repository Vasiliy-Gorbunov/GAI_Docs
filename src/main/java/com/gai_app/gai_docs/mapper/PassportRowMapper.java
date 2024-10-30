package com.gai_app.gai_docs.mapper;

import com.gai_app.gai_docs.entity.Passport;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PassportRowMapper implements RowMapper<Passport> {

    @Override
    public Passport mapRow(ResultSet rs, int rowNum) throws SQLException {
        Passport passport = new Passport();
        passport.setId(rs.getLong("id"));
        passport.setCarId(rs.getLong("car_id"));
        passport.setDate(rs.getObject("date", LocalDate.class));
        return passport;
    }

}
