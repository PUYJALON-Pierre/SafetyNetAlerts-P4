package com.safetynet.safetynetalerts.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;


/**
 * Model class for MedicalRecord
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
@Data
@Builder
public class MedicalRecord {


  private String firstName;

  private String lastName;

  private String birthdate;

  private List <String> medications;

  private List <String> allergies;

}
