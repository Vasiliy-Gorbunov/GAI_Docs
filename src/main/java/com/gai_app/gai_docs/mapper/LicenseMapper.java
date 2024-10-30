package com.gai_app.gai_docs.mapper;
import com.gai_app.gai_docs.DTO.LicenseDto;
import com.gai_app.gai_docs.entity.Category;
import com.gai_app.gai_docs.entity.License;
import com.gai_app.gai_docs.model.LicenseModel;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring", uses = {PassportMapper.class})
public interface LicenseMapper {

    LicenseDto toLicenseDto(LicenseModel model);

    LicenseModel toLicenseModelFromDto(LicenseDto dto);

    LicenseModel toLicenseModelFromEntity(License license);

    License toLicense(LicenseModel model);

    List<Category> toCategoryList(List<Category> categoryList);

    List<LicenseDto> toLicenseDtoList(List<LicenseModel> licenseModelList);

    List<LicenseModel> toLicenseModelListFromDto(List<LicenseDto> licenseDtoList);

    List<LicenseModel> toLicenseModelListFromEntity(List<License> licenseList);

    List<License> toLicenseList(List<LicenseModel> licenseModelList);
}
