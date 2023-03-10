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
      return new ResponseEntity<FireStation>(fireStationToAdd, HttpStatus.BAD_REQUEST);
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
      return new ResponseEntity<FireStation>(fireStationToDelete, HttpStatus.NOT_FOUND);
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
      return new ResponseEntity<List<FireStation>>(fireStations, HttpStatus.NOT_FOUND);
    } else {
    logger.info("Firestation list found");
    return new ResponseEntity<List<FireStation>>(fireStations, HttpStatus.FOUND);}
  }




}
