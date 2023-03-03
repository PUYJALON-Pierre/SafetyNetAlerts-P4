package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.DTO.EmailDTO;
import com.safetynet.safetynetalerts.DTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByAddressInfosDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonDataBase;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
public class PersonServiceTest {

  @Autowired
  private IPersonService iPersonService;

  @MockBean
  private JsonDataBase jsonDataBase;

  @Mock
  MedicalRecord medicalRecord;

  public List<Person> persons = new ArrayList<>();

  @BeforeEach
  void setUpPerTest() throws Exception {
    
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
    
    when(jsonDataBase.getPersons()).thenReturn(persons);

  }

  @AfterEach
  void cleanUpPerTest() throws Exception {
    persons.clear();
  }

  @Test
  void FindAllPersonsTest() {

    List<Person> findAll = iPersonService.findAll();

    assertEquals(findAll, jsonDataBase.getPersons());
  }

  
  @Test
  void addPersonTest() {

    Person personToAdd = Person.builder().firstName("Benoit").lastName("Dupont")
        .address("854 E. Tulipe Dr").city("Culver").zip("97451").phone("841-874-2648")
        .email("bendup@email.com").medicalRecord(medicalRecord).build();

    // when add person
    iPersonService.addPerson(personToAdd);
   
    // retrieve numberOfPersons expected with size list
    int numberOfPersonExpected = 5;
    assertEquals(persons.size(), numberOfPersonExpected);

    //scénario echec?
    
  }

  @Test
  void updatePersonTest() {

    persons.clear();

    // adding a person
    persons.add(Person.builder().firstName("Brian").lastName("Stelzer").address("947 E. Rose Dr")
        .city("Culver").zip("97451").phone("841-874-7784").email("bstel@email.com")
        .medicalRecord(medicalRecord).build());

    // Creating update for person by firstName and lastName
    Person personToUpdate = Person.builder().firstName("Brian").lastName("Stelzer")
        .address("444 E. Rose Dr").city("Culver").zip("97451").phone("444-444-4444")
        .email("benoit@email.com").medicalRecord(medicalRecord).build();

    // When update
    Person personUpdated = iPersonService.updatePerson(personToUpdate);

    // person retrieve in List is same as personToUpdate
    assertEquals( "444 E. Rose Dr", personUpdated.getAddress() );
    assertEquals( "444-444-4444", personUpdated.getPhone() );
    assertEquals( "benoit@email.com", personUpdated.getEmail() );
    assertEquals( persons.size(),1);
  }

  @Test
  void findPersonByNameTest() {

    // firstName and lastName parameters
    String firstName = "John";
    String lastName = "Boyd";

    // When findByname
    Person personToFind = iPersonService.findByName(firstName, lastName);

    // retrieve person attributes
    assertEquals(personToFind.getFirstName(), "John");
    assertEquals(personToFind.getLastName(), "Boyd");
    assertEquals(personToFind.getAddress(), "1509 Culver St");
    assertEquals(personToFind.getCity(), "Culver");
    assertEquals(personToFind.getZip(), "97451");
    assertEquals(personToFind.getPhone(), "841-874-6512");
    assertEquals(personToFind.getEmail(), "jaboyd@email.com");
  }

  @Test
  void deletePersonTest() {
    persons.clear();

    // adding a Person
    persons.add(Person.builder().firstName("Brian").lastName("Stelzer").address("947 E. Rose Dr")
        .city("Culver").zip("97451").phone("841-874-7784").email("bstel@email.com")
        .medicalRecord(medicalRecord).build());

    // When delete
    String firstName = "Brian";
    String lastName = "Stelzer";
    iPersonService.deletePerson(firstName, lastName);

    // Check if list is empty
    List<Person> emptyList = new ArrayList<>();
    assertEquals(emptyList, persons);

  }

  @Test
  void findPersonsByAdressWithInfosTest() {

    // given address to search, adding firestation to retrieve stationNumber, adding medicalRecords
    // to find
    String address = "1509 Culver St";
    List<FireStation> fireStations = new ArrayList<>();
    fireStations.add(FireStation.builder().address("1509 Culver St").stationNumber("3").build());
    when(jsonDataBase.getFirestations()).thenReturn(fireStations);

    List<MedicalRecord> medicalRecords = new ArrayList<>();
    List<String> medications = new ArrayList<>();
    List<String> allergies = new ArrayList<>();

    medications.add("aznol:350mg" + "hydrapermazol:100mg");
    allergies.add("nillacilan");

    medicalRecords.add(MedicalRecord.builder().firstName("John").lastName("Boyd")
        .birthdate("03/06/1984").medications(medications).allergies(allergies).build());

    medicalRecords.add(MedicalRecord.builder().firstName("Jacob").lastName("Boyd")
        .birthdate("03/06/1989").medications(medications).allergies(allergies).build());

    when(jsonDataBase.getMedicalRecords()).thenReturn(medicalRecords);

    // in order to link person and medical records
    for (Person person : persons) {

      for (MedicalRecord medicalRecord : medicalRecords) {

        if (person.getFirstName().equals(medicalRecord.getFirstName())
            && person.getLastName().equals(medicalRecord.getLastName())) {

          person.setMedicalRecord(medicalRecord);
        }
      }
    }

    // when
    List<PersonsByAddressInfosDTO> personsToFind = iPersonService
        .findPersonsByAddressWithInfos(address);

    String stationNumber = null;
    for (PersonsByAddressInfosDTO personInfos : personsToFind) {
      stationNumber = personInfos.getStationNumber();
    }
    // Check that size of list is 2 because of John Boyd and Jacob Boyd at same house
    assertEquals(personsToFind.size(), 2);
    assertEquals(stationNumber, "3");

  }

