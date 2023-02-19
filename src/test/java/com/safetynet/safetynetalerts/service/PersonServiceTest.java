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
public class PersonServiceTest {

  @InjectMocks
  private static PersonServiceImpl personServiceImpl;

  @Mock
  private static JsonDataBase jsonDataBase;

  @Mock
  MedicalRecord medicalRecord;

  public static List<Person> persons = new ArrayList<>();

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

    List<Person> findAll = personServiceImpl.findAll();

    assertEquals(findAll, jsonDataBase.getPersons());

  }

  @Test
  void addPersonTest() {

    Person personToAdd = Person.builder().firstName("Benoit").lastName("Dupont")
        .address("854 E. Tulipe Dr").city("Culver").zip("97451").phone("841-874-2648")
        .email("bendup@email.com").medicalRecord(medicalRecord).build();

    // quand ajoute personne
    personServiceImpl.addPerson(personToAdd);
    jsonDataBase.setPersons(persons);

    // retrieve le nombre de personne
    int numberOfPersonExpected = 5;
    assertEquals(persons.size(), numberOfPersonExpected);

  }

  @Test
  void updatePersonTest() {

    persons.clear();

    // ajout d'une personne
    persons.add(Person.builder().firstName("Brian").lastName("Stelzer").address("947 E. Rose Dr")
        .city("Culver").zip("97451").phone("841-874-7784").email("bstel@email.com")
        .medicalRecord(medicalRecord).build());

    // créer update de la personne avec first name et lastname
    Person personToUpdate = Person.builder().firstName("Brian").lastName("Stelzer")
        .address("444 E. Rose Dr").city("Culver").zip("97451").phone("444-444-4444")
        .email("benoit@email.com").medicalRecord(medicalRecord).build();

    String firstName = "Brian";
    String lastName = "Stelzer";

    // quand update personne
    personServiceImpl.updatePerson(personToUpdate, firstName, lastName);

    // retrieve le nombre de personne problème
    assertEquals(persons.toString(), ("[" + personToUpdate.toString() + "]"));

  }

  @Test
  void findPersonByNameTest() {
//rajouter medical record

    String firstName = "John";
    String lastName = "Boyd";
    // quand findByname

    Person personToFind = personServiceImpl.findByName(firstName, lastName);

    // retrieve le nombre de personne

    assertEquals(personToFind.getFirstName(), "John");
    assertEquals(personToFind.getLastName(), "Boyd");

  }

  @Test
  void deletePersonTest() {
    persons.clear();

    // ajout d'une personne
    persons.add(Person.builder().firstName("Brian").lastName("Stelzer").address("947 E. Rose Dr")
        .city("Culver").zip("97451").phone("841-874-7784").email("bstel@email.com")
        .medicalRecord(medicalRecord).build());

    String firstName = "Brian";
    String lastName = "Stelzer";
    // quand delete

    personServiceImpl.deletePerson(firstName, lastName);
    // retrieve le nombre de personne
    // liste vide pour comparer
    List<Person> emptyList = new ArrayList<>();
    assertEquals(emptyList, persons);

  }

  @Test
  void findPersonByAdressTest() {

    String address = "1509 Culver St";
    // quand findByname

    List<String> personsToFind = personServiceImpl.findPersonsByAddressWithInfos(address);

    // retrieve le nombre de personne

    assertThat(personsToFind.contains("John"));
    assertThat(personsToFind.contains("Jacob"));
  }

  @Test
  void findAllEmailTest() {

    List<String> findAllEmail = personServiceImpl.findAllEmail();

    assertEquals(findAllEmail.toString(),
        ("[jaboyd@email.com, drk@email.com, lily@email.com, bstel@email.com]"));

  }

  @Test
  void findAllPersonsInfoTest() {
//rajouter medicalrecords

    List<String> findAllPersonsInfo = personServiceImpl.findAllPersonsInfo();
    // System.out.println(findAllPersonsInfo);

    assertEquals(findAllPersonsInfo.toString(),
        "[Boyd, John, 1509 Culver St, null, jaboyd@email.com, Boyd, Jacob, 1509 Culver St, null, drk@email.com, Cooper, Lily, 489 Manchester St, null, lily@email.com, Stelzer, Brian, 947 E. Rose Dr, null, bstel@email.com]");
  }

  @Test
  void findChildByAddressTest() {
//create lists in order to use medical record
   List<MedicalRecord> medicalRecords = new ArrayList<>();
    List<String> medications = new ArrayList<>();
    List<String> allergies = new ArrayList<>();

    //add medicalRecords
    medications.add("aznol:350mg" + "hydrapermazol:100mg");
    allergies.add("nillacilan");

    medicalRecords.add(MedicalRecord.builder().firstName("John").lastName("Boyd")
        .birthdate("03/06/2008").medications(medications).allergies(allergies).build());

    medicalRecords.add(MedicalRecord.builder().firstName("Jacob").lastName("Boyd")
        .birthdate("03/06/2020").medications(medications).allergies(allergies).build());

    medicalRecords.add(MedicalRecord.builder().firstName("Lily").lastName("Cooper")
        .birthdate("03/06/1994").medications(medications).allergies(allergies).build());

    medicalRecords.add(MedicalRecord.builder().firstName("Brian").lastName("Stelzer")
        .birthdate("12/06/1975").medications(medications).allergies(allergies).build());

  
    //in order to link person and medical records
    for (Person person : persons) {

      for (MedicalRecord medicalRecord : medicalRecords) {

        if (person.getFirstName().equals(medicalRecord.getFirstName())
            && person.getLastName().equals(medicalRecord.getLastName())) {

          person.setMedicalRecord(medicalRecord);
        }
      }
    }
    
   //when findChildByAddress (miss personne à l'adress)
    String address = "1509 Culver St";
    List<String> childrenListByAddress = personServiceImpl.findChildByAddress(address);

    
    
    

    assertEquals(childrenListByAddress.toString(), "[Boyd, John, 14 years, Boyd, John, Boyd, Jacob, Boyd, Jacob, 2 years, Boyd, John, Boyd, Jacob]");
  }

}
