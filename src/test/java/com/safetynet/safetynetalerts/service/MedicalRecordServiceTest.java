package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetynet.safetynetalerts.model.JsonDataBase;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
public class MedicalRecordServiceTest {

  @Autowired
  private IMedicalRecordService iMedicalRecordService;

  @MockBean
  private JsonDataBase jsonDataBase;

  public List<MedicalRecord> medicalRecords = new ArrayList<>();
  public List<String> medications = new ArrayList<>();
  public List<String> allergies = new ArrayList<>();

  @BeforeEach
  void setUpPerTest() throws Exception {

    medications.add("aznol:350mg" + "hydrapermazol:100mg");
    allergies.add("nillacilan");

    medicalRecords.add(MedicalRecord.builder().firstName("John").lastName("Boyd")
        .birthdate("03/06/1984").medications(medications).allergies(allergies).build());

    medicalRecords.add(MedicalRecord.builder().firstName("Jacob").lastName("Boyd")
        .birthdate("03/06/1989").medications(medications).allergies(allergies).build());

    medicalRecords.add(MedicalRecord.builder().firstName("Tenley").lastName("Boyd")
        .birthdate("02/18/2012").medications(medications).allergies(allergies).build());

    medicalRecords.add(MedicalRecord.builder().firstName("Tessa").lastName("Carman")
        .birthdate("02/18/2012").medications(medications).allergies(allergies).build());

    when(jsonDataBase.getMedicalRecords()).thenReturn(medicalRecords);

  }

  @AfterEach
  void cleanUpPerTest() throws Exception {
    medicalRecords.clear();
  }

  @Test
  void findAllMedicalRecordsTest() {

    List<MedicalRecord> findAllMedicalRecords = iMedicalRecordService.findAll();

    assertEquals(findAllMedicalRecords, jsonDataBase.getMedicalRecords());

  }

  @Test
  void addMedicalRecordTest() {

    // Given
    MedicalRecord medicalRecord1 = MedicalRecord.builder().firstName("Benoit").lastName("Dupont")
        .birthdate("03/06/1994").medications(medications).allergies(allergies).build();

    // when add person
    iMedicalRecordService.addMedicalRecord(medicalRecord1);

    // retrieve number of medicalRecords expected with size list
    int numberOfPersonExpected = 5;
    assertEquals(medicalRecords.size(), numberOfPersonExpected);

  }

  @Test
  void updateMedicalRecordTest() {

    medicalRecords.clear();
    medications.clear();
    allergies.clear();

    // adding medicalRecord
    medications.add("aznol:350mg" + "hydrapermazol:100mg");
    allergies.add("nillacilan");
    medicalRecords.add(MedicalRecord.builder().firstName("John").lastName("Boyd")
        .birthdate("03/06/1984").medications(medications).allergies(allergies).build());

    // Creating update for person by firstName and lastName
    List<String> medications2 = new ArrayList<>();
    List<String> allergies2 = new ArrayList<>();
    medications2.add("pharmacol:5000mg" + "terazine:10mg" + "noznazol:250mg");

    MedicalRecord medicalRecordToUpdate = MedicalRecord.builder().firstName("John").lastName("Boyd")
        .birthdate("03/06/1993").medications(medications2).allergies(allergies2).build();

    // When update
    iMedicalRecordService.updateMedicalRecord(medicalRecordToUpdate);

    // Check if medicalRecords retrieve in List is same as medicalRecordToUpdate
    assertEquals(medicalRecords.size(), 1);
    assertEquals(medicalRecords.get(0).getLastName(), "Boyd");
    assertEquals(medicalRecords.get(0).getFirstName(), "John");
    assertEquals(medicalRecords.get(0).getBirthdate(), "03/06/1993");
    assertEquals(medicalRecords.get(0).getMedications(), medications2);
    assertEquals(medicalRecords.get(0).getAllergies(), allergies2);

  }

  @Test
  void deleteMedicalRecordTest() {

    medicalRecords.clear();
    medications.clear();
    allergies.clear();

    // Clearing and adding a medicalRecord to delete
    medications.add("aznol:350mg" + "hydrapermazol:100mg");
    allergies.add("nillacilan");
    medicalRecords.add(MedicalRecord.builder().firstName("John").lastName("Boyd")
        .birthdate("03/06/1984").medications(medications).allergies(allergies).build());

    // when delete
    String firstName = "John";
    String lastName = "Boyd";
    iMedicalRecordService.deleteMedicalRecord(firstName, lastName);

    // Check if list is empty
    List<MedicalRecord> emptyList = new ArrayList<>();
    assertEquals(emptyList, medicalRecords);
  }

  @Test
  void findMedicalRecordByNameTest() {

    // given
    String firstName = "John";
    String lastName = "Boyd";

    // when findByName
    MedicalRecord medicalRecordToFind = iMedicalRecordService.findMedicalRecordByName(firstName,
        lastName);

    // then
    assertEquals(medicalRecordToFind.getFirstName(), "John");
    assertEquals(medicalRecordToFind.getLastName(), "Boyd");
    assertEquals(medicalRecordToFind.getBirthdate(), "03/06/1984");
    assertEquals(medicalRecordToFind.getMedications().toString(),
        "[aznol:350mghydrapermazol:100mg]");
    assertEquals(medicalRecordToFind.getAllergies().toString(), "[nillacilan]");

  }

}
