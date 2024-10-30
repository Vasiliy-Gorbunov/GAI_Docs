package com.gai_app.gai_docs.DTO;

import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Long carId;

    @NotNull
    private List<Long> ownersId;

    @NotNull
    private LocalDate date;

}
