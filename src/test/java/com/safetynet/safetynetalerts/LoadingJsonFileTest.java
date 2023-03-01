package com.safetynet.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonDataBase;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
public class LoadingJsonFileTest {

  
  @Autowired
  JsonDataBase jsonDataBase;
  
  @Test
  void LoadingJsonFile() {

    //Given
    List<Person> persons = new ArrayList<>();
    List<FireStation> fireStations = new ArrayList<>();
    List<MedicalRecord> medicalRecords = new ArrayList<>();

    //When
    persons = jsonDataBase.getPersons();
    fireStations = jsonDataBase.getFirestations();
    medicalRecords = jsonDataBase.getMedicalRecords();

    // Then
    assertNotNull(persons);
    assertNotNull(fireStations);
    assertNotNull(medicalRecords);

  }
}
