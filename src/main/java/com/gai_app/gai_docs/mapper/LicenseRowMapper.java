package com.gai_app.gai_docs.mapper;
import com.gai_app.gai_docs.entity.Category;
import com.gai_app.gai_docs.entity.License;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LicenseRowMapper implements RowMapper<License> {

    @Override
    public License mapRow(ResultSet rs, int rowNum) throws SQLException {
        License license = new License();
        license.setId(rs.getLong("id"));
        license.setDate(rs.getObject("date", LocalDate.class));
        license.setOwnerId(rs.getLong("owner_id"));
        String categoriesString = rs.getString("categories");
        if (categoriesString != null && !categoriesString.isEmpty()) {
            List<Category> categories = Arrays.stream(categoriesString.split(","))
                    .map(Category::valueOf)
                    .collect(Collectors.toList());
            license.setCategories(categories);
        }
        return license;
    }
}
