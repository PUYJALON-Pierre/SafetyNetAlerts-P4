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
    logger.debug(" Start finding all persons");
    logger.info(" Getting all persons ");
    List<Person> persons = jSonDataBase.getPersons();
    if(persons.isEmpty()) {
    logger.error("No persons found");
    }
    return persons;
  }

  
  @Override
  public Person addPerson(Person personToAdd) {
    logger.debug("Starting adding person");
    List<Person> persons = jSonDataBase.getPersons();
    boolean anyMatch = persons.stream()
        .anyMatch(p -> p.getFirstName().equals(personToAdd.getFirstName())
            && p.getLastName().equals(personToAdd.getLastName()));
    if (!anyMatch) {
      logger.info("Person add because not already existing");
      persons.add(personToAdd);
      jSonDataBase.setPersons(persons);
    } else {
      logger.info("Person not add because already existing");
    }
    return personToAdd;
  }

  @Override
  public Person updatePerson(Person personUpdate) {
    logger.debug("Starting updating person");
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
      logger.info("Person find and updated");
    }

    else {
      logger.error("No more person to udpate by this firstname and lastname");
    }
    return personUpdate;
  }

  @Override
  public Person deletePerson(String firstName, String lastName) {

    Person personToDelete = findByName(firstName, lastName);
    logger.debug("Deleting person");

    if (personToDelete != null) {
      logger.info("Deleting person {} {} ", firstName, lastName);

      List<Person> persons = jSonDataBase.getPersons();
      persons.remove(personToDelete);
      jSonDataBase.setPersons(persons);
    }

    else {
      logger.error("Person {} {} not found", firstName, lastName);

    }
    return personToDelete;
  }

  @Override
  public Person findByName(String firstName, String lastName) {
    logger.debug("Starting finding person by name");
    Optional<Person> optionalPerson = jSonDataBase.getPersons().stream()
        .filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
        .findAny();

    if (optionalPerson.isPresent()) {
      logger.info("Found person {} {} ", firstName, lastName);
      return optionalPerson.get();
    } else {
      logger.error("Error finding person by firstname and lastname, no match");
    }
    return null;
  }

//------------------------------------------------------------------------------------------------------

  @Override
  public List<PersonsByAddressInfosDTO> findPersonsByAddressWithInfos(String address) {
    logger.debug("Starting finding person with informations at address : {}", address);
    /* Create list of DTO with information to stock and return */
    List<PersonsByAddressInfosDTO> personsByAddressInfos = new ArrayList<>();
    List<Person> persons = jSonDataBase.getPersons();
    List<FireStation> fireStations = jSonDataBase.getFirestations();

    // add person informations to list when a person address and address parameters match
    logger.info("Searching person at address : {}", address);
    for (Person person : persons) {
      if (person.getAddress().equals(address)) {

        PersonsByAddressInfosDTO personDTO = PersonsByAddressInfosDTO.builder()
            .address(person.getAddress()).lastName(person.getLastName())
            .firstName(person.getFirstName()).phoneNumber(person.getPhone())
            .birthdate(person.getMedicalRecord().getBirthdate())
            .medications(person.getMedicalRecord().getMedications())
            .allergies(person.getMedicalRecord().getAllergies()).build();

        // add stationNumber to DTO object when fireStation address match address parameter
        for (FireStation fireStation : fireStations) {
          if (fireStation.getAddress().equals(address)) {

            personDTO.setStationNumber(fireStation.getStationNumber());

          }
        }
        personsByAddressInfos.add(personDTO);
      }
    }
    if (personsByAddressInfos.isEmpty()) {
      logger.error("No person found at address : {}", address);
    }

    return personsByAddressInfos;
  }

  @Override
  public List<EmailDTO> findAllEmailByCity(String city) {
    logger.debug("Starting finding all inhabitants email of : {}", city);
    List<EmailDTO> emailList = new ArrayList<>();
    List<Person> persons = jSonDataBase.getPersons();
    logger.info("Creating list of email for all inhabitants");
    for (Person person : persons) {

      if (person.getCity().equals(city)) {

        EmailDTO email = EmailDTO.builder().email(person.getEmail()).build();
        emailList.add(email);
      }
    }

    if (emailList.isEmpty()) {
      logger.error("No mail found for : {}", city);
    }
    return emailList;
  }

  @Override
  public List<PersonInfoDTO> findAllPersonsInfo() {
    logger.debug("Starting finding all inhabitants informations");
    List<PersonInfoDTO> infoList = new ArrayList<>();

    List<Person> persons = jSonDataBase.getPersons();
    logger.info("Creating list of inofrmations for all inhabitants");
    for (Person person : persons) {

      PersonInfoDTO personInfoDTO = PersonInfoDTO.builder().lastName(person.getLastName())
          .firstName(person.getFirstName()).address(person.getAddress())
          .birthdate(person.getMedicalRecord().getBirthdate()).email(person.getEmail())
          .medications(person.getMedicalRecord().getMedications())
          .allergies(person.getMedicalRecord().getAllergies()).build();

      infoList.add(personInfoDTO);
    }
    if (infoList.isEmpty()) {
      logger.error("No person informations");
    }
    return infoList;
  }

  @Override
  public List<ChildDTO> findChildByAddress(String address) {
    logger.debug("Starting finding children at address : {}", address);
    List<ChildDTO> childrenList = new ArrayList<>();
    List<Person> persons = jSonDataBase.getPersons();
    List<Person> personsAtSameHouse = new ArrayList<>();

    logger.info("Searching children at address : {}", address);
    for (Person person : persons) {
      String personAddress = person.getAddress().toString();
      // filter people at this address
      if (personAddress.equals(address)) {

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

          ChildDTO child = ChildDTO.builder().firstName(person.getFirstName())
              .LastName(person.getLastName()).age(age).build();

          childrenList.add(child);
        }
      }
      // setting list of person at same house (nom de la personne aussi pas que les autre problème?)
      for (ChildDTO child : childrenList) {
        child.setPersonsAtSameHouse(personsAtSameHouse);
      }
    }
    if (childrenList.isEmpty()) {
      logger.info("No children found at address : {}", address);
    }
    return childrenList;
  }

}
