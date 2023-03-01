package com.safetynet.safetynetalerts.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonDataBase;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class PersonServiceImpl implements IPersonService {

  @Autowired
  private JsonDataBase jSonDataBase;

  @Override
  public List<Person> findAll() {
    return jSonDataBase.getPersons();
  }

  @Override
  public void addPerson(Person personToAdd) {

    List<Person> persons = jSonDataBase.getPersons();

    persons.add(personToAdd);

    jSonDataBase.setPersons(persons);

  }

  @Override
  public void updatePerson(Person personUpdate, String firstName, String lastName) {

    // Finding person to update with a stream by firstName and lastName
    Optional<Person> optionalPerson = jSonDataBase.getPersons().stream()
        .filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
        .findAny();

    if (optionalPerson.isPresent()) {

      Person personToUpdate = optionalPerson.get();

      // updating personToUpdate by personUpdate (expect firstName and lastName)
      personToUpdate.setAddress(personUpdate.getAddress());
      personToUpdate.setCity(personUpdate.getCity());
      personToUpdate.setZip(personUpdate.getZip());
      personToUpdate.setPhone(personUpdate.getPhone());
      personToUpdate.setEmail(personUpdate.getEmail());
      personToUpdate.setMedicalRecord(personUpdate.getMedicalRecord());

      // Rajouter set person??
      jSonDataBase.setPersons(jSonDataBase.getPersons());
    }

    else {
      System.out.println("No more person to udpate by firstname and lastname");
    }
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
  public List<String> findPersonsByAddressWithInfos(String address) {
    /* Create list of string with information to stock and return */
    List<String> personsByAddressInfos = new ArrayList<>();
    List<Person> persons = jSonDataBase.getPersons();
    List<FireStation> fireStations = jSonDataBase.getFirestations();

    // add person informations to list when a person address and address parameters match
    for (Person person : persons) {
      if (person.getAddress() == address) {

        personsByAddressInfos.add(person.getLastName());
        personsByAddressInfos.add(person.getFirstName());
        personsByAddressInfos.add(person.getPhone());
        personsByAddressInfos.add(person.getMedicalRecord().getBirthdate());
        personsByAddressInfos.addAll(person.getMedicalRecord().getMedications());
        personsByAddressInfos.addAll(person.getMedicalRecord().getAllergies());

        // add stationNumber to list when a person address and fireStation address match
        for (FireStation fireStation : fireStations) {
          if (fireStation.getAddress() == address) {

            personsByAddressInfos.add(fireStation.getStationNumber());
          }
        }
      }
    }
    return personsByAddressInfos;
  }

  
  
  @Override
  public List<String> findChildrenAtSameHouseByAddress(String address) {
    /* Create list of string with information to stock and return */
    List<String> personsAtSameHouse = new ArrayList<>();
    List<Person> persons = jSonDataBase.getPersons();
    List<FireStation> fireStations = jSonDataBase.getFirestations();

    // add person informations to list when a person address and address parameters match
    for (Person person : persons) {
      if (person.getAddress() == address) {

        personsAtSameHouse.add(person.getLastName());
        personsAtSameHouse.add(person.getFirstName());
  

        // add stationNumber to list when a person address and fireStation address match
        for (FireStation fireStation : fireStations) {
          if (fireStation.getAddress() == address) {

            personsAtSameHouse.add(fireStation.getStationNumber());
          }
        }
      }
    }
    return personsAtSameHouse;
  }
  
  
  
  
  @Override
  public List<String> findAllEmail() {

    List<String> emailList = new ArrayList<>();
    List<Person> persons = jSonDataBase.getPersons();

    for (Person person : persons) {
      emailList.add(person.getEmail());
    }

    return emailList;
  }

  @Override
  public List<String> findAllPersonsInfo() {

    List<String> infoList = new ArrayList<>();

    List<Person> persons = jSonDataBase.getPersons();

    for (Person person : persons) {
      infoList.add(person.getLastName());
      infoList.add(person.getFirstName());
      infoList.add(person.getAddress());
      infoList.add(person.getMedicalRecord().getBirthdate());
      infoList.add(person.getEmail());
      infoList.addAll(person.getMedicalRecord().getMedications());
      infoList.addAll(person.getMedicalRecord().getAllergies());
      // infoList.add("--------------------------------------------------------------------------");
    }
    return infoList;
  }

  @Override
  public List<String> findChildByAddress(String address) {

    List<String> childrenList = new ArrayList<>();

    List<Person> persons = jSonDataBase.getPersons();

    for (Person person : persons) {

      // filter people at this address
      if (person.getAddress() == address) {

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
        int age = c.get(Calendar.YEAR)-1970;
        // if child then add informations to list
        if (age <= 18) {
          childrenList.add(person.getLastName());
          childrenList.add(person.getFirstName());
          childrenList.add(age + " years");
          childrenList.addAll(findChildrenAtSameHouseByAddress(address));
        }
      }
    }
    return childrenList;
  }
}

