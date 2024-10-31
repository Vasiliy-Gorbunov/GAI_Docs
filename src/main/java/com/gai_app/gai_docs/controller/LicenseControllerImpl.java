package com.gai_app.gai_docs.controller;
import com.gai_app.gai_docs.DTO.LicenseDto;
import com.gai_app.gai_docs.DTO.OwnerDto;
import com.gai_app.gai_docs.controller.feign.OwnerClient;
import com.gai_app.gai_docs.mapper.MappingUtils;
import com.gai_app.gai_docs.model.LicenseModel;
import com.gai_app.gai_docs.service.LicenseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping(value = "/licenses")
public class LicenseControllerImpl implements LicenseController {

    private final LicenseService licenseService;
    private final MappingUtils mappingUtils;
    private final OwnerClient ownerClient;
    private static final Logger logger = LoggerFactory.getLogger(LicenseControllerImpl.class);

    @Autowired
    public LicenseControllerImpl(LicenseService licenseService, MappingUtils mappingUtils, OwnerClient ownerClient) {
        this.licenseService = licenseService;
        this.mappingUtils = mappingUtils;
        this.ownerClient = ownerClient;
    }


    @GetMapping
    public List<LicenseDto> getAllLicenses() {
        return licenseService.getAllLicenses().stream().map(mappingUtils::mapToLicenseDto).toList();
    }

    @GetMapping("/{id}")
    public LicenseDto getLicenseById(@PathVariable Long id) {
        return mappingUtils.mapToLicenseDto(licenseService.getLicenseById(id));
    }


    @GetMapping("/ownerid/{id}")
    public LicenseDto getLicenseByOwnerId(@PathVariable Long id) {
        return mappingUtils.mapToLicenseDto(licenseService.getLicenseByOwnerId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LicenseDto createLicense(@Valid @RequestBody LicenseDto licenseDto) {

        HttpStatusCode statusCode = getOwnerById(licenseDto.getOwnerId()).getStatusCode();
        boolean status_4xx = statusCode.is4xxClientError();
        logger.info("Status code in create license: {}", statusCode);
        if (status_4xx) {
            throw new ResponseStatusException(statusCode);
        }
        LicenseModel licenseModel = mappingUtils.mapToLicenseModelFromDto(licenseDto);
        return mappingUtils.mapToLicenseDto(licenseService.createLicense(licenseModel));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public LicenseDto updateLicense(@PathVariable Long id, @Valid @RequestBody LicenseDto licenseDto) {
        LicenseModel updatedLicense = mappingUtils.mapToLicenseModelFromDto(licenseDto);
        return mappingUtils.mapToLicenseDto(licenseService.updateLicense(id, updatedLicense));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLicense(@PathVariable Long id) {
        licenseService.deleteLicense(id);
    }

    private ResponseEntity<Object> getOwnerById(Long ownerId) {
        logger.info("Get owner by id: {}", ownerId);
        ResponseEntity<Object> ownerById = ownerClient.getOwnerById(ownerId);
        logger.info("Received owner: {}", ownerById);
        HttpStatusCode statusCode = ownerById.getStatusCode();
        logger.info("Status code: {}", statusCode);
        return new ResponseEntity<>(ownerById, statusCode);
    }

}