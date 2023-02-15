package com.safetynet.safetynetalerts.model;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Person {

  private String firstName;

  private String lastName;

  private String address;

  private String city;

  private String zip;

  private String phone;

  private String email;

  //In order to link a medicalRecord to a person
  private MedicalRecord medicalRecord;

}

