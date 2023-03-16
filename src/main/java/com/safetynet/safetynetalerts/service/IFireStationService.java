package com.safetynet.safetynetalerts.service;

import java.util.List;
import java.util.Set;

import com.safetynet.safetynetalerts.DTO.PersonsByAddressInfosDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByStationWithCountOfAdultAndChildDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.Person;

/**
 * Interface for services operations concerning FireStation
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
public interface IFireStationService {

  /**
   * Get list of all FireStations from data
   *
   * @return List of FireStation
   */
  public List<FireStation> findAll();

  /**
   * Save in data a given fireStation
   *
   * @param fireStationToAdd - FireStation
   * @return FireStation
   */
  public FireStation addFireStation(FireStation fireStationToAdd);

  /**
   * Update fireStation number in data with a new FireStation
   *
   * @param fireStation - FireStation
   * @return FireStation
   */
  public FireStation updateStationNumber(FireStation fireStation);

  /**
   * Delete FireStation from data by address and stationNumber
   *
   * @param address - String
   * @param stationNumber - String
   * @return FireStation
   */
  public FireStation deleteFireStation(String address, String stationNumber);

  /**
   * Get persons covered by FireStations
   *
   * @param stationNumberList - List of String
   * @return List of PersonsByAddressInfosDTO
   */
  public List<PersonsByAddressInfosDTO> findAllPersonsSortedByAddressAndStation(
      List<String> stationNumberList);

  /**
   * Get persons from a FireStation with count of children and adult
   *
   * @param stationNumber - String
   * @return PersonsByStationWithCountOfAdultAndChildDTO
   */
  public PersonsByStationWithCountOfAdultAndChildDTO findPersonsByStationWithAdultAndChildCount(
      String stationNumber);

  /**
   * Get phoneNumbers covered by a FireStation number
   *
   * @param stationNumber - String
   * @return Set of String
   */
  public Set<String> findPhoneNumbersByStation(String stationNumber);

  /**
   * Get list of persons covered by a fireStation
   *
   * @param stationNumber - String
   * @return List of Person
   */
  public List<Person> findPersonsByStation(String stationNumber);

  /**
   * Get a FireStation by address and stationNumber
   *
   * @param address - String
   * @param stationNumber - String
   * @return FireStation
   */
  public FireStation findStationByAddress(String address, String stationNumber);

}
