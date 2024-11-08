package com.gai_app.gai_docs.service;

import com.gai_app.gai_docs.model.PassportModel;

import java.util.List;

public interface PassportService {
    List<PassportModel> getAllPassports();
    List<PassportModel> getAllPassportsByOwnerId(Long id);
    PassportModel getPassportById(Long id);
    PassportModel getPassportByCarId(Long carId);
    PassportModel createPassport(PassportModel passportModel);
    PassportModel updatePassport(Long id, PassportModel updatedPassport);
    void deletePassport(Long id);



}
