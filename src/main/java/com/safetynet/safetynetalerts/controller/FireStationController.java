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

import com.safetynet.safetynetalerts.DTO.PersonsByAddressInfosDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByStationWithCountOfAdultAndChildDTO;
import com.safetynet.safetynetalerts.DTO.PhoneNumberDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.IFireStationService;

@RestController
public class FireStationController {

  final static Logger logger = LogManager.getLogger(FireStationController.class);

  @Autowired
  private IFireStationService iFireStationService;

  /************** ENDPOINT FOR UPDATING DATA ***************/

  // Add new fireStation
  @PostMapping(value = "/firestation")
  public ResponseEntity<FireStation> addFireStation(@RequestBody FireStation fireStation) {
    logger.debug("PostMapping firestation {} ", fireStation);
    FireStation fireStationToAdd = iFireStationService.addFireStation(fireStation);

    if (fireStationToAdd == null) {
      logger.error("Error during adding firestation");
      return new ResponseEntity<FireStation>(fireStationToAdd, HttpStatus.NOT_FOUND);
    } else {
      logger.info("Creation of firestation completed or already existing");
      return new ResponseEntity<FireStation>(fireStationToAdd, HttpStatus.CREATED);
    }
  }

  // UpdatePerson fireStation
  @PutMapping(value = "/firestation")
  public ResponseEntity<FireStation> updateFireStation(@RequestBody FireStation firestationUpdate) {
    logger.debug("PutMapping firestation {} ", firestationUpdate);

    FireStation fireStationToUpdate = iFireStationService.updateStationNumber(firestationUpdate);

    if (fireStationToUpdate == null) {
      logger.error("Error during updating firestation");
      return new ResponseEntity<FireStation>(fireStationToUpdate, HttpStatus.NOT_FOUND);
    } else {
      logger.info("Firestation station number updated successfully");
      return new ResponseEntity<FireStation>(fireStationToUpdate, HttpStatus.OK);
    }
  }

  // Delete new person by firstName and lastName
  @DeleteMapping(value = "/firestation")
  public ResponseEntity<FireStation> deleteFireStation(
      @RequestParam(value = "address") String address,
      @RequestParam(value = "stationNumber") String stationNumber) {
    logger.debug("DeleteMapping firestation {} {}", address, stationNumber);
    FireStation fireStationToDelete = iFireStationService.deleteFireStation(address, stationNumber);

    if (fireStationToDelete == null) {
      logger.error("Error during deleting firestation");
      return new ResponseEntity<FireStation>(fireStationToDelete, HttpStatus.BAD_REQUEST);
    }

    else {
      logger.info("Firestation deleting successfully");
      return new ResponseEntity<FireStation>(fireStationToDelete, HttpStatus.OK);
    }
  }

  /************** SPECIFIED ENDPOINTS **************/

  // Retrieve all fireStations
  @GetMapping("/firestations")
  public ResponseEntity<List<FireStation>> retrieveAllFireStations() {
    logger.debug("GetMapping of all firestations");

    List<FireStation> fireStations = iFireStationService.findAll();

    if (fireStations.isEmpty()) {
      logger.error("Error during recuperation of firestations");
      return new ResponseEntity<List<FireStation>>(fireStations, HttpStatus.BAD_REQUEST);
    } else {
    logger.info("Firestation list found");
    return new ResponseEntity<List<FireStation>>(fireStations, HttpStatus.OK);}
  }

  // Retrieve PersonsByStation WithCount Of Adult And Children(URL n°1) 
  @GetMapping("/firestation")
  public ResponseEntity<PersonsByStationWithCountOfAdultAndChildDTO> findPersonsByStationWithAdultAndChildCount(
      @RequestParam(value = "stationNumber") String stationNumber) {
    logger.debug("GetMapping of all firestations");

    PersonsByStationWithCountOfAdultAndChildDTO personsByStationWithCountOfAdultAndChildDTO = iFireStationService
        .findPersonsByStationWithAdultAndChildCount(stationNumber);

    if (personsByStationWithCountOfAdultAndChildDTO == null) {
      logger.error("Error during recuperation of persons by station with adult and children");
      return new ResponseEntity<PersonsByStationWithCountOfAdultAndChildDTO>(personsByStationWithCountOfAdultAndChildDTO,
          HttpStatus.BAD_REQUEST);
    } else {
    logger.info("Persons by station with adult and children count created");
    return new ResponseEntity<PersonsByStationWithCountOfAdultAndChildDTO>(personsByStationWithCountOfAdultAndChildDTO,
        HttpStatus.CREATED);}

  }

  // Retrieve phoneNumbers by stations(URL n°3)
  @GetMapping("/phoneAlert") 
  public ResponseEntity<List<PhoneNumberDTO>> findPhoneNumbersByStation(
      @RequestParam(value = "stationNumber") String stationNumber) {
    logger.debug("GetMapping of all phonenumbers for firestations number : {}", stationNumber);

    List<PhoneNumberDTO> phoneNumberDTO = iFireStationService
        .findPhoneNumbersByStation(stationNumber);

    if (phoneNumberDTO == null) {
      logger.error("Error during recuperation of person's phonenumbers by station");
      return new ResponseEntity<List<PhoneNumberDTO>>(phoneNumberDTO, HttpStatus.BAD_REQUEST);
    } else {
    logger.info("Persons by station with adult and children count created");
    return new ResponseEntity<List<PhoneNumberDTO>>(phoneNumberDTO, HttpStatus.CREATED);}
  }

  
  // Retrieve Persons by station sort by address (URL n°5)
  @GetMapping("/flood") 
  public ResponseEntity<List<PersonsByAddressInfosDTO>> findAllPersonsSortedByAddressAndStation(
      @RequestParam(value = "stationNumber") String stationNumber) {
    logger.debug("GetMapping of all persons covered by station number : {} , sort by address", stationNumber);
    List<PersonsByAddressInfosDTO> personsByAddressInfosDTO = iFireStationService.findAllPersonsSortedByAddressAndStation(stationNumber);
    
    if (personsByAddressInfosDTO == null) {
      logger.error("Error during recuperation of persons covered by station number : {}", stationNumber);
      return new ResponseEntity<List<PersonsByAddressInfosDTO>>(personsByAddressInfosDTO, HttpStatus.BAD_REQUEST);
    } else {
    logger.info("Persons sorted by address and covered by station number : {} created" ,stationNumber);
    return new ResponseEntity<List<PersonsByAddressInfosDTO>>(personsByAddressInfosDTO, HttpStatus.CREATED);}
    
  }

}
