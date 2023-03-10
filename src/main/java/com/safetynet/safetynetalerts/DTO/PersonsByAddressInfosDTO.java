package com.safetynet.safetynetalerts.DTO;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonsByAddressInfosDTO {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String address;

  private String firstName;

  private String lastName;

  private String phoneNumber;

  private int age;

  private List <String> medications;

  private List <String> allergies;
  

}
