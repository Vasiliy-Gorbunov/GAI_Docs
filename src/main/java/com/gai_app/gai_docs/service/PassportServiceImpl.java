package com.gai_app.gai_docs.service;

import com.gai_app.gai_docs.entity.Passport;
import com.gai_app.gai_docs.exception.ResourceNotFoundException;
import com.gai_app.gai_docs.mapper.MappingUtils;
import com.gai_app.gai_docs.model.PassportModel;
import com.gai_app.gai_docs.repository.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;
    private final MappingUtils mappingUtils;

    @Autowired
    public PassportServiceImpl(PassportRepository passportRepository, MappingUtils mappingUtils) {
        this.passportRepository = passportRepository;
        this.mappingUtils = mappingUtils;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassportModel> getAllPassports() {
        return passportRepository.findAll().stream().map(mappingUtils::mapToPassportModelFromEntity).collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public PassportModel getPassportById(Long id) {
        return mappingUtils.mapToPassportModelFromEntity(passportRepository.findById(id)
                .orElseThrow(() -> ThrowableMessage(id)));
    }


    @Override
    @Transactional
    public PassportModel createPassport(PassportModel passportModel) {
        return mappingUtils.mapToPassportModelFromEntity(passportRepository.save(mappingUtils.mapToPassport(passportModel)));
    }


    @Override
    @Transactional
    public PassportModel updatePassport(Long id, PassportModel updatedPassport) {
        Passport existingPassport = passportRepository.findById(id)
                .orElseThrow(() -> ThrowableMessage(id));

        Passport updatingPassport = mappingUtils.mapToPassport(updatedPassport);

        existingPassport.setDate(updatingPassport.getDate());
        existingPassport.setCarId(updatingPassport.getCarId());
        existingPassport.setOwnersId(updatingPassport.getOwnersId());

        return mappingUtils.mapToPassportModelFromEntity(passportRepository.save(existingPassport));
    }


    @Override
    @Transactional
    public void deletePassport(Long id) {
        Passport existingPassport = passportRepository.findById(id)
                .orElseThrow(() -> ThrowableMessage(id));
        passportRepository.deleteById(existingPassport.getId());
    }


    private ResourceNotFoundException ThrowableMessage(Long id) {
        return new ResourceNotFoundException("Passport with id " + id + " not found");
    }
}