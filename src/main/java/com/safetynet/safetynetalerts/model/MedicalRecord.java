package com.safetynet.safetynetalerts.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class MedicalRecord {


  private String firstName;

  private String lastName;

  private String birthdate;

  private List <String> medications;

  private List <String> allergies;

}
