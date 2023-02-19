package com.safetynet.safetynetalerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonDataBase;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

  @InjectMocks
  private static FireStationServiceImpl fireStationServiceImpl;

  @Mock
  private static JsonDataBase jsonDataBase;

  @Mock
  MedicalRecord medicalRecord;

  public static List<Person> personsByStation = new ArrayList<>();
  public static List<FireStation> firestations = new ArrayList<>();
  public static List<Person> persons = new ArrayList<>();
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

    medicalRecords.add(MedicalRecord.builder().firstName("Lily").lastName("Cooper")
        .birthdate("08/26/2012").medications(medications).allergies(allergies).build());

    medicalRecords.add(MedicalRecord.builder().firstName("Brian").lastName("Stelzer")
        .birthdate("02/18/2022").medications(medications).allergies(allergies).build());

    persons.add(Person.builder().firstName("John").lastName("Boyd").address("1509 Culver St")
        .city("Culver").zip("97451").phone("841-874-6512").email("jaboyd@email.com")
        .medicalRecord(medicalRecord).build());
    persons.add(Person.builder().firstName("Jacob").lastName("Boyd").address("1509 Culver St")
        .city("Culver").zip("97451").phone("841-874-6513").email("drk@email.com")
        .medicalRecord(medicalRecord).build());
    persons.add(Person.builder().firstName("Lily").lastName("Cooper").address("489 Manchester St")
        .city("Culver").zip("97451").phone("841-874-9845").email("lily@email.com")
        .medicalRecord(medicalRecord).build());
    persons.add(Person.builder().firstName("Brian").lastName("Stelzer").address("947 E. Rose Dr")
        .city("Culver").zip("97451").phone("841-874-7784").email("bstel@email.com")
        .medicalRecord(medicalRecord).build());

    firestations.add(FireStation.builder().address("1509 Culver St").stationNumber("3")
        .personsByStation(personsByStation).build());
    firestations.add(FireStation.builder().address("29 15th St").stationNumber("2")
        .personsByStation(personsByStation).build());
    firestations.add(FireStation.builder().address("489 Manchester St").stationNumber("4")
        .personsByStation(personsByStation).build());
    firestations.add(FireStation.builder().address("947 E. Rose Dr").stationNumber("1")
        .personsByStation(personsByStation).build());

    when(jsonDataBase.getFirestations()).thenReturn(firestations);

  }

  @AfterEach
  void cleanUpPerTest() throws Exception {
    firestations.clear();
    persons.clear();
    medicalRecords.clear();
    personsByStation.clear();
  }

  @Test
  void FindAllFireStationsTest() {

    List<FireStation> findAllStations = fireStationServiceImpl.findAll();

    assertEquals(findAllStations, jsonDataBase.getFirestations());

  }

  @Test
  void addFireStationTest() {

    FireStation fireStationToAdd = FireStation.builder().address("748 Townings Dr")
        .stationNumber("3").personsByStation(personsByStation).build();

    // quand ajoute fire
    fireStationServiceImpl.addFireStation(fireStationToAdd);
    jsonDataBase.setFirestations(firestations);

    // retrieve le nombre de personne
    int numberOfFireStationExpected = 5;
    assertEquals(firestations.size(), numberOfFireStationExpected);

  }

  @Test
  void updateFireStationTest() {

    firestations.clear();

    // ajout d'une station
    firestations.add(FireStation.builder().address("1509 Culver St").stationNumber("3")
        .personsByStation(personsByStation).build());

    // créer update de la personne avec first name et lastname
    FireStation fireStationToUpdate = FireStation.builder().address("1509 Culver St")
        .stationNumber("1").personsByStation(personsByStation).build();

    String address = "1509 Culver St";
    String stationNumberToUpdate = "1";

    // quand update personne
    fireStationServiceImpl.updateStationNumber(address, stationNumberToUpdate);

    // retrieve le nombre de personne problème
    assertEquals("[" + fireStationToUpdate.toString() + "]", firestations.toString());

  }

  @Test
  void deleteFireStationTest() {
    firestations.clear();

    // ajout d'une station
    firestations.add(FireStation.builder().address("1509 Culver St").stationNumber("3")
        .personsByStation(personsByStation).build());

    String address = "1509 Culver St";
    String stationNumber = "3";
    // quand delete

    fireStationServiceImpl.deleteFireStation(address, stationNumber);

    // liste vide pour comparer
    List<FireStation> emptyList = new ArrayList<>();
    assertEquals(emptyList, firestations);
  }

  @Test
  void findPersonByStationTest() {
    // Given stationNumber searching
    String stationNumber = "4";

    // Linking between firestation and people
    List<Person> personsByStation = new ArrayList<Person>();
    for (FireStation firestation : firestations) {
      if (firestation.getStationNumber() == stationNumber) {

        for (Person person : persons) {

          if (firestation.getAddress().toString().equals(person.getAddress().toString())) {

            personsByStation.add(person);
            firestation.setPersonsByStation(personsByStation);
          }
        }
      }
    }

    // When
    personsByStation = fireStationServiceImpl.findPersonsByStation(stationNumber);
    // then
    assertEquals(personsByStation.toString(),
        "[Person(firstName=Lily, lastName=Cooper, address=489 Manchester St, city=Culver, zip=97451, phone=841-874-9845, email=lily@email.com, medicalRecord=medicalRecord)]");

  }

@Test
  void findPersonsByStationWithAdultAndChildCountTest() {

    // in order to link person and medical records
    for (Person person : persons) {

      for (MedicalRecord medicalRecord : medicalRecords) {

        if (person.getFirstName().equals(medicalRecord.getFirstName())
            && person.getLastName().equals(medicalRecord.getLastName())) {

          person.setMedicalRecord(medicalRecord);
        }
      }
    }
    // Given stationNumber searching for 4
    String stationNumber = "4";

    // Linking between firestation and people
    List<Person> personsByStation = new ArrayList<Person>();
    for (FireStation firestation : firestations) {
      if (firestation.getStationNumber() == stationNumber) {

        for (Person person : persons) {

          if (firestation.getAddress().toString().equals(person.getAddress().toString())) {

            personsByStation.add(person);
            firestation.setPersonsByStation(personsByStation);
          }
        }
      }
    }

    // When
    personsByStation = fireStationServiceImpl.findPersonsByStation(stationNumber);

   
    List<String> personsByStationWithAdultAndChildCount = fireStationServiceImpl.findPersonsByStationWithAdultAndChildCount(stationNumber);

    // Then
 

    assertEquals(personsByStationWithAdultAndChildCount.toString(), "[Cooper, Lily, 489 Manchester St, 841-874-9845, Nombre d'enfants : 1 Nombre d'adultes : 0]");
  
  }

  @Test
  void findPhoneNumbersByStationTest() {

    // Given stationNumber searching for 4
    String stationNumber = "4";

    // Linking between firestation and people
    List<Person> personsByStation = new ArrayList<Person>();
    for (FireStation firestation : firestations) {
      if (firestation.getStationNumber() == stationNumber) {

        for (Person person : persons) {

          if (firestation.getAddress().toString().equals(person.getAddress().toString())) {

            personsByStation.add(person);
            firestation.setPersonsByStation(personsByStation);
          }
        }
      }
    }

    // When
    personsByStation = fireStationServiceImpl.findPersonsByStation(stationNumber);
    List<String> phoneNumberByStation = fireStationServiceImpl
        .findPhoneNumbersByStation(stationNumber);
//Then
    assertEquals(phoneNumberByStation.toString(), "[Lily, Cooper, 841-874-9845, ------------------------------------------------------]");

  }

}
