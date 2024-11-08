package com.gai_app.gai_docs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ElementCollection
    @CollectionTable(name = "license_categories", joinColumns = @JoinColumn(name = "license_id"))
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private List<Category> categories;

    private Long ownerId;
}
