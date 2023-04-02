package com.safetynet.safetynetalerts.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.model.JsonDataBase;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.IMedicalRecordService;

@Service
public class IMedicalRecordServiceImpl implements IMedicalRecordService {

  final static Logger logger = LogManager.getLogger(IMedicalRecordServiceImpl.class);

  @Autowired
  private JsonDataBase jSonDataBase;

  @Override
  public List<MedicalRecord> findAll() {

    logger.debug(" Start finding all medical records");
    logger.info(" Getting all medical records ");
    List<MedicalRecord> medicalRecords = jSonDataBase.getMedicalRecords();
    if (medicalRecords.isEmpty()) {
      logger.error("No medical record founded");
    }
    return medicalRecords;
  }

  @Override
  public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
    logger.debug("Starting adding medicalRecord");
    List<MedicalRecord> medicalRecords = jSonDataBase.getMedicalRecords();

    logger.info("Medical record add");
    medicalRecords.add(medicalRecord);
    jSonDataBase.setMedicalRecords(medicalRecords);

    if (medicalRecord.equals(null)) {
      logger.error("MedicalRecord to add is null");
    }
    return medicalRecord;
  }

  @Override
  public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecordUpdate) {
    logger.debug("Starting updating medical record");
    // Finding medicalRecord to update with a stream by firstName and lastName
    Optional<MedicalRecord> optionalMedicalRecord = jSonDataBase.getMedicalRecords().stream()
        .filter(p -> p.getFirstName().equals(medicalRecordUpdate.getFirstName())
            && p.getLastName().equals(medicalRecordUpdate.getLastName()))
        .findAny();

    MedicalRecord medicalRecordToUpdate = null;
    if (optionalMedicalRecord.isPresent()) {

      medicalRecordToUpdate = optionalMedicalRecord.get();

      // updating personToUpdate by personUpdate (expect firstName and lastName)
      medicalRecordToUpdate.setBirthdate(medicalRecordUpdate.getBirthdate());
      medicalRecordToUpdate.setMedications(medicalRecordUpdate.getMedications());
      medicalRecordToUpdate.setAllergies(medicalRecordUpdate.getAllergies());
      logger.info("Medical record find and updated");
    } else {
      logger.error("Error finding medicalRecord to udpate, no match by firstname and lastname");
    }
    return medicalRecordToUpdate;
  }

  @Override
  public MedicalRecord deleteMedicalRecord(String firstName, String lastName) {
    logger.debug("Deleting medical record");
    MedicalRecord medicalRecordToDelete = findMedicalRecordByName(firstName, lastName);

    if (medicalRecordToDelete != null) {

      List<MedicalRecord> medicalRecords = jSonDataBase.getMedicalRecords();
      medicalRecords.remove(medicalRecordToDelete);
      jSonDataBase.setMedicalRecords(medicalRecords);
      logger.info("Deleting medical record for : {} , {} ", firstName, lastName);
    }

    else {
      logger.error("Medical record for {}, {} not found", firstName, lastName);
    }
    return medicalRecordToDelete;
  }

  @Override
  public MedicalRecord findMedicalRecordByName(String firstName, String lastName) {
    logger.debug("Starting finding medical record by name");

    Optional<MedicalRecord> optionalMedicalRecord = jSonDataBase.getMedicalRecords().stream()
        .filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
        .findAny();
    if (optionalMedicalRecord.isPresent()) {
      logger.info("Found medical record for : {}, {} ", firstName, lastName);
      return optionalMedicalRecord.get();
    } else {
      logger.info("Error finding medical record for {}, {}", firstName, lastName);
    }
    return null;
  }
}
