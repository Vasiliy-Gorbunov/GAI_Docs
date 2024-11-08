package com.gai_app.gai_docs.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Getter
@Setter
public class PassportDto {

    private Long id;

    @NotNull(message = "Car Id cannot be null")
    @Positive(message = "Car Id must be positive")
    private Long carId;

    @NotNull(message = "Owners Id cannot be null")
    @NotEmpty(message = "Owners Id cannot be empty")
    private List<Long> ownersId;

    @NotNull(message = "Date cannot be null")
    @Past(message = "Date must be in the past")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    // Метод для валидации, что все элементы списка положительные
    @AssertTrue(message = "All Owners Id must be positive")
    @JsonIgnore
    public boolean isOwnersIdPositive() {
        return ownersId.stream().allMatch(id -> id != null && id > 0);
    }
}
