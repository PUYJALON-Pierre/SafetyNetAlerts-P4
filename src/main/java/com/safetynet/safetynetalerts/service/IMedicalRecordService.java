package com.safetynet.safetynetalerts.service;

import java.util.List;

import com.safetynet.safetynetalerts.model.MedicalRecord;

/**
 * Interface for services operations concerning MedicalRecord
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
public interface IMedicalRecordService {

  /**
   * Get list of all MedicalRecords from data
   *
   * @return List of MedicalRecord
   */
  public List<MedicalRecord> findAll();

  /**
   * Save in data a given medicalRecord
   *
   * @param medicalRecord - MedicalRecord
   * @return MedicalRecord
   */
  public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord);

  /**
   * Update specific medicalRecord in data with a new medicalRecord
   *
   * @param medicalRecordUpdate - MedicalRecord
   * @return MedicalRecord
   */
  public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecordUpdate);

  /**
   * Delete MedicalRecord from data by firstName and lastName
   *
   * @param firstName - String
   * @param lastName - String
   * @return MedicalRecord
   */
  public MedicalRecord deleteMedicalRecord(String firstName, String lastName);

  /**
   * Find a MedicalRecord from data by firstName and lastName
   *
   * @param firstName - String
   * @param lastName - String
   * @return MedicalRecord
   */
  public MedicalRecord findMedicalRecordByName(String firstName, String lastName);
}
