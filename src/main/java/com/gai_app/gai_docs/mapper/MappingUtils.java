package com.gai_app.gai_docs.mapper;
import com.gai_app.gai_docs.DTO.LicenseDto;
import com.gai_app.gai_docs.DTO.PassportDto;
import com.gai_app.gai_docs.entity.License;
import com.gai_app.gai_docs.entity.Passport;
import com.gai_app.gai_docs.model.LicenseModel;
import com.gai_app.gai_docs.model.PassportModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {

    @Autowired
    private LicenseMapper licenseMapper;

    @Autowired
    private PassportMapper passportMapper;

    public LicenseDto mapToLicenseDto(LicenseModel model) {
        return licenseMapper.toLicenseDto(model);
    }

    public LicenseModel mapToLicenseModelFromDto(LicenseDto dto) {
        return licenseMapper.toLicenseModelFromDto(dto);
    }

    public LicenseModel mapToLicenseModelFromEntity(License License) {
        return licenseMapper.toLicenseModelFromEntity(License);
    }

    public License mapToLicense(LicenseModel model) {
        return licenseMapper.toLicense(model);
    }


    public PassportDto mapToPassportDto(PassportModel model) {
        return passportMapper.toPassportDto(model);
    }

    public PassportModel mapToPassportModelFromDto(PassportDto dto) {
        return passportMapper.toPassportModelFromDto(dto);
    }

    public PassportModel mapToPassportModelFromEntity(Passport Passport) {
        return passportMapper.toPassportModelFromEntity(Passport);
    }

    public Passport mapToPassport(PassportModel model) {
        return passportMapper.toPassport(model);
    }
}
