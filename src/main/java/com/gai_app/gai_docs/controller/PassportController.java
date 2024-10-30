package com.gai_app.gai_docs.controller;

import com.gai_app.gai_docs.DTO.PassportDto;

import java.util.List;


public interface PassportController {


    List<PassportDto> getAllPassports();

    PassportDto getPassportById(Long id);


    PassportDto createPassport(PassportDto passportDto);


    PassportDto updatePassport(Long id, PassportDto passportDto);


    void deletePassport(Long id);
}
