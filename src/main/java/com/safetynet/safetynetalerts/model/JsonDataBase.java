package com.safetynet.safetynetalerts.model;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;



/**
 * JsonDataBase class that can stock list of Person, list of MedicalRecord and list of Firestation
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
@Data
@Component
public class JsonDataBase {


  private List<Person> persons;

  private List<FireStation> firestations;

  private List<MedicalRecord> medicalRecords;


}
