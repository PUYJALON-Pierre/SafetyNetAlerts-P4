package com.safetynet.safetynetalerts.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.safetynetalerts.model.JsonDataBase;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

  @InjectMocks
  MedicalRecordServiceImpl medicalRecordServiceImpl;

  @Mock
  private static JsonDataBase jsonDataBase;

  public static List<MedicalRecord> medicalRecords = new ArrayList<>();
  public static List<String> medications = new ArrayList<>();
  public static List<String> allergies = new ArrayList<>();

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
  void FindAllMedicalRecordsTest() {

    List<MedicalRecord> findAllMedicalRecords = medicalRecordServiceImpl.findAll();

    assertEquals(findAllMedicalRecords, jsonDataBase.getMedicalRecords());

  }

  @Test
  void updateMedicalRecordTest() {

    medicalRecords.clear();
    medications.clear();
    allergies.clear();

    // ajout d'un medical record
    medications.add("aznol:350mg" + "hydrapermazol:100mg");
    allergies.add("nillacilan");
    medicalRecords.add(MedicalRecord.builder().firstName("John").lastName("Boyd")
        .birthdate("03/06/1984").medications(medications).allergies(allergies).build());

    // créer update du medical record avec first name et lastname

    List<String> medications2 = new ArrayList<>();
    List<String> allergies2 = new ArrayList<>();
    medications2.add("pharmacol:5000mg" + "terazine:10mg" + "noznazol:250mg");

    MedicalRecord medicalRecordToUpdate = MedicalRecord.builder().firstName("John").lastName("Boyd")
        .birthdate("03/06/1993").medications(medications2).allergies(allergies2).build();

    // quand update personne
    String firstName = "John";
    String lastName = "Boyd";
    medicalRecordServiceImpl.updateMedicalRecord(medicalRecordToUpdate, firstName, lastName);

    // retrieve le nombre de personne problème
    assertEquals(medicalRecords.toString(), ("[" + medicalRecordToUpdate.toString() + "]"));
    assertEquals(medicalRecordToUpdate.getAllergies().toString(), "[]");
    assertEquals(medicalRecordToUpdate.getMedications().toString(), medications2.toString());
  }

  @Test
  void deleteMedicalRecordTest() {

    medicalRecords.clear();
    medications.clear();
    allergies.clear();

    // ajout d'un medical record
    medications.add("aznol:350mg" + "hydrapermazol:100mg");
    allergies.add("nillacilan");
    medicalRecords.add(MedicalRecord.builder().firstName("John").lastName("Boyd")
        .birthdate("03/06/1984").medications(medications).allergies(allergies).build());

    // quand delete
    String firstName = "John";
    String lastName = "Boyd";
    medicalRecordServiceImpl.deleteMedicalRecord(firstName, lastName);

    // retrieve le nombre de personne
    // liste vide pour comparer
    List<MedicalRecord> emptyList = new ArrayList<>();
    assertEquals(emptyList, medicalRecords);
  }

  @Test
  void findMedicalRecordByNameTest() {

    // rajouter medical record

    String firstName = "John";
    String lastName = "Boyd";
    // quand findByname

    MedicalRecord medicalRecordToFind = medicalRecordServiceImpl.findMedicalRecordByName(firstName,
        lastName);

    System.out.println(medicalRecordToFind.getMedications().toString());
    System.out.println(medicalRecordToFind.getAllergies().toString());

    assertEquals(medicalRecordToFind.getFirstName(), "John");
    assertEquals(medicalRecordToFind.getLastName(), "Boyd");
    assertEquals(medicalRecordToFind.getBirthdate(), "03/06/1984");
    assertEquals(medicalRecordToFind.getMedications().toString(),
        "[aznol:350mghydrapermazol:100mg]");
    assertEquals(medicalRecordToFind.getAllergies().toString(), "[nillacilan]");

  }

}
