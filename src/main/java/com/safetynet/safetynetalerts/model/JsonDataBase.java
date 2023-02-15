package com.safetynet.safetynetalerts.model;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Singular;

@Data
@Component
public class JsonDataBase {


  private List<Person> persons;
  
  
  private List<FireStation> firestations;
  

  private List<MedicalRecord> medicalRecords;

  
  
  
  
  
  
  
  
  

  
  
}
