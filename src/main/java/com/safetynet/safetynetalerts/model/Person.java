package com.safetynet.safetynetalerts.model;

import lombok.Builder;
import lombok.Data;



/**
 * Model class for Person
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
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

