package com.safetynet.safetynetalerts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.DTO.EmailDTO;
import com.safetynet.safetynetalerts.DTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByAddressInfosDTO;
import com.safetynet.safetynetalerts.model.Person;

@Service
public interface IPersonService {

  /*Create a List of all persons*/
  public List<Person> findAll();

  /*Add a person */
  public Person addPerson (Person person);

  /*Update a person by firstName and LastName */
  public Person updatePerson (Person personUpdate);

  /*Delete a person by firstName and LastName */
  public Person deletePerson (String firstName, String lastName);

  /*Find a person by firstName and LastName*/
  public Person findByName(String firstname, String lastName);

  /*(URL Fire) Create a List of DTO persons from an address with informations */
  public List<PersonsByAddressInfosDTO> findPersonsByAddressWithInfos (String address);

  /*(URL Email)Find email for all persons*/
  public List<EmailDTO> findAllEmailByCity(String city);

  /* (URL N°6)Create a List of all informations for each person */
  public List<PersonInfoDTO> findAllPersonsInfo();

  /*Create a List of children to an address */
  public List<ChildDTO> findChildByAddress (String address);

}
