package com.gai_app.gai_docs.DTO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.gai_app.gai_docs.entity.Category;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Getter
@Setter
public class LicenseDto {

    private Long id;

    @NotNull(message = "Date cannot be null")
    @Past(message = "Date must be in the past")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @NotNull(message = "Categories cannot be null")
    @NotEmpty(message = "Categories cannot be empty")
    private List<Category> categories;

    @NotNull (message = "Owner's Id cannot be null")
    @Positive (message = "Owner's Id must be positive number")
    private Long ownerId;

}
