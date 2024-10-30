package com.gai_app.gai_docs.service;

import com.gai_app.gai_docs.model.LicenseModel;

import java.util.List;

public interface LicenseService {
    List<LicenseModel> getAllLicenses();
    LicenseModel getLicenseById(Long id);
    LicenseModel createLicense(LicenseModel licenseModel);
    LicenseModel updateLicense(Long id, LicenseModel updatedLicense);
    void deleteLicense(Long id);
    LicenseModel getLicenseByOwnerId(Long id);

}
