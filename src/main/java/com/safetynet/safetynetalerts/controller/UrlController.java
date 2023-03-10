package com.safetynet.safetynetalerts.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.DTO.EmailDTO;
import com.safetynet.safetynetalerts.DTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByAddressInfosDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByStationWithCountOfAdultAndChildDTO;
import com.safetynet.safetynetalerts.DTO.PersonsListByAddressWithStationDTO;
import com.safetynet.safetynetalerts.DTO.PhoneNumberDTO;
import com.safetynet.safetynetalerts.service.IFireStationService;
import com.safetynet.safetynetalerts.service.IPersonService;




@RestController
public class UrlController {

final static Logger logger = LogManager.getLogger(UrlController.class);

@Autowired
private IFireStationService iFireStationService;

@Autowired
private IPersonService iPersonService;



// Retrieve PersonsByStation WithCount Of Adult And Children(URL n°1) 
@GetMapping("/firestation")
public ResponseEntity<PersonsByStationWithCountOfAdultAndChildDTO> findPersonsByStationWithAdultAndChildCount(
    @RequestParam(value = "stationNumber") String stationNumber) {
  logger.debug("GetMapping of all firestations");

  PersonsByStationWithCountOfAdultAndChildDTO personsByStationWithCountOfAdultAndChildDTO = iFireStationService
      .findPersonsByStationWithAdultAndChildCount(stationNumber);

  if (personsByStationWithCountOfAdultAndChildDTO == null) {
    logger.error("Error during recuperation of persons by station with adult and children");
    return new ResponseEntity<PersonsByStationWithCountOfAdultAndChildDTO>(personsByStationWithCountOfAdultAndChildDTO,
        HttpStatus.NOT_FOUND);
  } else {
  logger.info("Persons by station with adult and children count founded");
  return new ResponseEntity<PersonsByStationWithCountOfAdultAndChildDTO>(personsByStationWithCountOfAdultAndChildDTO,
      HttpStatus.FOUND);}

}


// Retrieve Children at an address (URL n°2)  
@GetMapping("/childAlert")
public ResponseEntity<List<ChildDTO>> findChildByAddress(@RequestParam (value = "address") String address) {
  logger.debug("GetMapping of children at address : {}", address);
 
  List<ChildDTO> childDTO = iPersonService.findChildByAddress(address);
  if (childDTO == null) {
   logger.error("Error finding children at address : {}", address);
    return new ResponseEntity<List<ChildDTO>>(childDTO, HttpStatus.NOT_FOUND);
  } 
  else {
    logger.info("Children list at address : {} founded", address);
    return new ResponseEntity<List<ChildDTO>>(childDTO, HttpStatus.FOUND);}
}






// Retrieve phoneNumbers by stations(URL n°3)
@GetMapping("/phoneAlert") 
public ResponseEntity<List<PhoneNumberDTO>> findPhoneNumbersByStation(
    @RequestParam(value = "firestation") String stationNumber) {
  logger.debug("GetMapping of all phonenumbers for firestations number : {}", stationNumber);

  List<PhoneNumberDTO> phoneNumberDTO = iFireStationService
      .findPhoneNumbersByStation(stationNumber);

  if (phoneNumberDTO == null) {
    logger.error("Error during recuperation of person's phonenumbers by station");
    return new ResponseEntity<List<PhoneNumberDTO>>(phoneNumberDTO, HttpStatus.NOT_FOUND);
  } else {
  logger.info("Persons by station with adult and children count founded");
  return new ResponseEntity<List<PhoneNumberDTO>>(phoneNumberDTO, HttpStatus.FOUND);}
}



// Retrieve persons by address with station number (URL n°4) 
@GetMapping("/fire")
public ResponseEntity<PersonsListByAddressWithStationDTO> findPersonsByAddressWithInfos(
    @RequestParam (value = "address") String address) {
  logger.debug("GetMapping of persons by address with informations");
  
  PersonsListByAddressWithStationDTO personByAddressInfosWithStation =iPersonService.findPersonsByAddressWithInfos(address);
  if (personByAddressInfosWithStation == null) {
    logger.error("Error finding persons informations at address : {}", address);
     return new ResponseEntity<PersonsListByAddressWithStationDTO>(personByAddressInfosWithStation, HttpStatus.NOT_FOUND);
   } else {
  logger.info("Persons informations at address : {} founded", address);
  return new ResponseEntity<PersonsListByAddressWithStationDTO>(personByAddressInfosWithStation, HttpStatus.FOUND);}
}




// Retrieve Persons by station sort by address (URL n°5)
@GetMapping("/flood") 
public ResponseEntity<List<PersonsByAddressInfosDTO>> findAllPersonsSortedByAddressAndStation(
    @RequestParam(value = "stations") List<String> stationNumberList) {
  logger.debug("GetMapping of all persons covered by station number : {} , sort by address", stationNumberList);
  List<PersonsByAddressInfosDTO> personsByAddressInfosDTO = iFireStationService.findAllPersonsSortedByAddressAndStation(stationNumberList);
  
  if (personsByAddressInfosDTO == null) {
    logger.error("Error during recuperation of persons covered for station number : {}", stationNumberList);
    return new ResponseEntity<List<PersonsByAddressInfosDTO>>(personsByAddressInfosDTO, HttpStatus.NOT_FOUND);
  } else {
  logger.info("Persons sorted by address and covered by for stations number : {} founded" ,stationNumberList);
  return new ResponseEntity<List<PersonsByAddressInfosDTO>>(personsByAddressInfosDTO, HttpStatus.FOUND);}
  
}



// Retrieve all person informations by firstName and lastName (URL n°6)
@GetMapping("/personInfo")
public ResponseEntity<PersonInfoDTO> findAllPersonsInfo (
    @RequestParam(value = "firstName") String firstName,
    @RequestParam(value = "lastName") String lastName) {
  logger.debug("GetMapping of all informations for person : {} ,{}", firstName, lastName);
  PersonInfoDTO personInfoDTO =iPersonService.findAllPersonInfo(firstName,lastName);
  
  if (personInfoDTO == null) {
    logger.error("Error finding person informations for : {}",firstName, lastName);
     return new ResponseEntity<PersonInfoDTO>(personInfoDTO, HttpStatus.NOT_FOUND);
   } else {
     logger.info("Person informations founded");
     return new ResponseEntity<PersonInfoDTO>(personInfoDTO, HttpStatus.FOUND);
   }
}

// Retrieve all mails (URL n°7)
@GetMapping("/communityEmail")
public ResponseEntity<List<EmailDTO>> findAllEmail(@RequestParam String city) {
  logger.debug("GetMapping of all email of inhabitants");
  List<EmailDTO> emailDTO = iPersonService.findAllEmailByCity(city);
  
  if (emailDTO == null) {
    logger.error("Error finding persons email ");
    return new ResponseEntity<List<EmailDTO>>(emailDTO, HttpStatus.NOT_FOUND);
  }
  else {
    logger.info("Persons email list found");
    return new ResponseEntity<List<EmailDTO>>(emailDTO, HttpStatus.FOUND);
  }
}


}
