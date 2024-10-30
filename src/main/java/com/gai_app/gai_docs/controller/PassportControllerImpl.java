package com.gai_app.gai_docs.controller;
import com.gai_app.gai_docs.DTO.PassportDto;
import com.gai_app.gai_docs.mapper.MappingUtils;
import com.gai_app.gai_docs.model.PassportModel;
import com.gai_app.gai_docs.service.PassportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/passports")
public class PassportControllerImpl implements PassportController {

    private final PassportService passportService;
    private final MappingUtils mappingUtils;

    @Autowired
    public PassportControllerImpl(PassportService passportService, MappingUtils mappingUtils) {
        this.passportService = passportService;
        this.mappingUtils = mappingUtils;
    }


    @GetMapping
    public List<PassportDto> getAllPassports() {
        return passportService.getAllPassports().stream().map(mappingUtils::mapToPassportDto).toList();
    }

    @GetMapping("/{id}")
    public PassportDto getPassportById(@PathVariable Long id) {
        return mappingUtils.mapToPassportDto(passportService.getPassportById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassportDto createPassport(@Valid @RequestBody PassportDto passportDto) {
        PassportModel passportModel = mappingUtils.mapToPassportModelFromDto(passportDto);
        return mappingUtils.mapToPassportDto(passportService.createPassport(passportModel));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PassportDto updatePassport(@PathVariable Long id, @Valid @RequestBody PassportDto passportDto) {
        PassportModel updatedPassport = mappingUtils.mapToPassportModelFromDto(passportDto);
        return mappingUtils.mapToPassportDto(passportService.updatePassport(id, updatedPassport));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePassport(@PathVariable Long id) {
        passportService.deletePassport(id);
    }

}