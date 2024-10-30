package com.gai_app.gai_docs.model;

import com.gai_app.gai_docs.entity.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Getter
@Setter
public class LicenseModel {

    private Long id;

    private LocalDate date;

    private List<Category> categories;

    private Long ownerId;
}
