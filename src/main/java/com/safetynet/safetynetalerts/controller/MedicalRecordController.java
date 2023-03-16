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

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.IMedicalRecordService;

/**
 * Controller class for medicalRecords CRUD Endpoints
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
@RestController
public class MedicalRecordController {

  final static Logger logger = LogManager.getLogger(MedicalRecordController.class);

  @Autowired
  private IMedicalRecordService iMedicalRecordService;

  /**
   * Adding new medicalRecord
   *
   * @param medicalRecordToAdd - MedicalRecord object to add
   * @return ResponseEntity MedicalRecord
   */
  @PostMapping(value = "/medicalRecord")
  public ResponseEntity<MedicalRecord> addMedicalRecord(
      @RequestBody MedicalRecord medicalRecordToAdd) {
    logger.debug("PostMapping medicalRecord {} ", medicalRecordToAdd);
    MedicalRecord medicalRecord = iMedicalRecordService.addMedicalRecord(medicalRecordToAdd);

    if (medicalRecord == null) {
      logger.error("Error during adding medical record");
      return new ResponseEntity<>(medicalRecord, HttpStatus.BAD_REQUEST);
    } else {
      logger.info("Creation of medical record completed");
      return new ResponseEntity<>(medicalRecord, HttpStatus.CREATED);
    }
  }

  /**
   * Updating existing medicalRecord
   *
   * @param medicalRecordToUpdate - MedicalRecord object to update
   * @return ResponseEntity MedicalRecord
   */
  @PutMapping(value = "/medicalRecord")
  public ResponseEntity<MedicalRecord> updateMedicalRecord(
      @RequestBody MedicalRecord medicalRecordToUpdate) {
    logger.debug("PutMapping person {} ", medicalRecordToUpdate);

    MedicalRecord medicalRecord = iMedicalRecordService.updateMedicalRecord(medicalRecordToUpdate);

    if (medicalRecord == null) {
      logger.error("Error during medical record update");
      return new ResponseEntity<>(medicalRecord, HttpStatus.NOT_FOUND);
    } else {
      logger.info("Medical record update successfully");
      return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
    }
  }

  /**
   * Delete medicalRecord by firstName and lastName
   *
   * @param firstName - MedicalRecord firstName
   * @param lastName - MedicalRecord lastName
   * @return ResponseEntity MedicalRecord
   */
  @DeleteMapping(value = "/medicalRecord")
  public ResponseEntity<MedicalRecord> deleteMedicalRecord(
      @RequestParam(value = "firstName") String firstName,
      @RequestParam(value = "lastName") String lastName) {
    logger.debug("DeleteMapping medical record for : {} {}", firstName, lastName);

    MedicalRecord medicalRecordToDelete = iMedicalRecordService.deleteMedicalRecord(firstName,
        lastName);
    if (medicalRecordToDelete == null) {
      logger.error("Error during deleting medical record");
      return new ResponseEntity<>(medicalRecordToDelete, HttpStatus.NOT_FOUND);
    } else {
      logger.info("Medical record deleting successfully");
      return new ResponseEntity<>(medicalRecordToDelete, HttpStatus.OK);
    }
  }

  /**
   * Retrieve all medicalRecords
   *
   * @return ResponseEntity (List of MedicalRecord)
   */
  @GetMapping("/medicalRecords")
  public ResponseEntity<List<MedicalRecord>> medicalRecordsList() {
    logger.debug("GetMapping of all medical records");
    List<MedicalRecord> medicalRecords = iMedicalRecordService.findAll();

    if (medicalRecords.isEmpty()) {
      logger.error("Error during recuperation of medical records");
      return new ResponseEntity<>(medicalRecords, HttpStatus.NOT_FOUND);
    } else {
      logger.info("Medical records list found");
      return new ResponseEntity<>(medicalRecords, HttpStatus.FOUND);
    }
  }

}
