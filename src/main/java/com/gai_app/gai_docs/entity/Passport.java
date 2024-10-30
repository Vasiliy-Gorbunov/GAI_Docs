package com.gai_app.gai_docs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String uid;

    private Long carId;

    @ElementCollection
    @CollectionTable(name = "passport_owners_id", joinColumns = @JoinColumn(name = "passport_id"))
    @Column(name = "owner_id")
    private List<Long> ownersId;

    private LocalDate date;
}
