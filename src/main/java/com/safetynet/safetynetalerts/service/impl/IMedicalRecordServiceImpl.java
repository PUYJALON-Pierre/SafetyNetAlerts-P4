package com.safetynet.safetynetalerts.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.model.JsonDataBase;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.IMedicalRecordService;



  @Service
  public class IMedicalRecordServiceImpl implements IMedicalRecordService {

  @Autowired
  private JsonDataBase jSonDataBase;

  @Override
  public List<MedicalRecord> findAll() {

    return jSonDataBase.getMedicalRecords();
  }

  @Override
  public void addMedicalRecord(MedicalRecord medicalRecord) {

    List<MedicalRecord> medicalRecords = jSonDataBase.getMedicalRecords();

    medicalRecords.add(medicalRecord);

    jSonDataBase.setMedicalRecords(medicalRecords);

  }

  @Override
  public void updateMedicalRecord(MedicalRecord medicalRecordUpdate) {

    //Finding medicalRecord to update with a stream by firstName and lastName
    Optional<MedicalRecord> optionalMedicalRecord = jSonDataBase.getMedicalRecords().stream().filter(p -> p.getFirstName().equals(medicalRecordUpdate.getFirstName())&& p.getLastName().equals(medicalRecordUpdate.getLastName())).findAny();

    if (optionalMedicalRecord.isPresent()) {

     MedicalRecord medicalRecordToUpdate = optionalMedicalRecord.get();

     //updating personToUpdate by personUpdate (expect firstName an d lastName)
     medicalRecordToUpdate.setBirthdate(medicalRecordUpdate.getBirthdate());
     medicalRecordToUpdate.setMedications(medicalRecordUpdate.getMedications());
     medicalRecordToUpdate.setAllergies(medicalRecordUpdate.getAllergies());
    }
    else { System.out.println("Error finding medicalRecord to udpate, no match by firstname and lastname");}
  }


  @Override
  public void deleteMedicalRecord(String firstName, String lastName) {

    MedicalRecord medicalRecordToDelete = findMedicalRecordByName(firstName, lastName);

    List<MedicalRecord> medicalRecords = jSonDataBase.getMedicalRecords();

    medicalRecords.remove(medicalRecordToDelete);

    jSonDataBase.setMedicalRecords(medicalRecords);
  }



  @Override
  public MedicalRecord findMedicalRecordByName(String firstName, String lastName) {

      Optional<MedicalRecord> optionalMedicalRecord = jSonDataBase.getMedicalRecords().stream().filter(p -> p.getFirstName().equals(firstName)&& p.getLastName().equals(lastName)).findAny();
      if (optionalMedicalRecord.isPresent()) {

        return optionalMedicalRecord.get();
      }
      System.out.println("Error finding person by firstname and lastname, no match");
      return null;

  }

}
