package com.safetynet.safetynetalerts.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.DTO.EmailDTO;
import com.safetynet.safetynetalerts.DTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByAddressInfosDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonDataBase;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.IPersonService;

@Service
public class IPersonServiceImpl implements IPersonService {

  final static Logger logger = LogManager.getLogger(IPersonServiceImpl.class);

  @Autowired
  private JsonDataBase jSonDataBase;

  @Override
  public List<Person> findAll() {
    return jSonDataBase.getPersons();

  }

  @Override
  public Person addPerson(Person personToAdd) {
   List<Person>persons = jSonDataBase.getPersons();
   boolean anyMatch = persons.stream().anyMatch(p -> p.getFirstName().equals(personToAdd.getFirstName())
            && p.getLastName().equals(personToAdd.getLastName()));
   if (!anyMatch) {
     
     jSonDataBase.getPersons().add(personToAdd);
   }
   return personToAdd;
  }

  @Override
  public Person updatePerson(Person personUpdate) {

    // Finding person to update with a stream by firstName and lastName
    Optional<Person> optionalPerson = jSonDataBase.getPersons().stream()
        .filter(p -> p.getFirstName().equals(personUpdate.getFirstName())
            && p.getLastName().equals(personUpdate.getLastName()))
        .findAny();

    if (optionalPerson.isPresent()) {

      Person personToUpdate = optionalPerson.get();

      // updating personToUpdate by personUpdate (can't change firstName and lastName)
      personToUpdate.setAddress(personUpdate.getAddress());
      personToUpdate.setCity(personUpdate.getCity());
      personToUpdate.setZip(personUpdate.getZip());
      personToUpdate.setPhone(personUpdate.getPhone());
      personToUpdate.setEmail(personUpdate.getEmail());
      personToUpdate.setMedicalRecord(personUpdate.getMedicalRecord());

      jSonDataBase.setPersons(jSonDataBase.getPersons());
    }

    else {
      System.out.println("No more person to udpate by this firstname and lastname");
    }
    return personUpdate;
  }

  @Override
  public void deletePerson(String firstName, String lastName) {

    Person personToDelete = findByName(firstName, lastName);

    List<Person> persons = jSonDataBase.getPersons();

    persons.remove(personToDelete);

    jSonDataBase.setPersons(persons);
  }

  @Override
  public Person findByName(String firstName, String lastName) {
    Optional<Person> optionalPerson = jSonDataBase.getPersons().stream()
        .filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
        .findAny();

    if (optionalPerson.isPresent()) {

      return optionalPerson.get();
    } else {
      System.out.println("No more finding person by firstname and lastname, no match");
    }
    return null;
  }

//------------------------------------------------------------------------------------------------------

  @Override
  public List<PersonsByAddressInfosDTO> findPersonsByAddressWithInfos(String address) {

    /*(FIRE URL) Create list of DTO with information to stock and return */
    List<PersonsByAddressInfosDTO> personsByAddressInfos = new ArrayList<>();
    List<Person> persons = jSonDataBase.getPersons();
    List<FireStation> fireStations = jSonDataBase.getFirestations();

    // add person informations to list when a person address and address parameters match
    for (Person person : persons) {
      if (person.getAddress() == address) {

       PersonsByAddressInfosDTO personDTO = PersonsByAddressInfosDTO.builder()
           .lastName(person.getLastName())
           .firstName(person.getFirstName())
           .phoneNumber(person.getPhone())
           .birthdate(person.getMedicalRecord().getBirthdate()) 
           .medications(person.getMedicalRecord().getMedications())
           .allergies(person.getMedicalRecord().getAllergies())
           .build();

        // add stationNumber to DTO object when fireStation address match address parameter
        for (FireStation fireStation : fireStations) {
          if (fireStation.getAddress() == address) {

            personDTO.setStationNumber(fireStation.getStationNumber());

          }
        }
        personsByAddressInfos.add(personDTO);
      }
    }
    return personsByAddressInfos;
  }


  @Override
  public List<EmailDTO> findAllEmail() {

    List<EmailDTO> emailList = new ArrayList<>();
    List<Person> persons = jSonDataBase.getPersons();

    for (Person person : persons) {
      EmailDTO email = EmailDTO.builder().email(person.getEmail()).build();
      emailList.add(email);
    }
    return emailList;
  }

  @Override
  public List<PersonInfoDTO> findAllPersonsInfo() {

    List<PersonInfoDTO> infoList = new ArrayList<>();

    List<Person> persons = jSonDataBase.getPersons();

    for (Person person : persons) {

      PersonInfoDTO personInfoDTO = PersonInfoDTO.builder()
          .lastName(person.getLastName())
          .firstName(person.getFirstName())
          .address(person.getAddress())
          .birthdate(person.getMedicalRecord().getBirthdate())
          .email(person.getEmail())
          .medications(person.getMedicalRecord().getMedications())
          .allergies(person.getMedicalRecord().getAllergies())
          .build();

      infoList.add(personInfoDTO);

    }
    return infoList;
  }

  @Override
  public List<ChildDTO> findChildByAddress(String address) {

    List<ChildDTO> childrenList = new ArrayList<>();
    List<Person> persons = jSonDataBase.getPersons();
    List<Person> personsAtSameHouse = new ArrayList<>();
    for (Person person : persons) {

      // filter people at this address
      if (person.getAddress() == address) {

        // adding people to list of person in the house
        
        personsAtSameHouse.add(person);

        // checking if person is a child by calculating age
       
        Date birthdate = null;
        try {
          birthdate = (new SimpleDateFormat("MM/dd/yyyy"))
              .parse(person.getMedicalRecord().getBirthdate());
        } catch (ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        Date actualDate = new Date();
        // Calculating age
        Long ageMillisecond = (actualDate.getTime() - birthdate.getTime());

        // converting age into years
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(ageMillisecond);
        int age = c.get(Calendar.YEAR) - 1970;
        // if child then add informations to list
        if (age <= 18) {

          ChildDTO child = ChildDTO.builder()
              .firstName(person.getFirstName())
              .LastName(person.getLastName())
              .age(age)
              .build();

          childrenList.add(child);
        }
      }
      //setting list of person at same house (nom de la personne aussi pas que les autre problème?)
      for (ChildDTO child : childrenList) {
        child.setPersonsAtSameHouse(personsAtSameHouse);
      }
    }
    return childrenList;
  }

}
