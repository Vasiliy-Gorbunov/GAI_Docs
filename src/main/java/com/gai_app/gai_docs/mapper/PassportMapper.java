package com.gai_app.gai_docs.mapper;

import com.gai_app.gai_docs.DTO.PassportDto;
import com.gai_app.gai_docs.entity.Passport;
import com.gai_app.gai_docs.model.PassportModel;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PassportMapper {

    PassportDto toPassportDto(PassportModel model);

    PassportModel toPassportModelFromDto(PassportDto dto);

    PassportModel toPassportModelFromEntity(Passport passport);

    Passport toPassport(PassportModel model);
}

