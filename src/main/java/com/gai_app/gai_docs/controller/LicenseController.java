package com.gai_app.gai_docs.controller;

import java.util.List;
import com.gai_app.gai_docs.DTO.LicenseDto;


public interface LicenseController {


    List<LicenseDto> getAllLicenses();

    LicenseDto getLicenseById(Long id);


    LicenseDto createLicense(LicenseDto licenseDto);


    LicenseDto updateLicense(Long id, LicenseDto licenseDto);


    void deleteLicense(Long id);

    LicenseDto getLicenseByOwnerId(Long id);

    Object getOwnerByLicenseId(Long id);
}
