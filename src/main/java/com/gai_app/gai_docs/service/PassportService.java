package com.gai_app.gai_docs.service;

import com.gai_app.gai_docs.model.PassportModel;

import java.util.List;

public interface PassportService {
    public List<PassportModel> getAllPassports();
    public PassportModel getPassportById(Long id);
    public PassportModel createPassport(PassportModel passportModel);
    public PassportModel updatePassport(Long id, PassportModel updatedPassport);
    public void deletePassport(Long id);



}
