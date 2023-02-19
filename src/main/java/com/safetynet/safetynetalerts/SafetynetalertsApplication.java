package com.safetynet.safetynetalerts;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonDataBase;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootApplication @Configuration
public class SafetynetalertsApplication {

  public static void main(String[] args) {

    final Logger LOGGER = LogManager.getLogger(SafetynetalertsApplication.class);
    LOGGER.debug("Starting SafetynetalertsApplication");

    SpringApplication.run(SafetynetalertsApplication.class, args);
  }

  @Bean
  CommandLineRunner runner() {
    return args -> {

      // Loading Json file
      String filePath = "src/main/resources/data.json";
      byte[] bytesFile = Files.readAllBytes(new File(filePath).toPath());
      JsonDataBase jsonDataBase = new JsonDataBase();
      JsonIterator iterator = JsonIterator.parse(bytesFile);
      Any any = iterator.readAny();

      // Iterating and building for Person
      Any personToRead = any.get("persons");
      List<Person> persons = new ArrayList<>();
      personToRead
          .forEach(a -> persons.add(Person.builder().firstName(a.get("firstName").toString())

              .address(a.get("address").toString())

              .city(a.get("city").toString())

              .lastName(a.get("lastName").toString())

              .phone(a.get("phone").toString())

              .zip(a.get("zip").toString())

              .email(a.get("email").toString())

              .build()));

      // Iterating and building for Medical Records
      Any medicalToRead = any.get("medicalrecords");
      List<MedicalRecord> medicalRecords = new ArrayList<>();
      medicalToRead.forEach(
          a -> medicalRecords.add(MedicalRecord.builder().firstName(a.get("firstName").toString())

              .lastName(a.get("lastName").toString())

              .birthdate(a.get("birthdate").toString())

              .medications(List.of(a.get("medications").toString()))

              .allergies(List.of(a.get("allergies").toString()))

              .build()));

      // Linking between persons and medicalRecords
      for (Person person : persons) {

        for (MedicalRecord medicalRecord : medicalRecords) {

          if (person.getFirstName().equals(medicalRecord.getFirstName())
              && person.getLastName().equals(medicalRecord.getLastName())) {

            person.setMedicalRecord(medicalRecord);
          }
        }
      }

      // Iterating and building for FireStations
      Any firestationToRead = any.get("firestations");
      List<FireStation> fireStations = new ArrayList<>();
      firestationToRead
          .forEach(a -> fireStations.add(FireStation.builder().address(a.get("address").toString())

              .stationNumber(a.get("station").toString())

              .build()));

      // Linking between persons and firestations
      for (FireStation firestation : fireStations) {

        List<Person> personsByStation = new ArrayList<Person>();
        for (Person person : persons) {

          if (firestation.getAddress().equals(person.getAddress())) {

            personsByStation.add(person);
            firestation.setPersonsByStation(personsByStation);
          }
        }
      }
     
      /*Printing person, medicalRecord, fireStation object in order to check that they are
       * correctly building
       * 
       * for (MedicalRecord m : medicalRecords) { System.out.println(m.getFirstName() + "\n" +
       * m.getLastName() + "\n" + m.getBirthdate() + "\n" + m.getMedications() + "\n" +
       * m.getAllergies());
       * System.out.println("------------------------------------------------------------"); }
       * 
       * for (Person p : persons) { System.out.println(p.getFirstName() + "\n" + p.getLastName() +
       * "\n" + p.getAddress() + "\n" + p.getCity() + "\n" + p.getPhone() + "\n" + p.getZip() + "\n"
       * + p.getEmail() + "\n" + p.getMedicalRecord().getAllergies() + "\n" +
       * p.getMedicalRecord().getMedications());
       * 
       * System.out.println("------------------------------------------------------------");
       * 
       * }
       * 
       * for (FireStation f : fireStations) { System.out.println(f.getAddress() + "\n" +
       * f.getStationNumber() + f.getPersonsByStation());
       * 
       * } System.out.println("------------------------------------------------------------");
       */

      jsonDataBase.setPersons(persons);
      jsonDataBase.setMedicalRecords(medicalRecords);
      jsonDataBase.setFirestations(fireStations);
      // return jsonDataBase???

   
      /*
       * System.out.println("nombre de personnes : " + jsonDataBase.getPersons().size());
       * System.out.println("------------------------------------------------------------");
       * System.out.println("nombre de medical records : " +
       * jsonDataBase.getMedicalRecords().size());
       * System.out.println("------------------------------------------------------------");
       * System.out.println("nombre de FireStation : " +jsonDataBase.getFirestations().size());
       * 
       */

    };
  }

}
