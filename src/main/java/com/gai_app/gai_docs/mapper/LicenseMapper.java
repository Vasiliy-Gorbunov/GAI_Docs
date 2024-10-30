package com.gai_app.gai_docs.mapper;
import com.gai_app.gai_docs.DTO.LicenseDto;
import com.gai_app.gai_docs.entity.License;
import com.gai_app.gai_docs.model.LicenseModel;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface LicenseMapper {

    LicenseDto toLicenseDto(LicenseModel model);

    LicenseModel toLicenseModelFromDto(LicenseDto dto);

    LicenseModel toLicenseModelFromEntity(License license);

    License toLicense(LicenseModel model);
}
