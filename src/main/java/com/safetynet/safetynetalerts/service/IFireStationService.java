package com.safetynet.safetynetalerts.service;

import java.util.List;

import com.safetynet.safetynetalerts.DTO.PersonsByAddressInfosDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByStationWithCountOfAdultAndChildDTO;
import com.safetynet.safetynetalerts.DTO.PhoneNumberDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.Person;


public interface IFireStationService {


  /*Create list of all FireStations*/
  public List<FireStation> findAll();

  /*Add a FireStation*/
  public void addFireStation(FireStation fireStationToAdd);

  /*Update FireStation by address and stationNumber */
  public void updateStationNumber(FireStation fireStation);

  /*Delete FireStation by address and stationNumber*/
  public void deleteFireStation(String address, String stationNumber);

  /*Create list of all people covered by a FireStations*/
  public List <PersonsByAddressInfosDTO> findAllPersonsSortedByAddressAndStation(String stationNumber);

  /*Create list of string of all people covered by a FireStation number with informations*/
  public PersonsByStationWithCountOfAdultAndChildDTO findPersonsByStationWithAdultAndChildCount(String stationNumber);

  /*Create list of all people's phoneNumbers covered by a FireStation number*/
  public List<PhoneNumberDTO> findPhoneNumbersByStation(String stationNumber);

  /*Create list of person covered by a FireStation number*/
  public List <Person> findPersonsByStation(String stationNumber);

}

