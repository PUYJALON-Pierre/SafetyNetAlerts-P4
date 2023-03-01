package com.safetynet.safetynetalerts.service;

import java.util.List;

import com.safetynet.safetynetalerts.model.MedicalRecord;

public interface IMedicalRecordService {

  /*Create list of all medicalRecords*/
  public List<MedicalRecord> findAll();

  /*Add a medicalRecord */
  public void addMedicalRecord(MedicalRecord medicalRecord);

  /*Update a medicalRecord */
  public void updateMedicalRecord(MedicalRecord medicalRecordUpdate);

  /*Delete a medicalRecord */
  public void deleteMedicalRecord(String firstName, String lastName);

  /*Find a medicalRecord */
  public MedicalRecord findMedicalRecordByName(String firstName, String lastName);
}
