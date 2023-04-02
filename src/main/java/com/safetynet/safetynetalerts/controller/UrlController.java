package com.safetynet.safetynetalerts.controller;

import java.util.List;
import java.util.Set;

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
import com.safetynet.safetynetalerts.service.IFireStationService;
import com.safetynet.safetynetalerts.service.IPersonService;

/**
 * Controller class for specific Endpoints
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
@RestController
public class UrlController {

  final static Logger logger = LogManager.getLogger(UrlController.class);

  @Autowired
  private IFireStationService iFireStationService;

  @Autowired
  private IPersonService iPersonService;

  /**
   * Retrieve persons by station with count of adult and children (URL n°1)
   *
   * @param stationNumber - String
   * @return ResponseEntity PersonsByStationWithCountOfAdultAndChildDTO
   */
  @GetMapping("/firestation")
  public ResponseEntity<PersonsByStationWithCountOfAdultAndChildDTO> findPersonsByStationWithAdultAndChildCount(
      @RequestParam(value = "stationNumber") String stationNumber) {
    logger.debug("GetMapping of all firestations");

    PersonsByStationWithCountOfAdultAndChildDTO personsByStationWithCountOfAdultAndChildDTO = iFireStationService
        .findPersonsByStationWithAdultAndChildCount(stationNumber);

    if (personsByStationWithCountOfAdultAndChildDTO == null) {
      logger.error("Error during recuperation of persons by station with adult and children");
      return new ResponseEntity<>(
          personsByStationWithCountOfAdultAndChildDTO, HttpStatus.NOT_FOUND);
    } else {
      logger.info("Persons by station with adult and children count founded");
      return new ResponseEntity<>(
          personsByStationWithCountOfAdultAndChildDTO, HttpStatus.FOUND);
    }

  }

  /**
   * Retrieve Children at an address (URL n°2)
   *
   * @param address - String
   * @return ResponseEntity (List of ChildDTO)
   */
  @GetMapping("/childAlert")
  public ResponseEntity<List<ChildDTO>> findChildByAddress(
      @RequestParam(value = "address") String address) {
    logger.debug("GetMapping of children at address : {}", address);

    List<ChildDTO> childDTO = iPersonService.findChildByAddress(address);
    if (childDTO == null) {
      logger.error("Error finding children at address : {}", address);
      return new ResponseEntity<>(childDTO, HttpStatus.NOT_FOUND);
    } else {
      logger.info("Children list at address : {} founded", address);
      return new ResponseEntity<>(childDTO, HttpStatus.FOUND);
    }
  }

  /**
   * Retrieve phoneNumbers by stations (URL n°3)
   *
   * @param stationNumber - String
   * @return ResponseEntity (Set of String)
   */
  @GetMapping("/phoneAlert")
  public ResponseEntity<Set<String>> findPhoneNumbersByStation(
      @RequestParam(value = "firestation") String stationNumber) {
    logger.debug("GetMapping of all phonenumbers for firestations number : {}", stationNumber);

    Set<String> phoneNumberDTO = iFireStationService
        .findPhoneNumbersByStation(stationNumber);

    if (phoneNumberDTO == null) {
      logger.error("Error during recuperation of person's phonenumbers by station");
      return new ResponseEntity<>(phoneNumberDTO, HttpStatus.NOT_FOUND);
    } else {
      logger.info("Phonenumbers for station number : {} founded", stationNumber);
      return new ResponseEntity<>(phoneNumberDTO, HttpStatus.FOUND);
    }
  }

  /**
   * Retrieve persons and fireStation concerned by address (URL n°4)
   *
   * @param address - String
   * @return ResponseEntity PersonsListByAddressWithStationDTO
   */
  @GetMapping("/fire")
  public ResponseEntity<PersonsListByAddressWithStationDTO> findPersonsByAddressWithInfos(
      @RequestParam(value = "address") String address) {
    logger.debug("GetMapping of persons by address with informations");

    PersonsListByAddressWithStationDTO personByAddressInfosWithStation = iPersonService
        .findPersonsByAddressWithInfos(address);
    if (personByAddressInfosWithStation == null) {
      logger.error("Error finding persons informations at address : {}", address);
      return new ResponseEntity<>(personByAddressInfosWithStation,
          HttpStatus.NOT_FOUND);
    } else {
      logger.info("Persons informations at address : {} founded", address);
      return new ResponseEntity<>(personByAddressInfosWithStation,
          HttpStatus.FOUND);
    }
  }

  /**
   * Retrieve Persons by station sort by address from a stationNumberList(URL n°5)
   *
   * @param stationNumberList - List of String
   * @return ResponseEntity (List of PersonsByAddressInfosDTO)
   */
  @GetMapping("/flood")
  public ResponseEntity<List<PersonsByAddressInfosDTO>> findAllPersonsSortedByAddressAndStation(
      @RequestParam(value = "stations") List<String> stationNumberList) {
    logger.debug("GetMapping of all persons covered by station number : {} , sort by address",
        stationNumberList);
    List<PersonsByAddressInfosDTO> personsByAddressInfosDTO = iFireStationService
        .findAllPersonsSortedByAddressAndStation(stationNumberList);

    if (personsByAddressInfosDTO == null) {
      logger.error("Error during recuperation of persons covered for station number : {}",
          stationNumberList);
      return new ResponseEntity<>(personsByAddressInfosDTO,
          HttpStatus.NOT_FOUND);
    } else {
      logger.info("Persons sorted by address and covered by for stations number : {} founded",
          stationNumberList);
      return new ResponseEntity<>(personsByAddressInfosDTO,
          HttpStatus.FOUND);
    }

  }

  /**
   * Retrieve all informations of a person find by name(URL n°6)
   *
   * @param firstName - String
   * @param lastName - String
   * @return ResponseEntity List of PersonInfoDTO
   */
  @GetMapping("/personInfo")
  public ResponseEntity<List<PersonInfoDTO>> findAllPersonsInfo(
      @RequestParam(value = "firstName") String firstName,
      @RequestParam(value = "lastName") String lastName) {
    logger.debug("GetMapping of all informations for person : {} ,{}", firstName, lastName);
    List<PersonInfoDTO> personInfoDTO = iPersonService.findAllPersonInfo(firstName, lastName);

    if (personInfoDTO == null) {
      logger.error("Error finding person informations for : {}, {}", firstName, lastName);
      return new ResponseEntity<>(personInfoDTO, HttpStatus.NOT_FOUND);
    } else {
      logger.info("Person informations for : {} , {} founded", firstName, lastName);
      return new ResponseEntity<>(personInfoDTO, HttpStatus.FOUND);
    }
  }

  /**
   * Retrieve all mails by city (URL n°7)
   *
   * @param city - String
   * @return ResponseEntity (List of EmailDTO)
   */
  @GetMapping("/communityEmail")
  public ResponseEntity<List<EmailDTO>> findAllEmail(@RequestParam String city) {
    logger.debug("GetMapping of all email of inhabitants");
    List<EmailDTO> emailDTO = iPersonService.findAllEmailByCity(city);

    if (emailDTO == null) {
      logger.error("Error finding persons email for city : {}", city);
      return new ResponseEntity<>(emailDTO, HttpStatus.NOT_FOUND);
    } else {
      logger.info("Persons email list found for city : {}", city);
      return new ResponseEntity<>(emailDTO, HttpStatus.FOUND);
    }
  }

}
