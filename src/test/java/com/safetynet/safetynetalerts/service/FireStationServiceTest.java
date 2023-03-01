package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetynet.safetynetalerts.DTO.PersonsByAddressInfosDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByStationWithCountOfAdultAndChildDTO;
import com.safetynet.safetynetalerts.DTO.PhoneNumberDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonDataBase;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.impl.IFireStationServiceImpl;

@SpringBootTest
public class FireStationServiceTest {

  @Autowired
  private IFireStationService iFireStationService;

  @MockBean
  private JsonDataBase jsonDataBase;

  @Mock
  MedicalRecord medicalRecord;

  public List<Person> personsByStation = new ArrayList<>();
  public List<FireStation> firestations = new ArrayList<>();
  public List<Person> persons = new ArrayList<>();
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
    firestations.add(FireStation.builder().address("908 73rd St").stationNumber("1")
        .personsByStation(personsByStation).build());
    firestations.add(FireStation.builder().address("644 Gershwin Cir").stationNumber("1")
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

    List<FireStation> findAllStations = iFireStationService.findAll();
    assertEquals(findAllStations, jsonDataBase.getFirestations());

  }

  @Test
  void addFireStationTest() {

    // given
    FireStation fireStationToAdd = FireStation.builder().address("748 Townings Dr")
        .stationNumber("3").personsByStation(personsByStation).build();

    // when
    iFireStationService.addFireStation(fireStationToAdd);
    jsonDataBase.setFirestations(firestations);

    // retrieve numberOfPersons expected with size list
    int numberOfFireStationExpected = 7;
    assertEquals(firestations.size(), numberOfFireStationExpected);

  }

  @Test
  void updateFireStationTest() {
    // given
    firestations.clear();

    personsByStation.add((Person.builder().firstName("John").lastName("Boyd")
        .address("1509 Culver St").city("Culver").zip("97451").phone("841-874-6512")
        .email("jaboyd@email.com").medicalRecord(medicalRecord).build()));

    // adding station and creating update object
    firestations.add(FireStation.builder().address("1509 Culver St").stationNumber("3")
        .personsByStation(personsByStation).build());

    FireStation fireStationToUpdate = FireStation.builder().address("1509 Culver St")
        .stationNumber("1").personsByStation(personsByStation).build();

    // when
    iFireStationService.updateStationNumber(fireStationToUpdate);

    // then
    assertEquals(firestations.get(0).getAddress(), "1509 Culver St");
    assertEquals(firestations.get(0).getStationNumber(), "1");
    assertEquals(firestations.get(0).getPersonsByStation().size(), 1);

  }

  @Test
  void deleteFireStationTest() {
    firestations.clear();

    // given
    firestations.add(FireStation.builder().address("1509 Culver St").stationNumber("3")
        .personsByStation(personsByStation).build());

    // when
    String address = "1509 Culver St";
    String stationNumber = "3";
    iFireStationService.deleteFireStation(address, stationNumber);

    // then compare to emptyList
    List<FireStation> emptyList = new ArrayList<>();
    assertEquals(emptyList, firestations);
  }

  @Test
  void findPersonByStationTest() {
    // Given stationNumber searching
    String stationNumber = "4";

    // Linking between fireStation and people
    List<Person> personsByStation = new ArrayList<>();
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
    personsByStation = iFireStationService.findPersonsByStation(stationNumber);
    
    // Then
    assertEquals(personsByStation.size(), 1);
    assertEquals(personsByStation.get(0).getFirstName(), "Lily");
    assertEquals(personsByStation.get(0).getLastName(), "Cooper");
    assertEquals(personsByStation.get(0).getAddress(), "489 Manchester St");
    assertEquals(personsByStation.get(0).getCity(), "Culver");
    assertEquals(personsByStation.get(0).getZip(), "97451");
    assertEquals(personsByStation.get(0).getPhone(), "841-874-9845");
    assertEquals(personsByStation.get(0).getEmail(), "lily@email.com");
    assertEquals(personsByStation.get(0).getMedicalRecord(), medicalRecord);
  }

