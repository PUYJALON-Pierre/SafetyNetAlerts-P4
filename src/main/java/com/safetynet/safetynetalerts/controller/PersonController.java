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
      return new ResponseEntity<Person>(personToAdd, HttpStatus.NOT_FOUND);
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

    if (personUpdate == null) {
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
      return new ResponseEntity<Person>(personToDelete, HttpStatus.BAD_REQUEST);
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
      return new ResponseEntity<List<Person>>(persons, HttpStatus.BAD_REQUEST);
    }
    else {logger.info("Persons list created");
    return new ResponseEntity<List<Person>>(persons, HttpStatus.CREATED);}
  }

  // Retrieve Children at an address (URL n°2)  
  @GetMapping("/childAlert")
  public ResponseEntity<List<ChildDTO>> findChildByAddress(@RequestParam (value = "address") String address) {
    logger.debug("GetMapping of children at address : {}", address);
   
    List<ChildDTO> childDTO = iPersonService.findChildByAddress(address);
    if (childDTO == null) {
     logger.error("Error finding children at address : {}", address);
      return new ResponseEntity<List<ChildDTO>>(childDTO, HttpStatus.BAD_REQUEST);
    } 
    else {
      logger.info("Children list at address : {} created", address);
      return new ResponseEntity<List<ChildDTO>>(childDTO, HttpStatus.CREATED);}
  }
  
  // Retrieve persons by address with station number (URL n°4) 
  @GetMapping("/fire")
  public ResponseEntity<List<PersonsByAddressInfosDTO>> findPersonsByAddressWithInfos(
      @RequestParam (value = "address") String address) {
    logger.debug("GetMapping of persons by address with informations");
    
    List<PersonsByAddressInfosDTO> personByAddressInfos =iPersonService.findPersonsByAddressWithInfos(address);
    if (personByAddressInfos == null) {
      logger.error("Error finding persons informations at address : {}", address);
       return new ResponseEntity<List<PersonsByAddressInfosDTO>>(personByAddressInfos, HttpStatus.BAD_REQUEST);
     } else {
    logger.info("Persons informations at address : {} created", address);
    return new ResponseEntity<List<PersonsByAddressInfosDTO>>(personByAddressInfos, HttpStatus.CREATED);}
  }

  // Retrieve all person informations (URL n°6) pas certain d'ecriture uri trier par firstname et lastname???
  @GetMapping("/personInfo")
  public ResponseEntity<List<PersonInfoDTO>> findAllPersonsInfo () {
    logger.debug("GetMapping of all persons informations");
    List<PersonInfoDTO> personInfoDTO =iPersonService.findAllPersonsInfo();
    
    if (personInfoDTO == null) {
      logger.error("Error finding persons informations at address ");
       return new ResponseEntity<List<PersonInfoDTO>>(personInfoDTO, HttpStatus.BAD_REQUEST);
     } else {
       logger.info("Persons informations at address : {} created");
       return new ResponseEntity<List<PersonInfoDTO>>(personInfoDTO, HttpStatus.CREATED);
     }
  }

  // Retrieve all mails (URL n°7)
  @GetMapping("/communityEmail")
  public ResponseEntity<List<EmailDTO>> findAllEmail(@RequestParam String city) {
    logger.debug("GetMapping of all email of inhabitants");
    List<EmailDTO> emailDTO = iPersonService.findAllEmailByCity(city);
    
    if (emailDTO == null) {
      logger.error("Error finding persons email ");
      return new ResponseEntity<List<EmailDTO>>(emailDTO, HttpStatus.BAD_REQUEST);
    }
    else {
      logger.info("Persons email list created");
      return new ResponseEntity<List<EmailDTO>>(emailDTO, HttpStatus.CREATED);
    }
  }

}
