package com.safetynet.safetynetalerts.service;

import java.util.List;

import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.DTO.EmailDTO;
import com.safetynet.safetynetalerts.DTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.DTO.PersonsListByAddressWithStationDTO;
import com.safetynet.safetynetalerts.model.Person;

/**
 * Interface for services operations concerning Person
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
public interface IPersonService {

  /**
   * Get list of all persons from data
   *
   * @return List of Person
   */
  public List<Person> findAll();

  /**
   * Save in data a given Person
   *
   * @param person - Person
   * @return Person
   */
  public Person addPerson(Person person);

  /**
   * Update specific person in data with a new person
   *
   * @param personUpdate - Person
   * @return Person
   */
  public Person updatePerson(Person personUpdate);

  /**
   * Delete Person from data by firstName and lastName
   *
   * @param firstName - String
   * @param lastName - String
   * @return Person
   */
  public List<Person> deletePerson(String firstName, String lastName);

  /**
   * Find a Person from data by firstName and lastName
   *
   * @param firstName - String
   * @param lastName - String
   * @return List of Person
   */
  public List<Person> findByName(String firstname, String lastName);

  /**
   * Get List of persons with informations and Firestation stationNumber, from an address
   *
   * @param address - String
   * @return PersonsListByAddressWithStationDTO
   */
  public PersonsListByAddressWithStationDTO findPersonsByAddressWithInfos(String address);

  /**
   * Get List of emails for all persons by city
   *
   * @param city - String
   * @return List of EmailDTO
   */
  public List<EmailDTO> findAllEmailByCity(String city);

  /**
   * Get all informations for a person(s) by name
   *
   * @param firstName - String
   * @param lastName - String
   * @return List of PersonInfoDTO
   */
  public List<PersonInfoDTO> findAllPersonInfo(String firstName, String lastName);

  /**
   * Get all children at and address with informations
   *
   * @param address - String
   * @return List of ChildDTO
   */
  public List<ChildDTO> findChildByAddress(String address);

}
