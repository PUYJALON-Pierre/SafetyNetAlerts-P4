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
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.DTO.PersonCoveredByStationNumberDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByAddressInfosDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByStationWithCountOfAdultAndChildDTO;
import com.safetynet.safetynetalerts.DTO.PhoneNumberDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonDataBase;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.IFireStationService;

@Service
public class IFireStationServiceImpl implements IFireStationService {

  final static Logger logger = LogManager.getLogger(IFireStationServiceImpl.class);

  @Autowired
  private JsonDataBase jSonDataBase;

  @Override
  public List<FireStation> findAll() {
    return jSonDataBase.getFirestations();
  }

  @Override
  public FireStation addFireStation(FireStation fireStation) {
    logger.debug("Adding firestation");
    List<FireStation> fireStations = jSonDataBase.getFirestations();

    boolean anyMatch = fireStations.stream()
        .anyMatch(p -> p.getAddress().equals(fireStation.getAddress())
            && p.getStationNumber().equals(fireStation.getStationNumber()));
    if (!anyMatch) {
      logger.info("Firestation add because not already existing");
      fireStations.add(fireStation);
      jSonDataBase.setFirestations(fireStations);
    } else {
      logger.info("Firestation not add because already existing");
    }
    return fireStation;
  }

  @Override
  public FireStation updateStationNumber(FireStation fireStation) {
    logger.debug("Updating firestation number");
    // Finding fireStation number to update with a stream filter by address
    Optional<FireStation> optionalFireStation = jSonDataBase.getFirestations().stream()
        .filter(p -> p.getAddress().equals(fireStation.getAddress())).findAny();

    // If match found, set new stationNumber to update
    if (optionalFireStation.isPresent()) {
      optionalFireStation.get().setStationNumber(fireStation.getStationNumber());
      logger.info("Firestation found and updated");
    } else {
      logger.error("Error finding firestation number to udpate, no match found");
    }
    return fireStation;
  }

  @Override
  public FireStation deleteFireStation(String address, String stationNumber) {

    FireStation fireStationToDelete = findStationByAddress(address, stationNumber);
    logger.debug("Starting deleting firestation {} {} ", address, stationNumber);

    if (fireStationToDelete != null) {
      logger.info("Deleting firestation {} {} ", address, stationNumber);

      List<FireStation> fireStations = jSonDataBase.getFirestations();
      fireStations.remove(fireStationToDelete);
      jSonDataBase.setFirestations(fireStations);
    } else {
      logger.error(
          "Error finding firestation to delete, no match found by address and station number");

    }
    return fireStationToDelete;
  }

  @Override
  public FireStation findStationByAddress(String address, String stationNumber) {
    logger.debug("Finding firestation by address : {} and number : {}", address, stationNumber);
    // Finding fireStation to delete with a stream filter by address and stationNumber
    Optional<FireStation> optionalFireStation = jSonDataBase.getFirestations().stream()
        .filter(p -> p.getAddress().equals(address) && p.getStationNumber().equals(stationNumber))
        .findAny();

    // If match found, remove it and set to delete
    if (optionalFireStation.isPresent()) {
      logger.info("Found firestation with address {} and number {} ", address, stationNumber);
      return optionalFireStation.get();
    } else {
      logger.error("No more finding firestations by address and station number, no match");
    }
    return null;
  }

  // ---------------------------------------------------------------------------------------------------

  @Override
  public List<PersonsByAddressInfosDTO> findAllPersonsSortedByAddressAndStation(
      String stationNumber) {
    logger.debug("Start finding persons from station {} sorted by address", stationNumber);
    List<PersonsByAddressInfosDTO> personsByStationSortByAddress = new ArrayList<>();
    List<Person> personsByStation = findPersonsByStation(stationNumber);

    //in order to sort by address (useless??)
    personsByStation.sort((t1, t2) -> {
      return t1.getAddress().compareTo(t2.getAddress());
    });
    logger.info("Creating information of person from station number {} ",  stationNumber);
    for (Person person : personsByStation) {
      PersonsByAddressInfosDTO personSortByAddressDTO = PersonsByAddressInfosDTO.builder()
          .address(person.getAddress()).lastName(person.getLastName())
          .firstName(person.getFirstName()).phoneNumber(person.getPhone())
          .birthdate(person.getMedicalRecord().getBirthdate())
          .medications(person.getMedicalRecord().getMedications())
          .allergies(person.getMedicalRecord().getAllergies()).stationNumber(stationNumber).build();

      personsByStationSortByAddress.add(personSortByAddressDTO);
    }
   
    if (personsByStationSortByAddress.isEmpty()) {
      logger.error("Error, no persons found for station number : {}", stationNumber);
    }

    return personsByStationSortByAddress;
  }