  @Test
  void findAllPersonsSortedByAddressAndStationTest() {

    // station to search
    String stationNumber = "1";

    // adding person in disorder to this station
    medications.clear();
    allergies.clear();
    medications.add("aznol:350mg" + "hydrapermazol:100mg");
    allergies.add("nillacilan");

    persons.add(Person.builder().firstName("Reginold").lastName("Walker").address("908 73rd St")
        .city("Culver").zip("97451").phone("841-874-8547").email("reg@email.com")
        .medicalRecord(medicalRecord).build());

    medicalRecords.add(MedicalRecord.builder().firstName("Reginold").lastName("Walker")
        .birthdate("07/07/1997").medications(medications).allergies(allergies).build());

    persons.add(Person.builder().firstName("Duncan").lastName("Boyd").address("644 Gershwin Cir")
        .city("Culver").zip("97451").phone("841-874-6512").email("jaboyd@email.com")
        .medicalRecord(medicalRecord).build());

    medicalRecords.add(MedicalRecord.builder().firstName("Duncan").lastName("Boyd")
        .birthdate("08/16/2002").medications(medications).allergies(allergies).build());

    persons.add(Person.builder().firstName("Jamie").lastName("Peters").address("908 73rd St")
        .city("Culver").zip("97451").phone("841-874-7462").email("jpeter@email.com")
        .medicalRecord(medicalRecord).build());

    medicalRecords.add(MedicalRecord.builder().firstName("Jamie").lastName("Peters")
        .birthdate("09/04/2001").medications(medications).allergies(allergies).build());

    // Linking between fireStation and people
    List<Person> personsByStation = new ArrayList<>();
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

    // in order to link person and medical records
    for (Person person : persons) {

      for (MedicalRecord medicalRecord : medicalRecords) {

        if (person.getFirstName().equals(medicalRecord.getFirstName())
            && person.getLastName().equals(medicalRecord.getLastName())) {

          person.setMedicalRecord(medicalRecord);
        }
      }
    }
    // When
    List<PersonsByAddressInfosDTO> personsByStationSortByAddress = iFireStationService
        .findAllPersonsSortedByAddressAndStation(stationNumber);

    // Then
    
    assertEquals(personsByStationSortByAddress.size(), 4);
    
    //first person
    assertEquals(personsByStationSortByAddress.get(0).getStationNumber(), "1");
    assertEquals(personsByStationSortByAddress.get(0).getAddress(), "644 Gershwin Cir");
    assertEquals(personsByStationSortByAddress.get(0).getLastName(), "Boyd");
    assertEquals(personsByStationSortByAddress.get(0).getFirstName(), "Duncan");
    assertEquals(personsByStationSortByAddress.get(0).getPhoneNumber(), "841-874-6512");
    assertEquals(personsByStationSortByAddress.get(0).getBirthdate(), "08/16/2002");
    assertEquals(personsByStationSortByAddress.get(0).getMedications().toString(), "[aznol:350mghydrapermazol:100mg]");
    assertEquals(personsByStationSortByAddress.get(0).getAllergies().toString(), "[nillacilan]");
  
    //second person
    assertEquals(personsByStationSortByAddress.get(1).getStationNumber(), "1");
    assertEquals(personsByStationSortByAddress.get(1).getAddress(), "908 73rd St");
    assertEquals(personsByStationSortByAddress.get(1).getLastName(), "Walker");
    assertEquals(personsByStationSortByAddress.get(1).getFirstName(), "Reginold");
    assertEquals(personsByStationSortByAddress.get(1).getPhoneNumber(), "841-874-8547");
    assertEquals(personsByStationSortByAddress.get(1).getBirthdate(), "07/07/1997");
    assertEquals(personsByStationSortByAddress.get(1).getMedications().toString(), "[aznol:350mghydrapermazol:100mg]");
    assertEquals(personsByStationSortByAddress.get(1).getAllergies().toString(), "[nillacilan]");
    
    
    //third person
    assertEquals(personsByStationSortByAddress.get(2).getStationNumber(), "1");
    assertEquals(personsByStationSortByAddress.get(2).getAddress(), "908 73rd St");
    assertEquals(personsByStationSortByAddress.get(2).getLastName(), "Peters");
    assertEquals(personsByStationSortByAddress.get(2).getFirstName(), "Jamie");
    assertEquals(personsByStationSortByAddress.get(2).getPhoneNumber(), "841-874-7462");
    assertEquals(personsByStationSortByAddress.get(2).getBirthdate(), "09/04/2001");
    assertEquals(personsByStationSortByAddress.get(2).getMedications().toString(), "[aznol:350mghydrapermazol:100mg]");
    assertEquals(personsByStationSortByAddress.get(2).getAllergies().toString(), "[nillacilan]");
    
    //fourth person
    assertEquals(personsByStationSortByAddress.get(3).getStationNumber(), "1");
    assertEquals(personsByStationSortByAddress.get(3).getAddress(), "947 E. Rose Dr");
    assertEquals(personsByStationSortByAddress.get(3).getLastName(), "Stelzer");
    assertEquals(personsByStationSortByAddress.get(3).getFirstName(), "Brian");
    assertEquals(personsByStationSortByAddress.get(3).getPhoneNumber(), "841-874-7784");
    assertEquals(personsByStationSortByAddress.get(3).getBirthdate(), "02/18/2022");
    assertEquals(personsByStationSortByAddress.get(3).getMedications().toString(), "[aznol:350mghydrapermazol:100mg]");
    assertEquals(personsByStationSortByAddress.get(3).getAllergies().toString(), "[nillacilan]");
   

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

    // Linking between fireStation and people
    List<Person> personsByStation = new ArrayList<>();
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
    personsByStation = iFireStationService.findPersonsByStation(stationNumber);

    PersonsByStationWithCountOfAdultAndChildDTO personsByStationWithAdultAndChildCount = iFireStationService
        .findPersonsByStationWithAdultAndChildCount(stationNumber);

    // Then
    assertEquals(personsByStationWithAdultAndChildCount.getPersonListByStationNumber().size(), 1);
    assertEquals(personsByStationWithAdultAndChildCount.getPersonListByStationNumber().get(0).getFirstName(), "Lily");
    assertEquals(personsByStationWithAdultAndChildCount.getPersonListByStationNumber().get(0).getLastName(), "Cooper");
    assertEquals(personsByStationWithAdultAndChildCount.getPersonListByStationNumber().get(0).getPhoneNumber(), "841-874-9845");
    assertEquals(personsByStationWithAdultAndChildCount.getPersonListByStationNumber().get(0).getAddress(), "489 Manchester St");
    
    assertEquals(personsByStationWithAdultAndChildCount.getNumberChildren(), 1);
    assertEquals(personsByStationWithAdultAndChildCount.getNumberAdult(), 0);
    
  }

  
  @Test
  void findPhoneNumbersByStationTest() {

    // Given stationNumber searching for 4
    String stationNumber = "4";

    // Linking between fireStation and people
    List<Person> personsByStation = new ArrayList<>();
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
    personsByStation = iFireStationService.findPersonsByStation(stationNumber);
    List<PhoneNumberDTO> phoneNumberByStation = iFireStationService
        .findPhoneNumbersByStation(stationNumber);
    // Then
    assertEquals(phoneNumberByStation.size(), 1);
    assertEquals(phoneNumberByStation.get(0).getFirstName(), "Lily");
    assertEquals(phoneNumberByStation.get(0).getLastName(), "Cooper");
    assertEquals(phoneNumberByStation.get(0).getPhoneNumber(), "841-874-9845");
   
  }

}
