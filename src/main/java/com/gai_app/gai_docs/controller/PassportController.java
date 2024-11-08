package com.gai_app.gai_docs.controller;

import com.gai_app.gai_docs.DTO.PassportDto;

import java.util.List;


public interface PassportController {


    List<PassportDto> getAllPassports();

    List<PassportDto> getAllPassportsByOwnerId(Long id);

    PassportDto getPassportById(Long id);

    PassportDto getPassportByCarId(Long id);

    Object getCarByPassportId(Long id);

    List<Object> getOwnersByPassportId(Long id);

    List<Object> getOwnersByCarId(Long id);

    PassportDto createPassport(PassportDto passportDto);

    PassportDto updatePassport(Long id, PassportDto passportDto);

    void deletePassport(Long id);
}
