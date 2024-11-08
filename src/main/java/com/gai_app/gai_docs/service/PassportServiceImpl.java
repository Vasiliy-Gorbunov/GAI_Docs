package com.gai_app.gai_docs.service;

import com.gai_app.gai_docs.entity.Passport;
import com.gai_app.gai_docs.exception.ResourceNotFoundException;
import com.gai_app.gai_docs.mapper.MappingUtils;
import com.gai_app.gai_docs.model.PassportModel;
import com.gai_app.gai_docs.repository.PassportRepository;
import com.gai_app.gai_docs.service.kafka.NotificationService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;
    private final MappingUtils mappingUtils;
    private final NotificationService notificationService;

    @Autowired
    public PassportServiceImpl(PassportRepository passportRepository, MappingUtils mappingUtils, NotificationService notificationService) {
        this.passportRepository = passportRepository;
        this.mappingUtils = mappingUtils;
        this.notificationService = notificationService;
    }


    @Transactional(readOnly = true)
    public List<PassportModel> getAllPassports() {
        return passportRepository.findAll().stream()
                .map(mappingUtils::mapToPassportModelFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PassportModel> getAllPassportsByOwnerId(Long id) {
        return passportRepository.findAllByOwnersIdContains(id).stream()
                .map(mappingUtils::mapToPassportModelFromEntity)
                .collect(Collectors.toList());

    }


    @Transactional(readOnly = true)
    public PassportModel getPassportById(Long id) {
        return mappingUtils.mapToPassportModelFromEntity(passportRepository.findById(id)
                .orElseThrow(() -> ThrowableMessage(id)));
    }

    @Transactional(readOnly = true)
    public PassportModel getPassportByCarId(Long carId) {
        return mappingUtils.mapToPassportModelFromEntity(passportRepository.findByCarId(carId)
                .orElseThrow(() -> ThrowableMessage(carId)));
    }


    @Transactional
    public PassportModel createPassport(PassportModel passportModel) {
        if (passportRepository.findByCarId(passportModel.getCarId()).isPresent()) {
            throw new EntityExistsException("Passport with car id: "
                    + passportModel.getCarId() + " already exists");
        }
        PassportModel savedPassport = mappingUtils.mapToPassportModelFromEntity(passportRepository
                .save(mappingUtils.mapToPassport(passportModel)));
        notificationService.getPassportModelCreateMessageAndSend(savedPassport, "created");
        return savedPassport;
    }


    @Transactional
    public PassportModel updatePassport(Long id, PassportModel updatedPassport) {
        Passport existingPassport = passportRepository.findById(id)
                .orElseThrow(() -> ThrowableMessage(id));
        if (!updatedPassport.getCarId().equals(id) &&
                passportRepository.findByCarId(updatedPassport.getCarId()).isPresent()) {
            throw new EntityExistsException("Passport with car id: "
                    + updatedPassport.getCarId() + " already exists");
        }
        Passport updatingPassport = mappingUtils.mapToPassport(updatedPassport);

        existingPassport.setDate(updatingPassport.getDate());
        existingPassport.setCarId(updatingPassport.getCarId());
        existingPassport.setOwnersId(updatingPassport.getOwnersId());

        PassportModel passportModel = mappingUtils.mapToPassportModelFromEntity(passportRepository
                .save(existingPassport));
        notificationService.getPassportModelCreateMessageAndSend(passportModel, "updated");
        return passportModel;
    }


    @Transactional
    public void deletePassport(Long id) {
        Passport existingPassport = passportRepository.findById(id)
                .orElseThrow(() -> ThrowableMessage(id));
        passportRepository.deleteById(existingPassport.getId());
        notificationService.getPassportModelCreateMessageAndSend(mappingUtils
                .mapToPassportModelFromEntity(existingPassport), "deleted");
    }


    private ResourceNotFoundException ThrowableMessage(Long id) {
        return new ResourceNotFoundException("Passport with id " + id + " not found");
    }
}