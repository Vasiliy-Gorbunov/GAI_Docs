package com.gai_app.gai_docs.mapper;

import com.gai_app.gai_docs.entity.License;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class LicenseRowMapper implements RowMapper<License> {

    @Override
    public License mapRow(ResultSet rs, int rowNum) throws SQLException {
        License license = new License();
        license.setId(rs.getLong("id"));
        license.setDate(rs.getObject("date", LocalDate.class));
        license.setOwnerId(rs.getLong("owner_id"));
        return license;
    }
}
