package com.safetynet.safetynetalerts.DTO;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonInfoDTO {


  private String firstName;

  private String lastName;

  private String address;

  private int age;

  private String email;

  private List <String> medications;

  private List <String> allergies;





}
