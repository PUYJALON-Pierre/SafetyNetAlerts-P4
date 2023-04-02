package com.safetynet.safetynetalerts;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonDataBase;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

/**
 * Main class of SafetynetalertsApplication, which is a Rest API that can manage informations about
 * inhabitants from a JsonFile and send them to emergency services when needed, in order to prevent
 * emergencies situations.
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 * @version 1.0.0
 */
@SpringBootApplication
public class SafetynetalertsApplication {

  @Autowired
  JsonDataBase jsonDataBase;

  final static Logger logger = LogManager.getLogger(SafetynetalertsApplication.class);

  public static void main(String[] args) {

    logger.debug("Starting SafetynetalertsApplication");
    SpringApplication.run(SafetynetalertsApplication.class, args);

  }

  @Bean
  CommandLineRunner runner() {
    return args -> {

      // Loading Json file

      String filePath = "src/main/resources/data.json";
      byte[] bytesFile = Files.readAllBytes(new File(filePath).toPath());

      // Iterating

      JsonIterator iterator = JsonIterator.parse(bytesFile);
      Any any = iterator.readAny();

      // Charging Persons from file into model object

      Any personToRead = any.get("persons");
      logger.info("Charging Person data from file into model object");
      List<Person> persons = initializePersons(personToRead);
      logger.info("Number of persons loaded ", persons.size());

      // Charging medicalRecords from file into model object

      Any medicalToRead = any.get("medicalrecords");
      logger.info("Charging medicalRecords data from file into model object");
      List<MedicalRecord> medicalRecords = initializeMedicalRecords(medicalToRead);
      logger.info("Number of medicalsRecords loaded ", medicalRecords.size());

      // Charging FireStation from file into model object

      Any fireStationToRead = any.get("firestations");
      logger.info("Charging fireStations data from file into model object");
      List<FireStation> fireStations = initializeFireStations(fireStationToRead);
      logger.info("Number of fireStations loaded ", fireStations.size());

      // Linking medicalRecord to Person

      logger.info("Adding medicalRecords to persons");
      addMedicalRecordToPerson(persons, medicalRecords);

      // Linking Person to FireStation

      logger.info("Adding persons to fireStations");
      addPersonToFireStation(persons, fireStations);

      // Setting jSonDataBase Object with generated lists
      jsonDataBase.setPersons(persons);
      jsonDataBase.setMedicalRecords(medicalRecords);
      jsonDataBase.setFirestations(fireStations);

    };
  }

  /**
   * This method initialize persons by building person objects and returning a list of person, from
   * elements iterated in a JsonFile
   *
   * @param personToRead - Any elements filtered by JsonIterator that concern person
   * @return List of Person
   */
  private List<Person> initializePersons(Any personToRead) {

    // Iterating and building for Persons
    List<Person> persons = new ArrayList<>();
    personToRead.forEach(a -> persons.add(Person.builder().firstName(a.get("firstName").toString())

        .address(a.get("address").toString())

        .city(a.get("city").toString())

        .lastName(a.get("lastName").toString())

        .phone(a.get("phone").toString())

        .zip(a.get("zip").toString())

        .email(a.get("email").toString())

        .build()));

    return persons;

  }

  /**
   * This method initialize medicalRecords by building medicalRecord objects and returning a list of
   * medicalRecords, from elements iterated in a JsonFile
   *
   * @param medicalToRead - Any elements filtered by JsonIterator that concern medicalRecords
   * @return List of MedicalRecord
   */
  private List<MedicalRecord> initializeMedicalRecords(Any medicalToRead) {

    // Iterating and building for Medical Records
    List<MedicalRecord> medicalRecords = new ArrayList<>();
    medicalToRead.forEach(
        a -> medicalRecords.add(MedicalRecord.builder().firstName(a.get("firstName").toString())

            .lastName(a.get("lastName").toString())

            .birthdate(a.get("birthdate").toString())

            .medications(List.of(a.get("medications").toString()))

            .allergies(List.of(a.get("allergies").toString()))

            .build()));

    return medicalRecords;
  }

  /**
   * This method initialize fireStations by building fireStation objects and returning a list of
   * fireStations, from elements iterated in a JsonFile
   *
   * @param firestationToRead - Any elements filetered by JsonIterator that concern fireStations
   * @return List of FireStation
   */
  private List<FireStation> initializeFireStations(Any firestationToRead) {

    // Iterating and building for FireStations
    List<FireStation> fireStations = new ArrayList<>();
    firestationToRead
        .forEach(a -> fireStations.add(FireStation.builder().address(a.get("address").toString())

            .stationNumber(a.get("station").toString())

            .build()));

    return fireStations;

  }

  /**
   * Linking between persons and medicalRecords, by setting a medical record to a person with
   * firstName and lastName
   *
   * @param persons - List of Person
   * @param medicalRecords - List of MedicalRecord
   */
  private void addMedicalRecordToPerson(List<Person> persons, List<MedicalRecord> medicalRecords) {

    for (Person person : persons) {

      for (MedicalRecord medicalRecord : medicalRecords) {

        if (person.getFirstName().equals(medicalRecord.getFirstName())
            && person.getLastName().equals(medicalRecord.getLastName())) {

          person.setMedicalRecord(medicalRecord);
        }
      }
    }

  }

  /**
   * Linking between persons and fireStations, by adding persons to fireStations with address in
   * personsByStation List
   *
   * @param persons - List of Person
   * @param fireStations - List of FireStation
   */
  private void addPersonToFireStation(List<Person> persons, List<FireStation> fireStations) {

    // Linking between persons and fireStations
    for (FireStation firestation : fireStations) {

      List<Person> personsByStation = new ArrayList<>();
      for (Person person : persons) {

        if (firestation.getAddress().equals(person.getAddress())) {

          personsByStation.add(person);
          firestation.setPersonsByStation(personsByStation);
        }
      }
    }

  }


}
