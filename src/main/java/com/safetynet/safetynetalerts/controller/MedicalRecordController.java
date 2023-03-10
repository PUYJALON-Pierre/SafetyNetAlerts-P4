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

@RestController
public class MedicalRecordController {

  final static Logger logger = LogManager.getLogger(MedicalRecordController.class);
  
  @Autowired
  private IMedicalRecordService iMedicalRecordService;

  /************** ENDPOINT FOR UPDATING DATA ***************/

  // Add new medicalRecord
  @PostMapping(value = "/medicalRecord")
  public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecordToAdd) {
    logger.debug("PostMapping medicalRecord {} ", medicalRecordToAdd);
    MedicalRecord medicalRecord = iMedicalRecordService.addMedicalRecord(medicalRecordToAdd);
    
    if (medicalRecord == null) {
      logger.error("Error during adding medical record");
      return new ResponseEntity<MedicalRecord>(medicalRecord, HttpStatus.BAD_REQUEST);
    } else {
      logger.info("Creation of medical record completed or already existing");
      return new ResponseEntity<MedicalRecord>(medicalRecord, HttpStatus.CREATED);
    }
  }

 
  // Update medicalRecord by firstName and lastName
  @PutMapping(value = "/medicalRecord")
  public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecordToUpdate) {
    logger.debug("PutMapping person {} ", medicalRecordToUpdate);
    
    MedicalRecord medicalRecord = iMedicalRecordService.updateMedicalRecord(medicalRecordToUpdate);
    
    if (medicalRecord == null) {
      logger.error("Error during medical record update");
      return new ResponseEntity<MedicalRecord>(medicalRecord, HttpStatus.NOT_FOUND);
    } else {
      logger.info("Medical record update successfully");
      return new ResponseEntity<MedicalRecord>(medicalRecord, HttpStatus.OK);
    }
  }

  
  // Delete new person by firstName and lastName
  @DeleteMapping(value = "/medicalRecord")
  public ResponseEntity<MedicalRecord> deleteMedicalRecord(
      @RequestParam(value = "firstName") String firstName,
      @RequestParam(value = "lastName") String lastName) {
    logger.debug("DeleteMapping medical record for : {} {}", firstName, lastName);
    
    MedicalRecord medicalRecordToDelete= iMedicalRecordService.deleteMedicalRecord(firstName, lastName);
    if (medicalRecordToDelete== null) {
      logger.error("Error during deleting medical record");
      return new ResponseEntity<MedicalRecord>(medicalRecordToDelete, HttpStatus.NOT_FOUND);
    } 
    else {
      logger.info("Medical record deleting successfully");
      return new ResponseEntity<MedicalRecord>(medicalRecordToDelete, HttpStatus.OK);
    }
  }

  
  /************** SPECIFIED ENDPOINTS **************/

  // Retrieve all medicalRecords
  @GetMapping("/medicalRecords")
  public ResponseEntity<List<MedicalRecord>> medicalRecordsList() {
    logger.debug("GetMapping of all medical records");
    List<MedicalRecord> medicalRecords = iMedicalRecordService.findAll();
    
    if (medicalRecords.isEmpty()) {
      logger.error("Error during recuperation of medical records");
      return new ResponseEntity<List<MedicalRecord>>(medicalRecords, HttpStatus.NOT_FOUND);
    }
    else {logger.info("Medical records list found");
    return new ResponseEntity<List<MedicalRecord>>(medicalRecords, HttpStatus.FOUND);}  
  }

}
