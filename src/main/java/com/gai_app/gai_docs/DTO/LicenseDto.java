package com.gai_app.gai_docs.DTO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.gai_app.gai_docs.entity.Category;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class LicenseDto {

    private Long id;

//    private String uid;

    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @NotNull
    private List<Category> categories;

    @NotNull
    private Long ownerId;

}