  @Override
  public PersonsByStationWithCountOfAdultAndChildDTO findPersonsByStationWithAdultAndChildCount(
      String stationNumber) {
    logger.debug("Start finding persons from station {} with adult and children count", stationNumber);
    
    List<Person> personsByStation = findPersonsByStation(stationNumber);
    List<PersonCoveredByStationNumberDTO> personsByStationWithInfos = new ArrayList<>();
    PersonsByStationWithCountOfAdultAndChildDTO personsByStationWithInfosAndCount = null;

    // variables in order to count adult and children
    int numberChildren = 0;
    int numberAdult = 0;
    
    logger.info("Searching adults and children from station number {} ",  stationNumber);
    for (Person person : personsByStation) {

      PersonCoveredByStationNumberDTO personDTO = PersonCoveredByStationNumberDTO.builder()
          .lastName(person.getLastName()).firstName(person.getFirstName())
          .address(person.getAddress()).phoneNumber(person.getPhone()).build();

      personsByStationWithInfos.add(personDTO);

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

      // if child then add increment count
      if (age <= 18) {

        numberChildren++;
      } else {
        numberAdult++;
      }
    }

    // add count of adult and children to list
    personsByStationWithInfosAndCount = PersonsByStationWithCountOfAdultAndChildDTO.builder()
        .personListByStationNumber(personsByStationWithInfos).numberChildren(numberChildren)
        .numberAdult(numberAdult).build();

    if (personsByStationWithInfosAndCount == null) {
      logger.error("Error, no persons found for station number : {}", stationNumber);
    }
    
    
    return personsByStationWithInfosAndCount;
  }

  @Override
  public List<Person> findPersonsByStation(String stationNumber) {
    logger.debug("Start finding persons from station {} ", stationNumber);
    
    List<Person> persons = jSonDataBase.getPersons();
    List<FireStation> fireStations = jSonDataBase.getFirestations();
    List<Person> personsByStation = new ArrayList<>();
    
    logger.info("Searching persons from station number {} ",  stationNumber);
    for (FireStation fireStation : fireStations) {
      /*
       * if stationNumber is equal to fireStationNumber, return a list of persons covered by this
       * FireStation which has been build during Json File charging
       */
      if (fireStation.getStationNumber().equals(stationNumber)) {

        for (Person person : persons) {

          if (fireStation.getAddress().toString().equals(person.getAddress().toString())) {

            personsByStation.add(person);
            fireStation.setPersonsByStation(personsByStation);
          }
        }
        personsByStation = fireStation.getPersonsByStation();
      }
    }
    
    if (personsByStation.isEmpty()) {
      logger.error("Error, no persons found for station number : {}", stationNumber);
    }
    return personsByStation;
  }

  
  @Override
  public List<PhoneNumberDTO> findPhoneNumbersByStation(String stationNumber) {
   
    logger.debug("Start finding person's phone number from station {} ", stationNumber);
    // Creation of a list to return with phoneNumbers
    List<PhoneNumberDTO> phoneNumberListByStation = new ArrayList<>();

    List<Person> personsByStation = findPersonsByStation(stationNumber);
    logger.info("Searching phone numbers from station number {} ",  stationNumber);
    for (Person person : personsByStation) {

      PhoneNumberDTO phoneNumberByStation = PhoneNumberDTO.builder().lastName(person.getLastName())
          .firstName(person.getFirstName()).phoneNumber(person.getPhone()).build();

      phoneNumberListByStation.add(phoneNumberByStation);

    }
   
    if (phoneNumberListByStation.isEmpty()) {
      logger.error("Error, no phone number found for station number : {}", stationNumber);
    }
    return phoneNumberListByStation;
  }

}
