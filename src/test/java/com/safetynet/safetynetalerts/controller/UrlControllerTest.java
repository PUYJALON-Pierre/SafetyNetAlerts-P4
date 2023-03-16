package com.safetynet.safetynetalerts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.DTO.EmailDTO;
import com.safetynet.safetynetalerts.DTO.PersonCoveredByStationNumberDTO;
import com.safetynet.safetynetalerts.DTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByAddressInfosDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByStationWithCountOfAdultAndChildDTO;
import com.safetynet.safetynetalerts.DTO.PersonsListByAddressWithStationDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonDataBase;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.IFireStationService;
import com.safetynet.safetynetalerts.service.IPersonService;

@WebMvcTest(controllers = UrlController.class)
public class UrlControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private IPersonService iPersonService;

  @MockBean
  private JsonDataBase jsonDataBase;

  @MockBean
  private IFireStationService iFireStationService;

  List<Person> persons = new ArrayList<>();

  List<FireStation> firestations = new ArrayList<>();

  @Test
  public void findPersonsByStationWithAdultAndChildCount_FoundTest() throws Exception {

    // Given
    List<PersonCoveredByStationNumberDTO> personCoveredByStationNumberDTOs = new ArrayList<>();
    personCoveredByStationNumberDTOs.isEmpty();
    PersonsByStationWithCountOfAdultAndChildDTO personsByStationWithCountOfAdultAndChildDTO = PersonsByStationWithCountOfAdultAndChildDTO
        .builder().numberAdult(0).numberChildren(0)
        .personListByStationNumber(personCoveredByStationNumberDTOs).build();

    // When
    when(iFireStationService.findPersonsByStationWithAdultAndChildCount("1"))
        .thenReturn(personsByStationWithCountOfAdultAndChildDTO);

    // Then
    mockMvc
        .perform(
            get("/firestation").param("stationNumber", "1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isFound());
  }

  @Test
  public void findPersonsByStationWithAdultAndChildCount_NotFoundTest() throws Exception {

    // Given
    List<PersonCoveredByStationNumberDTO> personCoveredByStationNumberDTOs = new ArrayList<>();
    personCoveredByStationNumberDTOs.isEmpty();
    PersonsByStationWithCountOfAdultAndChildDTO personsByStationWithCountOfAdultAndChildDTO = null;

    // When
    when(iFireStationService.findPersonsByStationWithAdultAndChildCount("1"))
        .thenReturn(personsByStationWithCountOfAdultAndChildDTO);

    // Then
    mockMvc
        .perform(
            get("/firestation").param("stationNumber", "1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void ChildAlert_FoundTest() throws Exception {

    // Given
    List<ChildDTO> childrenList = new ArrayList<>();
    childrenList.add(ChildDTO.builder().firstName("John").lastName("Boyd").age(10)
        .personsAtSameHouse(null).build());

    // When
    when(iPersonService.findChildByAddress("1509 Culver St")).thenReturn(childrenList);

    // Then
    mockMvc.perform(get("/childAlert").param("address", "1509 Culver St")
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isFound());
  }

  @Test
  public void ChildAlert_NotFoundTest() throws Exception {

    // Given
    List<ChildDTO> childrenList = null;

    // When
    when(iPersonService.findChildByAddress("1509 Culver St")).thenReturn(childrenList);

    // Then
    mockMvc.perform(get("/childAlert").param("address", "1509 Culver St")
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
  }

  @Test
  public void findPhoneNumbersByStation_FoundTest() throws Exception {

    // Given
    Set<String> phoneNumberDTOs = new HashSet<>();

    phoneNumberDTOs.add("444-5555-556");
    phoneNumberDTOs.add("777-7777-777");

    // When
    when(iFireStationService.findPhoneNumbersByStation("1")).thenReturn(phoneNumberDTOs);

    // Then
    mockMvc
        .perform(
            get("/phoneAlert").param("firestation", "1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isFound());
  }

  @Test
  public void findPhoneNumbersByStation_NotFoundTest() throws Exception {

    // Given
    Set<String> phoneNumberDTOs = null;

    // When
    when(iFireStationService.findPhoneNumbersByStation("1")).thenReturn(phoneNumberDTOs);

    // Then
    mockMvc
        .perform(
            get("/phoneAlert").param("firestation", "1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void findPersonByAddressWithInfo_FoundTest() throws Exception {

    // Given
    List<PersonsByAddressInfosDTO> personsByAddressInfosDTOs = new ArrayList<>();

    personsByAddressInfosDTOs.add(PersonsByAddressInfosDTO.builder().address("1509 Culver St")
        .firstName("John").lastName("Boyd").phoneNumber("454-555-555").age(10).medications(null)
        .allergies(null).build());

    personsByAddressInfosDTOs.add(PersonsByAddressInfosDTO.builder().address("1509 Culver St")
        .firstName("Jacob").lastName("Boyd").phoneNumber("444-555-222").age(78).medications(null)
        .allergies(null).build());

    PersonsListByAddressWithStationDTO personsListByAddressWithStationDTO = PersonsListByAddressWithStationDTO
        .builder().stationNumber("1").personsByAddressInfo(personsByAddressInfosDTOs).build();

    // When
    when(iPersonService.findPersonsByAddressWithInfos("1509 Culver St"))
        .thenReturn(personsListByAddressWithStationDTO);

    // Then
    mockMvc
        .perform(
            get("/fire").param("address", "1509 Culver St").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isFound());
  }

  @Test
  public void findPersonByAddressWithInfo_NotFoundTest() throws Exception {

    // Given
    PersonsListByAddressWithStationDTO personsListByAddressWithStationDTO = null;

    // When
    when(iPersonService.findPersonsByAddressWithInfos("1509 Culver St"))
        .thenReturn(personsListByAddressWithStationDTO);

    // Then
    mockMvc
        .perform(
            get("/fire").param("address", "1509 Culver St").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void findAllPersonsSortedByAddressAndStation_FoundTest() throws Exception {

    // Given
    List<String> stationNumberList = new ArrayList<>();
    stationNumberList.add("1");
    stationNumberList.add("4");

    List<PersonsByAddressInfosDTO> personsByAddressInfosDTOs = new ArrayList<>();

    personsByAddressInfosDTOs.add(PersonsByAddressInfosDTO.builder().address("1509 Culver St")
        .firstName("John").lastName("Boyd").phoneNumber("444-4444-444").age(10).medications(null)
        .allergies(null).build());
    personsByAddressInfosDTOs.add(PersonsByAddressInfosDTO.builder().address("1509 Culver St")
        .firstName("Jacob").lastName("Boyd").phoneNumber("777-7777-777").age(11).medications(null)
        .allergies(null).build());

    // When
    when(iFireStationService.findAllPersonsSortedByAddressAndStation(stationNumberList))
        .thenReturn(personsByAddressInfosDTOs);

    // Then
    mockMvc.perform(get("/flood").param("stations", "1").param("stations", "4")
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isFound());
  }

  @Test
  public void findAllPersonsSortedByAddressAndStation_NotFoundTest() throws Exception {

    // Given
    List<String> stationNumberList = new ArrayList<>();
    stationNumberList.add("1");
    stationNumberList.add("4");
    List<PersonsByAddressInfosDTO> personsByAddressInfosDTOs = null;

    // When
    when(iFireStationService.findAllPersonsSortedByAddressAndStation(stationNumberList))
        .thenReturn(personsByAddressInfosDTOs);

    // Then
    mockMvc
        .perform(get("/flood").param("stations", "1").param("stations", "4")
            .contentType(MediaType.APPLICATION_JSON))

        .andExpect(status().isNotFound());
  }

  @Test
  public void findAllPersonInfos_FoundTest() throws Exception {

    // Given
    String firstName = "John";
    String lastName = "Boyd";
    List<PersonInfoDTO> personsInfos = new ArrayList<>();

    // When
    when(iPersonService.findAllPersonInfo(firstName, lastName)).thenReturn(personsInfos);

    // Then
    mockMvc.perform(get("/personInfo").param("firstName", "John").param("lastName", "Boyd")
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isFound());
  }

  @Test
  public void findAllPersonInfos_NotFoundTest() throws Exception {

    // Given
    String firstName = "John";
    String lastName = "Boyd";
    List<PersonInfoDTO> personsInfos = null;

    // When
    when(iPersonService.findAllPersonInfo(firstName, lastName)).thenReturn(personsInfos);

    // Then
    mockMvc.perform(get("/personInfo").param("firstName", "John").param("lastName", "Boyd")
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
  }

  @Test
  public void findAllEmailByCity_FoundTest() throws Exception {

    // Given
    List<EmailDTO> emailDTOs = new ArrayList<>();

    emailDTOs.add(EmailDTO.builder().email("fdher@gmail.com").build());

    emailDTOs.add(EmailDTO.builder().email("djfhe@gmail.com").build());

    // When
    when(iPersonService.findAllEmailByCity("Culver")).thenReturn(emailDTOs);

    // Then
    mockMvc
        .perform(
            get("/communityEmail").param("city", "Culver").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isFound());
  }

  @Test
  public void findAllEmailByCity_NotFound() throws Exception {

    // Given
    List<EmailDTO> emailDTOs = null;

    // When
    when(iPersonService.findAllEmailByCity("Culver")).thenReturn(emailDTOs);

    // Then
    mockMvc
        .perform(
            get("/communityEmail").param("city", "Culver").contentType(MediaType.APPLICATION_JSON))

        .andExpect(status().isNotFound());
  }

}
