package com.safetynet.safetynetalerts.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.IPersonService;

/**
 * Controller class for persons CRUD Endpoints
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
@RestController
public class PersonController {

  final static Logger logger = LogManager.getLogger(PersonController.class);

  @Autowired
  private IPersonService iPersonService;

  /**
   * Adding new Person
   *
   * @param person - Person object to add
   * @return ResponseEntity Person
   */
  @PostMapping(value = "/person")
  public ResponseEntity<Person> addPerson(@RequestBody Person person) {
    logger.debug("PostMapping person {} ", person);
    Person personToAdd = iPersonService.addPerson(person);

    if (personToAdd == null) {
      logger.error("Error during adding person");
      return new ResponseEntity<>(personToAdd, HttpStatus.BAD_REQUEST);
    } else {
      logger.info("Creation of person completed");
      return new ResponseEntity<>(personToAdd, HttpStatus.CREATED);
    }
  }

  /**
   * Updating existing Person
   *
   * @param personUpdate - Person object to update
   * @return ResponseEntity Person
   */
  @PutMapping(value = "/person")
  public ResponseEntity<Person> updatePerson(@RequestBody Person personUpdate) {
    logger.debug("PutMapping person {} ", personUpdate);
    Person personToUpdate = iPersonService.updatePerson(personUpdate);

    if (personToUpdate == null) {
      logger.error("Error during person update");
      return new ResponseEntity<>(personToUpdate, HttpStatus.NOT_FOUND);
    } else {
      logger.info("Person update successfully");
      return new ResponseEntity<>(personToUpdate, HttpStatus.OK);
    }
  }

  /**
   * Delete person by firstName and lastName
   *
   * @param firstName - Person firstName
   * @param lastName - Person lastName
   * @return ResponseEntity List of Person - Persons deleted
   */
  @DeleteMapping(value = "/person")
  public ResponseEntity<List<Person>> deletePersonByFirstName(
      @RequestParam(value = "firstName") String firstName,
      @RequestParam(value = "lastName") String lastName) {
    logger.debug("DeleteMapping firstName {} {}", firstName, lastName);

    List<Person> personsToDelete = iPersonService.deletePerson(firstName, lastName);
    if (personsToDelete == null) {
      logger.error("Error during deleting person");
      return new ResponseEntity<>(personsToDelete, HttpStatus.NOT_FOUND);
    } else {
      logger.info("Person deleting successfully");
      return new ResponseEntity<>(personsToDelete, HttpStatus.OK);
    }
  }

  /**
   * Retrieve all persons
   *
   * @return ResponseEntity (List of Person)
   */
  @GetMapping("/persons")
  public ResponseEntity<List<Person>> getPersons() {
    logger.debug("GetMapping of all persons");

    List<Person> persons = iPersonService.findAll();

    if (persons.isEmpty()) {
      logger.error("Error during recuperation of persons");
      return new ResponseEntity<>(persons, HttpStatus.NOT_FOUND);
    } else {
      logger.info("Persons list founded");
      return new ResponseEntity<>(persons, HttpStatus.FOUND);
    }
  }

}
