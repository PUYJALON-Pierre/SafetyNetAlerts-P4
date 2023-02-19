package com.safetynet.safetynetalerts.service;

import java.util.List;

import com.safetynet.safetynetalerts.model.MedicalRecord;

public interface IMedicalRecordService {

  /*Faire une liste avec toutes les medicalRecords */
  public List<MedicalRecord> findAll();
  
  /*Ajouter medicalRecord */
  public void addMedicalRecord(MedicalRecord medicalRecord);
  
  /*Update un medicalRecord */
  public void updateMedicalRecord(MedicalRecord medicalRecordUpdate, String firstName, String lastName);
  
  /*Delete un medicalRecord */
  public void deleteMedicalRecord(String firstName, String lastName);
  
  
  /*Find un medicalRecord */
  public MedicalRecord findMedicalRecordByName(String firstName, String lastName);
}
