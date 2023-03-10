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

import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.DTO.EmailDTO;
import com.safetynet.safetynetalerts.DTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByAddressInfosDTO;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.IPersonService;

@RestController
public class PersonController {

  final static Logger logger = LogManager.getLogger(PersonController.class);

  @Autowired
  private IPersonService iPersonService;

  /************** ENDPOINT FOR UPDATING DATA ***************/

  // Add new person
  @PostMapping(value = "/person")
  public ResponseEntity<Person> addPerson(@RequestBody Person person) {
    logger.debug("PostMapping person {} ", person);
    Person personToAdd = iPersonService.addPerson(person);

    if (personToAdd == null) {
      logger.error("Error during adding person");
      return new ResponseEntity<Person>(personToAdd, HttpStatus.BAD_REQUEST);
    } else {
      logger.info("Creation of person completed or already existing");
      return new ResponseEntity<Person>(personToAdd, HttpStatus.CREATED);
    }
  }

  
  // UpdatePerson by firstName and lastName
  @PutMapping(value = "/person")
  public ResponseEntity<Person> updatePerson(@RequestBody Person personUpdate) {
    logger.debug("PutMapping person {} ", personUpdate);
    Person personToUpdate = iPersonService.updatePerson(personUpdate);

    if (personToUpdate == null) {
      logger.error("Error during person update");
      return new ResponseEntity<Person>(personToUpdate, HttpStatus.NOT_FOUND);
    } else {
      logger.info("Person update successfully");
      return new ResponseEntity<Person>(personToUpdate, HttpStatus.OK);
    }
  } 

  
  // Delete new person by firstName and lastName
  @DeleteMapping(value = "/person")
  public ResponseEntity<Person> deletePersonByFirstName(
      @RequestParam(value = "firstName") String firstName,
      @RequestParam(value = "lastName") String lastName) {
    logger.debug("DeleteMapping firstName {} {}", firstName, lastName);

    Person personToDelete = iPersonService.deletePerson(firstName, lastName);
    if (personToDelete== null) {
      logger.error("Error during deleting person");
      return new ResponseEntity<Person>(personToDelete, HttpStatus.NOT_FOUND);
    } 
    else {
      logger.info("Person deleting successfully");
      return new ResponseEntity<Person>(personToDelete, HttpStatus.OK);
    }
  }

  /************** SPECIFIED ENDPOINTS **************/

  
  // Retrieve all person
  @GetMapping("/persons")
  public ResponseEntity<List<Person>> getPersons() {
    logger.debug("GetMapping of all persons");
    
    List<Person> persons = iPersonService.findAll();

    if (persons.isEmpty()) {
      logger.error("Error during recuperation of persons");
      return new ResponseEntity<List<Person>>(persons, HttpStatus.NOT_FOUND);
    }
    else {logger.info("Persons list founded");
    return new ResponseEntity<List<Person>>(persons, HttpStatus.FOUND);}
  }



}
