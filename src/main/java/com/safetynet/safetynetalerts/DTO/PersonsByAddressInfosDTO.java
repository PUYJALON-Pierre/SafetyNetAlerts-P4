package com.safetynet.safetynetalerts.DTO;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonsByAddressInfosDTO {

  
  private String address;

  private String firstName;

  private String lastName;

  private String phoneNumber;

  private String birthdate;

  private List <String> medications;

  private List <String> allergies;
  
  private String stationNumber;
}
