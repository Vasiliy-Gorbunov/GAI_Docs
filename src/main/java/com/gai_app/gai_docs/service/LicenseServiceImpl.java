package com.gai_app.gai_docs.service;

import com.gai_app.gai_docs.entity.License;
import com.gai_app.gai_docs.exception.ResourceNotFoundException;
import com.gai_app.gai_docs.mapper.MappingUtils;
import com.gai_app.gai_docs.model.LicenseModel;
import com.gai_app.gai_docs.repository.LicenseRepository;
import com.gai_app.gai_docs.service.kafka.NotificationService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;
    private final MappingUtils mappingUtils;
    private final NotificationService notificationService;

    @Autowired
    public LicenseServiceImpl(LicenseRepository licenseRepository, MappingUtils mappingUtils, NotificationService notificationService) {
        this.licenseRepository = licenseRepository;
        this.mappingUtils = mappingUtils;
        this.notificationService = notificationService;
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
        if (licenseRepository.findByOwnerId(licenseModel.getOwnerId()).isPresent()) {
            throw new EntityExistsException("License with owner id: "
                    + licenseModel.getOwnerId() + " already exists");
        } else {

            LicenseModel savedLicense = mappingUtils.mapToLicenseModelFromEntity(licenseRepository
                    .save(mappingUtils.mapToLicense(licenseModel)));
            notificationService.getLicenseModelCreateMessageAndSend(savedLicense, "created");
            return savedLicense;
        }
    }



    @Transactional
    public LicenseModel updateLicense(Long id, LicenseModel updatedLicense) {
        License existingLicense = licenseRepository.findById(id)
                .orElseThrow(() -> ThrowableMessage("", id));

        if (!updatedLicense.getOwnerId().equals(id) &&
                licenseRepository.findByOwnerId(updatedLicense.getOwnerId()).isPresent()) {
            throw new EntityExistsException("License with owner id: "
                    + updatedLicense.getOwnerId() + " already exists");
        }

        License updatingLicense = mappingUtils.mapToLicense(updatedLicense);

        existingLicense.setDate(updatingLicense.getDate());
        existingLicense.setOwnerId(updatingLicense.getOwnerId());
        existingLicense.setCategories(updatingLicense.getCategories());

        LicenseModel license = mappingUtils.mapToLicenseModelFromEntity(licenseRepository
                .save(existingLicense));
        notificationService.getLicenseModelCreateMessageAndSend(license, "updated");
        return license;
    }


    @Transactional
    public void deleteLicense(Long id) {
        License existingLicense = licenseRepository.findById(id)
                .orElseThrow(() -> ThrowableMessage("",id));
        licenseRepository.deleteById(existingLicense.getId());
        notificationService.getLicenseModelCreateMessageAndSend(mappingUtils
                .mapToLicenseModelFromEntity(existingLicense), "deleted");
    }


    private ResourceNotFoundException ThrowableMessage(String owner, Long id) {
        return new ResourceNotFoundException("License with " + owner + "id " + id + " not found");
    }
}