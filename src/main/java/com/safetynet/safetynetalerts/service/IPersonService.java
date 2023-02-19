package com.safetynet.safetynetalerts.service;

import java.util.List;

import com.safetynet.safetynetalerts.model.Person;

public interface IPersonService {

  
  /*Create a List of all persons*/
  public List<Person> findAll();
  
  
  /*Add a person */
  public void addPerson (Person person);
  
  
  /*Update a person by firstName and LastName */
  public void updatePerson (Person person, String firstName, String lastName);
  

  /*Delete a person by firstName and LastName */
  public void deletePerson (String firstName, String lastName);
  
  
  /*Find a person by firstName and LastName*/
  public Person findByName(String firstname, String lastName);
  
  
  /*Create a List of persons to an address with informations */
  public List <String> findPersonsByAddressWithInfos (String address);
  
  /*Create a List of children to an address */
  public List<String> findChildrenAtSameHouseByAddress(String address);
  
  /*Find email for all persons*/
  public List<String> findAllEmail();
  
  
  /*Create a List of all informations of everyone */
  public List<String> findAllPersonsInfo();
  
  
  /*Create a List of persons to an address */
  public List <String> findChildByAddress (String address);
  
}
