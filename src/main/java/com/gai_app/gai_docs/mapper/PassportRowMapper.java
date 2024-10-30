package com.gai_app.gai_docs.mapper;

import com.gai_app.gai_docs.entity.Passport;
import org.springframework.jdbc.core.RowMapper;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PassportRowMapper implements RowMapper<Passport> {

    @Override
    public Passport mapRow(ResultSet rs, int rowNum) throws SQLException {
        Passport passport = new Passport();

        passport.setId(rs.getLong("id"));
        passport.setCarId(rs.getLong("car_id"));
        passport.setDate(rs.getObject("date", LocalDate.class));

        // Извлечение массива ownersId и преобразование его в List<Long>
        Array ownersIdArray = rs.getArray("owners_id");
        if (ownersIdArray != null) {
            Long[] ownersIdLongArray = (Long[]) ownersIdArray.getArray();
            List<Long> ownersId = Arrays.stream(ownersIdLongArray)
                    .collect(Collectors.toList());
            passport.setOwnersId(ownersId);
        }

        return passport;
    }
}
