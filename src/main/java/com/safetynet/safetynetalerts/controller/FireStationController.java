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

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.IFireStationService;

/**
 * Controller class for fireStations CRUD Endpoints
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
@RestController
public class FireStationController {

  final static Logger logger = LogManager.getLogger(FireStationController.class);

  @Autowired
  private IFireStationService iFireStationService;

  /**
   * Creating new fireStation
   *
   * @param fireStation - FireStation object to add
   * @return ResponseEntity FireStation
   */
  @PostMapping(value = "/firestation")
  public ResponseEntity<FireStation> addFireStation(@RequestBody FireStation fireStation) {
    logger.debug("PostMapping firestation {} ", fireStation);
    FireStation fireStationToAdd = iFireStationService.addFireStation(fireStation);

    if (fireStationToAdd == null) {
      logger.error("Error during adding firestation");
      return new ResponseEntity<>(fireStationToAdd, HttpStatus.BAD_REQUEST);
    } else {
      logger.info("Creation of firestation completed or already existing");
      return new ResponseEntity<>(fireStationToAdd, HttpStatus.CREATED);
    }
  }

  /**
   * Updating existing fireStation
   *
   * @param firestationUpdate - FireStation object to update
   * @return ResponseEntity FireStation
   */
  @PutMapping(value = "/firestation")
  public ResponseEntity<FireStation> updateFireStation(@RequestBody FireStation firestationUpdate) {
    logger.debug("PutMapping firestation {} ", firestationUpdate);

    FireStation fireStationToUpdate = iFireStationService.updateStationNumber(firestationUpdate);

    if (fireStationToUpdate == null) {
      logger.error("Error during updating firestation");
      return new ResponseEntity<>(fireStationToUpdate, HttpStatus.NOT_FOUND);
    } else {
      logger.info("Firestation station number updated successfully");
      return new ResponseEntity<>(fireStationToUpdate, HttpStatus.OK);
    }
  }

  /**
   * Delete fireStation by address and station number
   *
   * @param address - FireStationToDelete address
   * @param stationNumber - FireStationToDelete stationNumber
   * @return ResponseEntity FireStation
   */
  @DeleteMapping(value = "/firestation")
  public ResponseEntity<FireStation> deleteFireStation(
      @RequestParam(value = "address") String address,
      @RequestParam(value = "stationNumber") String stationNumber) {
    logger.debug("DeleteMapping firestation {} {}", address, stationNumber);
    FireStation fireStationToDelete = iFireStationService.deleteFireStation(address, stationNumber);

    if (fireStationToDelete == null) {
      logger.error("Error during deleting firestation");
      return new ResponseEntity<>(fireStationToDelete, HttpStatus.NOT_FOUND);
    }

    else {
      logger.info("Firestation deleting successfully");
      return new ResponseEntity<>(fireStationToDelete, HttpStatus.OK);
    }
  }

  /**
   * Retrieve all fireStations
   *
   * @return ResponseEntity (List of FireStation)
   */
  @GetMapping("/firestations")
  public ResponseEntity<List<FireStation>> retrieveAllFireStations() {
    logger.debug("GetMapping of all firestations");

    List<FireStation> fireStations = iFireStationService.findAll();

    if (fireStations.isEmpty()) {
      logger.error("Error during recuperation of firestations");
      return new ResponseEntity<>(fireStations, HttpStatus.NOT_FOUND);
    } else {
      logger.info("Firestation list found");
      return new ResponseEntity<>(fireStations, HttpStatus.FOUND);
    }
  }

}
