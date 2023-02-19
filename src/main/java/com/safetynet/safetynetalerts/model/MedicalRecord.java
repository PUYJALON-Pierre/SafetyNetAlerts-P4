package com.safetynet.safetynetalerts.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;


@Data
@Builder 
public class MedicalRecord {
  
  
  private String firstName;
  
  private String lastName;
  
  private String birthdate;
 
  private List <String> medications;
  
  private List <String> allergies;
  
}
