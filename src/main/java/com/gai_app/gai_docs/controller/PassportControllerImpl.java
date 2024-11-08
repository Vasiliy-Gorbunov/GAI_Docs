package com.gai_app.gai_docs.controller;

import com.gai_app.gai_docs.DTO.PassportDto;
import com.gai_app.gai_docs.controller.feign.OwnerClient;
import com.gai_app.gai_docs.exception.ResourceNotFoundException;
import com.gai_app.gai_docs.mapper.MappingUtils;
import com.gai_app.gai_docs.model.PassportModel;
import com.gai_app.gai_docs.service.PassportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/passports")
public class PassportControllerImpl implements PassportController {

    private final PassportService passportService;
    private final MappingUtils mappingUtils;
    private final RestTemplate restTemplate;
    private final OwnerClient ownerClient;

    @Autowired
    public PassportControllerImpl(PassportService passportService, MappingUtils mappingUtils, RestTemplate restTemplate, OwnerClient ownerClient) {
        this.passportService = passportService;
        this.mappingUtils = mappingUtils;
        this.restTemplate = restTemplate;
        this.ownerClient = ownerClient;
    }


    @GetMapping
    public List<PassportDto> getAllPassports() {
        return passportService.getAllPassports()
                .stream().map(mappingUtils::mapToPassportDto)
                .toList();
    }

    @GetMapping("ownerid/{id}")
    public List<PassportDto> getAllPassportsByOwnerId(@PathVariable Long id) {
        return passportService.getAllPassportsByOwnerId(id)
                .stream().map(mappingUtils::mapToPassportDto)
                .toList();
    }

    @GetMapping("/{id}")
    public PassportDto getPassportById(@PathVariable Long id) {
        return mappingUtils.mapToPassportDto(passportService.getPassportById(id));
    }

    @GetMapping("/carid/{id}")
    public PassportDto getPassportByCarId(@PathVariable Long id) {
        return mappingUtils.mapToPassportDto(passportService.getPassportByCarId(id));
    }

    @GetMapping("car/passportid/{id}")
    public Object getCarByPassportId(@PathVariable Long id) {
        return getCarByCarId(getPassportById(id).getCarId()).getBody();
    }

    @GetMapping("owners/passportid/{id}")
    public List<Object> getOwnersByPassportId(@PathVariable Long id) {
        List<Object> owners = new ArrayList<>();
        List<Long> ownersId = getPassportById(id).getOwnersId();
        for (Long ownerId : ownersId) {
            owners.add(getOwnerById(ownerId).getBody());
        }
        return owners;
    }

    @GetMapping("owners/carid/{id}")
    public List<Object> getOwnersByCarId(@PathVariable Long id) {
        List<Object> owners = new ArrayList<>();
        List<Long> ownersId = getPassportByCarId(id).getOwnersId();
        for (Long ownerId : ownersId) {
            owners.add(getOwnerById(ownerId).getBody());
        }
        return owners;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassportDto createPassport(@Valid @RequestBody PassportDto passportDto) {
        getCarByCarId(passportDto.getCarId());
        passportDto.getOwnersId().forEach(this::getOwnerById);
        PassportModel passportModel = mappingUtils.mapToPassportModelFromDto(passportDto);
        return mappingUtils.mapToPassportDto(passportService.createPassport(passportModel));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PassportDto updatePassport(@PathVariable Long id, @Valid @RequestBody PassportDto passportDto) {
        getCarByCarId(passportDto.getCarId());
        passportDto.getOwnersId().forEach(this::getOwnerById);
        PassportModel updatedPassport = mappingUtils.mapToPassportModelFromDto(passportDto);
        return mappingUtils.mapToPassportDto(passportService.updatePassport(id, updatedPassport));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePassport(@PathVariable Long id) {
        passportService.deletePassport(id);
    }

    private ResponseEntity<Object> getCarByCarId(@PathVariable Long id) {
        String url = "http://cars:8082/cars/" + id;
        try {
            return restTemplate.getForEntity(url, Object.class);
        } catch (HttpClientErrorException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    private ResponseEntity<Object> getOwnerById(Long ownerId) {
        return ownerClient.getOwnerById(ownerId);
    }

}