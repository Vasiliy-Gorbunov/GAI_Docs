package com.gai_app.gai_docs.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Getter
@Setter
@Component
public class OwnerDto {

    private Long id;
    private String name;
    private LocalDate dob;
    private String gender;
}
