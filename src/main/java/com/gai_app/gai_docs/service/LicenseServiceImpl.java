package com.gai_app.gai_docs.service;

import com.gai_app.gai_docs.entity.License;
import com.gai_app.gai_docs.exception.ResourceNotFoundException;
import com.gai_app.gai_docs.mapper.MappingUtils;
import com.gai_app.gai_docs.model.LicenseModel;
import com.gai_app.gai_docs.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;
    private final MappingUtils mappingUtils;

    @Autowired
    public LicenseServiceImpl(LicenseRepository licenseRepository, MappingUtils mappingUtils) {
        this.licenseRepository = licenseRepository;
        this.mappingUtils = mappingUtils;
    }


    @Transactional(readOnly = true)
    public List<LicenseModel> getAllLicenses() {
        return licenseRepository.findAll().stream().map(mappingUtils::mapToLicenseModelFromEntity).collect(Collectors.toList());
    }



    @Transactional(readOnly = true)
    public LicenseModel getLicenseById(Long id) {
        return mappingUtils.mapToLicenseModelFromEntity(licenseRepository.findById(id)
                .orElseThrow(() -> ThrowableMessage("", id)));
    }

    @Override
    public LicenseModel getLicenseByOwnerId(Long id) {
        return mappingUtils.mapToLicenseModelFromEntity(licenseRepository.findByOwnerId(id)
                .orElseThrow(() -> ThrowableMessage("owner ", id)));
    }

    @Transactional
    public LicenseModel createLicense(LicenseModel licenseModel) {
        return mappingUtils.mapToLicenseModelFromEntity(licenseRepository.save(mappingUtils.mapToLicense(licenseModel)));
    }



    @Transactional
    public LicenseModel updateLicense(Long id, LicenseModel updatedLicense) {
        License existingLicense = licenseRepository.findById(id)
                .orElseThrow(() -> ThrowableMessage("", id));

        License updatingLicense = mappingUtils.mapToLicense(updatedLicense);

        existingLicense.setDate(updatingLicense.getDate());
        existingLicense.setOwnerId(updatingLicense.getOwnerId());
        existingLicense.setCategories(updatingLicense.getCategories());

        return mappingUtils.mapToLicenseModelFromEntity(licenseRepository.save(existingLicense));
    }


    @Transactional
    public void deleteLicense(Long id) {
        License existingLicense = licenseRepository.findById(id)
                .orElseThrow(() -> ThrowableMessage("",id));
        licenseRepository.deleteById(existingLicense.getId());
    }


    private ResourceNotFoundException ThrowableMessage(String owner, Long id) {
        return new ResourceNotFoundException("License with " + owner + "id " + id + " not found");
    }
}