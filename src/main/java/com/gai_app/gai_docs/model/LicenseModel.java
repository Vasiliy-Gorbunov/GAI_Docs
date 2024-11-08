package com.gai_app.gai_docs.model;

import com.gai_app.gai_docs.entity.Category;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Data
public class LicenseModel {

    private Long id;

    private LocalDate date;

    private List<Category> categories;

    private Long ownerId;
}