  @Test
  void findAllEmailTest() {
    String city = "Culver";
    List<EmailDTO> findAllEmail = iPersonService.findAllEmailByCity(city);

    assertEquals(findAllEmail.size(), 4);
    assertEquals(findAllEmail.get(0).getEmail(), "jaboyd@email.com");
    assertEquals(findAllEmail.get(1).getEmail(), "drk@email.com");
    assertEquals(findAllEmail.get(2).getEmail(), "lily@email.com");
    assertEquals(findAllEmail.get(3).getEmail(), "bstel@email.com");

  }

  @Test
  void findAllPersonsInfoTest() {

    //given
    persons.clear();

    persons.add(Person.builder().firstName("John").lastName("Boyd").address("1509 Culver St")
        .city("Culver").zip("97451").phone("841-874-6512").email("jaboyd@email.com")
        .medicalRecord(medicalRecord).build());
    persons.add(Person.builder().firstName("Jacob").lastName("Boyd").address("1509 Culver St")
        .city("Culver").zip("97451").phone("841-874-6513").email("drk@email.com")
        .medicalRecord(medicalRecord).build());

    List<MedicalRecord> medicalRecords = new ArrayList<>();
    List<String> medications = new ArrayList<>();
    List<String> allergies = new ArrayList<>();

    medications.add("aznol:350mg" + "hydrapermazol:100mg");
    allergies.add("nillacilan");

    medicalRecords.add(MedicalRecord.builder().firstName("John").lastName("Boyd")
        .birthdate("03/06/1984").medications(medications).allergies(allergies).build());

    medicalRecords.add(MedicalRecord.builder().firstName("Jacob").lastName("Boyd")
        .birthdate("03/06/1989").medications(medications).allergies(allergies).build());

    when(jsonDataBase.getMedicalRecords()).thenReturn(medicalRecords);

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
    List<PersonInfoDTO> findAllPersonsInfo = iPersonService.findAllPersonsInfo();
    
    // Then
    assertEquals(findAllPersonsInfo.size(), 2);
    assertEquals(findAllPersonsInfo.get(0).getFirstName(), "John");
    assertEquals(findAllPersonsInfo.get(0).getLastName(), "Boyd");
    assertEquals(findAllPersonsInfo.get(0).getAddress(), "1509 Culver St");
    assertEquals(findAllPersonsInfo.get(0).getBirthdate(), "03/06/1984");
    assertEquals(findAllPersonsInfo.get(0).getEmail(), "jaboyd@email.com");
    assertEquals(findAllPersonsInfo.get(0).getMedications().toString(), "[aznol:350mghydrapermazol:100mg]");
    assertEquals(findAllPersonsInfo.get(0).getAllergies().toString(), "[nillacilan]");
    
    assertEquals(findAllPersonsInfo.get(1).toString(), "PersonInfoDTO(firstName=Jacob, lastName=Boyd, address=1509 Culver St, birthdate=03/06/1989, email=drk@email.com, medications=[aznol:350mghydrapermazol:100mg], allergies=[nillacilan])");
 
  }

  @Test
  void findChildByAddressTest() { 
    
    // create lists in order to use medical record
    List<MedicalRecord> medicalRecords = new ArrayList<>();
    List<String> medications = new ArrayList<>();
    List<String> allergies = new ArrayList<>();

    // add medicalRecords medications.add("aznol:350mg" + "hydrapermazol:100mg");
    allergies.add("nillacilan");

    medicalRecords.add(MedicalRecord.builder().firstName("John").lastName("Boyd")
        .birthdate("03/06/2008").medications(medications).allergies(allergies).build());

    medicalRecords.add(MedicalRecord.builder().firstName("Jacob").lastName("Boyd")
        .birthdate("03/06/2020").medications(medications).allergies(allergies).build());

    medicalRecords.add(MedicalRecord.builder().firstName("Lily").lastName("Cooper")
        .birthdate("03/06/1994").medications(medications).allergies(allergies).build());

    medicalRecords.add(MedicalRecord.builder().firstName("Brian").lastName("Stelzer")
        .birthdate("12/06/1975").medications(medications).allergies(allergies).build());

    // in order to link person and medical records
    for (Person person : persons) {

      for (MedicalRecord medicalRecord : medicalRecords) {

        if (person.getFirstName().equals(medicalRecord.getFirstName())
            && person.getLastName().equals(medicalRecord.getLastName())) {

          person.setMedicalRecord(medicalRecord);
        }
      }
    }

    // when findChildByAddress
    String address = "1509 Culver St";
    List<ChildDTO> childrenListByAddress = iPersonService.findChildByAddress(address);

   //then
    assertEquals(childrenListByAddress.size(), 2);
    assertEquals(childrenListByAddress.get(0).getFirstName(), "John");
    assertEquals(childrenListByAddress.get(0).getLastName(), "Boyd");
    assertEquals(childrenListByAddress.get(0).getAge(), 14);
    assertEquals(childrenListByAddress.get(0).getPersonsAtSameHouse().size(), 2);
    
    
    assertEquals(childrenListByAddress.get(1).getFirstName(), "Jacob");
    assertEquals(childrenListByAddress.get(1).getLastName(), "Boyd");
    assertEquals(childrenListByAddress.get(1).getAge(), 2);
    assertEquals(childrenListByAddress.get(1).getPersonsAtSameHouse().size(), 2);
   
  }
  
  
  @Test
  void findNoChildByAddressTest() { 

    persons.clear();
   
    String address = "1509 Culver St";
    List<ChildDTO> childrenListByAddress = iPersonService.findChildByAddress(address);
    List<ChildDTO> emptyList = new ArrayList();
   
    //then
    assertEquals(childrenListByAddress.toString(), emptyList.toString() );
  }
  
  

}
