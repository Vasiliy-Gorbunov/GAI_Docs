package com.gai_app.gai_docs.mapper;

import com.gai_app.gai_docs.DTO.PassportDto;
import com.gai_app.gai_docs.entity.Passport;
import com.gai_app.gai_docs.model.PassportModel;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring", uses = {LicenseMapper.class})
public interface PassportMapper {

    PassportDto toPassportDto(PassportModel model);

    PassportModel toPassportModelFromDto(PassportDto dto);

    PassportModel toPassportModelFromEntity(Passport passport);

    Passport toPassport(PassportModel model);

    List<Long> toLongList(List<Long> longList);

    List<PassportDto> toPassportDtoList(List<PassportModel> passportModelList);

    List<PassportModel> toPassportModelListFromDto(List<PassportDto> passportDtoList);

    List<PassportModel> toPassportModelListFromEntity(List<Passport> passportList);

    List<Passport> toPassportList(List<PassportModel> passportModelList);

}

