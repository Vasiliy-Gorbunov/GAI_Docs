package com.gai_app.gai_docs.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Data
public class PassportModel {

    private Long id;

    private Long carId;

    private List<Long> ownersId;

    private LocalDate date;
}
