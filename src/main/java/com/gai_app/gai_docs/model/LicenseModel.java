package com.gai_app.gai_docs.model;

import com.gai_app.gai_docs.entity.Category;

import java.time.LocalDate;
import java.util.List;

public class LicenseModel {

    private Long id;

//    private String uid;

    private LocalDate date;

    private List<Category> categories;

    private Long ownerId;
}
