package com.hbm.user_service.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public record RegistrationRequest(String firstName,
                                  String lastName,
                                  LocalDate dob,
                                  @Email String email,
                                  String password,
                                  List<String>roles){

}

