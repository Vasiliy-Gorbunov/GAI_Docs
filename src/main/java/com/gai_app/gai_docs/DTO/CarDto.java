package com.gai_app.gai_docs.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CarDto {

    private Long id;
    private String make;
    private String model;
    private String numberPlate;
    private Integer age;
}
