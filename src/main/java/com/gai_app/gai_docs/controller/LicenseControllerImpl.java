package com.gai_app.gai_docs.controller;
import com.gai_app.gai_docs.DTO.LicenseDto;
import com.gai_app.gai_docs.mapper.MappingUtils;
import com.gai_app.gai_docs.model.LicenseModel;
import com.gai_app.gai_docs.service.LicenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/licenses")
public class LicenseControllerImpl implements LicenseController {

    private final LicenseService licenseService;
    private final MappingUtils mappingUtils;

    @Autowired
    public LicenseControllerImpl(LicenseService licenseService, MappingUtils mappingUtils) {
        this.licenseService = licenseService;
        this.mappingUtils = mappingUtils;
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

}