package com.safetynet.safetynetalerts.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

  @Autowired
  private JsonDataBase jSonDataBase;

  @Override
  public List<FireStation> findAll() {
    return jSonDataBase.getFirestations();
  }

  @Override
  public void addFireStation(FireStation fireStation) {

    List<FireStation> fireStations = jSonDataBase.getFirestations();
    fireStations.add(fireStation);
    jSonDataBase.setFirestations(fireStations);
  }

  @Override
  public void updateStationNumber(FireStation fireStation) {

    // Finding fireStation number to update with a stream filter by address
    Optional<FireStation> optionalFireStation = jSonDataBase.getFirestations().stream()
        .filter(p -> p.getAddress().equals(fireStation.getAddress())).findAny();

    // If match found, set new stationNumber to update
    if (optionalFireStation.isPresent()) {
      optionalFireStation.get().setStationNumber(fireStation.getStationNumber());
    } else {
      System.out.println("Error finding firestation number to udpate, no match found by address");
    }
  }

  @Override
  public void deleteFireStation(String address, String stationNumber) {

    List<FireStation> fireStations = jSonDataBase.getFirestations();

    // Finding fireStation to delete with a stream filter by address and stationNumber
    Optional<FireStation> optionalFireStation = jSonDataBase.getFirestations().stream()
        .filter(p -> p.getAddress().equals(address) && p.getStationNumber().equals(stationNumber))
        .findAny();

    // If match found, remove it and set to delete
    if (optionalFireStation.isPresent()) {

      fireStations.remove(optionalFireStation.get());
      jSonDataBase.setFirestations(fireStations);

    } else {
      System.out.println(
          "Error finding firestation to delete, no match found by address and station number");
    }
  }

  // ---------------------------------------------------------------------------------------------------
  /*
   * not working yet
   *
   * Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste
   * doit regrouper les personnes par adresse. Elle doit aussi inclure le nom, le numéro de
   * téléphone et l'âge des habitants, et faire figurer leurs antécédents médicaux (médicaments,
   * posologie et allergies) à côté de chaque nom.
   *
   */
  @Override
  public List<PersonsByAddressInfosDTO> findAllPersonsSortedByAddressAndStation(String stationNumber) {

    List<PersonsByAddressInfosDTO> personsByStationSortByAddress = new ArrayList<>();
    List<Person> personsByStation = findPersonsByStation(stationNumber);

//in order to sort by address (useless??)
    personsByStation.sort((t1,t2)-> {
      return t1.getAddress().compareTo(t2.getAddress());
    });

    for (Person person : personsByStation) {
      PersonsByAddressInfosDTO personSortByAddressDTO = PersonsByAddressInfosDTO.builder()
          .address(person.getAddress())
          .lastName(person.getLastName())
          .firstName(person.getFirstName())
          .phoneNumber(person.getPhone())
          .birthdate(person.getMedicalRecord().getBirthdate())
          .medications(person.getMedicalRecord().getMedications())
          .allergies(person.getMedicalRecord().getAllergies())
          .stationNumber(stationNumber)
          .build();
      
      personsByStationSortByAddress.add(personSortByAddressDTO);
    }



    return personsByStationSortByAddress;
  }

  @Override
  public PersonsByStationWithCountOfAdultAndChildDTO findPersonsByStationWithAdultAndChildCount(String stationNumber) {

    List<FireStation> fireStations = jSonDataBase.getFirestations();
    List<Person> personsByStation = null;
    List<PersonCoveredByStationNumberDTO> personsByStationWithInfos = new ArrayList<>();
    PersonsByStationWithCountOfAdultAndChildDTO personsByStationWithInfosAndCount = null;

    for (FireStation fireStation : fireStations) {
      /*
       * if stationNumber is equal to fireStationNumber, return a list of DTOpersons covered by this
       * FireStation which infos
       */

      if (fireStation.getStationNumber() == stationNumber) {
        personsByStation = fireStation.getPersonsByStation();

        //variables in order to count adult and children
        int numberChildren = 0;
        int numberAdult = 0;

        for (Person person : personsByStation) {

          PersonCoveredByStationNumberDTO personDTO = PersonCoveredByStationNumberDTO.builder()
              .lastName(person.getLastName())
              .firstName(person.getFirstName())
              .address(person.getAddress())
              .phoneNumber(person.getPhone())
              .build();

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
            .personListByStationNumber(personsByStationWithInfos)
            .numberChildren(numberChildren)
            .numberAdult(numberAdult)
            .build();
            
     

        return personsByStationWithInfosAndCount;
      }
    }
    return personsByStationWithInfosAndCount;
  }

  @Override
  public List<Person> findPersonsByStation(String stationNumber) {

    List<Person> persons = jSonDataBase.getPersons();
    List<FireStation> fireStations = jSonDataBase.getFirestations();
    List<Person> personsByStation = null;
    for (FireStation fireStation : fireStations) {
      /*
       * if stationNumber is equal to fireStationNumber, return a list of persons covered by this
       * FireStation which has been build during Json File charging
       */
      if (fireStation.getStationNumber() == stationNumber) {

        for (Person person : persons) {

          if (fireStation.getAddress().toString().equals(person.getAddress().toString())) {

            personsByStation.add(person);
            fireStation.setPersonsByStation(personsByStation);
          }
        }
        personsByStation = fireStation.getPersonsByStation();
        return personsByStation;
      }
    }
    return personsByStation;
  }

  @Override
  public List<PhoneNumberDTO> findPhoneNumbersByStation(String stationNumber) {
    // Creation of a list to return with phoneNumbers

    List<PhoneNumberDTO> phoneNumberListByStation = new ArrayList<>();

    List<Person> personsByStation = findPersonsByStation(stationNumber);

    for (Person person : personsByStation) {


      PhoneNumberDTO  phoneNumberByStation = PhoneNumberDTO.builder()
          .lastName(person.getLastName())
          .firstName(person.getFirstName())
          .phoneNumber(person.getPhone())
          .build();

     phoneNumberListByStation.add(phoneNumberByStation);

    }
    return phoneNumberListByStation;
  }

}
