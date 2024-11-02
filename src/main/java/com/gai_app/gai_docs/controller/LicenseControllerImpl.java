package com.gai_app.gai_docs.controller;
import com.gai_app.gai_docs.DTO.LicenseDto;
import com.gai_app.gai_docs.controller.feign.OwnerClient;
import com.gai_app.gai_docs.mapper.MappingUtils;
import com.gai_app.gai_docs.model.LicenseModel;
import com.gai_app.gai_docs.service.LicenseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/licenses")
public class LicenseControllerImpl implements LicenseController {

    private final LicenseService licenseService;
    private final MappingUtils mappingUtils;
    private final OwnerClient ownerClient;

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

    @GetMapping("/owner/license/{id}")
    public Object getOwnerByLicenseId(@PathVariable Long id) {
        LicenseDto license = getLicenseById(id);
        return getOwnerById(license.getOwnerId()).getBody();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LicenseDto createLicense(@Valid @RequestBody LicenseDto licenseDto) {
        getOwnerById(licenseDto.getOwnerId());
        LicenseModel licenseModel = mappingUtils.mapToLicenseModelFromDto(licenseDto);
        return mappingUtils.mapToLicenseDto(licenseService.createLicense(licenseModel));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public LicenseDto updateLicense(@PathVariable Long id, @Valid @RequestBody LicenseDto licenseDto) {
        getOwnerById(licenseDto.getOwnerId());
        LicenseModel updatedLicense = mappingUtils.mapToLicenseModelFromDto(licenseDto);
        return mappingUtils.mapToLicenseDto(licenseService.updateLicense(id, updatedLicense));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLicense(@PathVariable Long id) {
        licenseService.deleteLicense(id);
    }

    private ResponseEntity<Object> getOwnerById(Long ownerId) {
        return ownerClient.getOwnerById(ownerId);
    }

}