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
public class FireStationServiceImpl implements IFireStationService {

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
  public void updateStationNumber(String address, String stationNumber) {

    // Finding fireStation number to update with a stream filter by address
    Optional<FireStation> optionalFireStation = jSonDataBase.getFirestations().stream()
        .filter(p -> p.getAddress().equals(address)).findAny();

    // If match found, set new stationNumber to update
    if (optionalFireStation.isPresent()) {
      optionalFireStation.get().setStationNumber(stationNumber);
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
  //not working yet
  @Override
  public List<Person> findAllPersonsByStationSortedByAddress() {
    
    return null;
  }
  
  @Override
  public List <String> findPersonsByStationWithAdultAndChildCount(String stationNumber){
    
    List<FireStation> fireStations = jSonDataBase.getFirestations();
    List<Person> personsByStation = null ;
 
    List<String> personsByStationWithInfos = new ArrayList<>() ;
    for (FireStation fireStation : fireStations) {
      /*
       * if stationNumber is equal to fireStationNumber, return a list of persons covered by this FireStation which has
       * been build during Json File charging
       */
      
      if (fireStation.getStationNumber() == stationNumber ){
        personsByStation = fireStation.getPersonsByStation();
     

        int numberChildren=0;
        int numberAdult =0;
        
        for(Person person : personsByStation) {
          
          personsByStationWithInfos.add(person.getLastName().toString());
          personsByStationWithInfos.add(person.getFirstName().toString());
          personsByStationWithInfos.add(person.getAddress().toString());
          personsByStationWithInfos.add(person.getPhone().toString());
          
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
            
            // if child then add increment count
            if (age <= 18) {
             
              numberChildren++;
            }
            else {
              numberAdult++;
            }
        }
        
        //add count of adult and children to list
        personsByStationWithInfos.add("Nombre d'enfants : "+numberChildren+" Nombre d'adultes : "+numberAdult);
        return personsByStationWithInfos;
      } 
    }
    return personsByStationWithInfos;
  }

  

  @Override
  public List <Person> findPersonsByStation(String stationNumber){
    
    List<Person> persons = jSonDataBase.getPersons();
    List<FireStation> fireStations = jSonDataBase.getFirestations();
    List<Person> personsByStation = null ;
    for (FireStation fireStation : fireStations) {
      /*
       * if stationNumber is equal to fireStationNumber, return a list of persons covered by this FireStation which has
       * been build during Json File charging
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
  public List<String> findPhoneNumbersByStation(String stationNumber) {
    // Creation of a list to return with phoneNumbers

    List<String> phoneNumberByStation = new ArrayList<>();

    List<Person> personsByStation = findPersonsByStation(stationNumber);
    
    

    for (Person person : personsByStation) {

      phoneNumberByStation.add(person.getFirstName().toString());
      phoneNumberByStation.add(person.getLastName().toString());
      phoneNumberByStation.add(person.getPhone().toString());
      phoneNumberByStation.add("------------------------------------------------------");
    }
    return phoneNumberByStation;
  }



}
