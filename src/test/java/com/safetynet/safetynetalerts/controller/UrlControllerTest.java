package com.safetynet.safetynetalerts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import com.safetynet.safetynetalerts.DTO.PhoneNumberDTO;
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
  private  IPersonService iPersonService;
  
  @MockBean
  private  JsonDataBase jsonDataBase;
  
  @MockBean
  private  IFireStationService iFireStationService;
  
  List<Person> persons =new ArrayList<>();
  
  List<FireStation> firestations =new ArrayList<>();
  
  
  
  
  @Test
  public void findPersonsByStationWithAdultAndChildCount_FoundTest() throws Exception {
    //mock the data return by service class

    List<PersonCoveredByStationNumberDTO> personCoveredByStationNumberDTOs = new ArrayList<>();
    personCoveredByStationNumberDTOs.isEmpty();
    PersonsByStationWithCountOfAdultAndChildDTO personsByStationWithCountOfAdultAndChildDTO = PersonsByStationWithCountOfAdultAndChildDTO.builder().numberAdult(0).numberChildren(0).personListByStationNumber(personCoveredByStationNumberDTOs).build()
;    

    when(iFireStationService.findPersonsByStationWithAdultAndChildCount("1")).thenReturn(personsByStationWithCountOfAdultAndChildDTO);
    
    //create a mock http request to verify the expected result
    mockMvc.perform(get("/firestation")
    .param("stationNumber", "1")
    .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isFound());
  }
  

  @Test
  public void findPersonsByStationWithAdultAndChildCount_NotFoundTest() throws Exception {
    //mock the data return by service class

    List<PersonCoveredByStationNumberDTO> personCoveredByStationNumberDTOs = new ArrayList<>();
    personCoveredByStationNumberDTOs.isEmpty();
    PersonsByStationWithCountOfAdultAndChildDTO personsByStationWithCountOfAdultAndChildDTO = null;
    when(iFireStationService.findPersonsByStationWithAdultAndChildCount("1")).thenReturn(personsByStationWithCountOfAdultAndChildDTO);
    
    //create a mock http request to verify the expected result
    mockMvc.perform(get("/firestation")
    .param("stationNumber", "1")
    .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isNotFound());
  }

  
  
  
  
  @Test
  public void ChildAlert_FoundTest() throws Exception {
    //mock the data return by service class

    List<ChildDTO> childrenList = new ArrayList<>();
    childrenList.add(ChildDTO.builder().firstName("John").lastName("Boyd").age(10).personsAtSameHouse(null).build());
    
    
    when(iPersonService.findChildByAddress("1509 Culver St")).thenReturn(childrenList);
    
    //create a mock http request to verify the expected result
    mockMvc.perform(get("/childAlert")
    .param("address", "1509 Culver St")
    .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isFound());
  }
  
  @Test
  public void ChildAlert_NotFoundTest() throws Exception {
    //mock the data return by service class

    List<ChildDTO> childrenList = null;
 
    when(iPersonService.findChildByAddress("1509 Culver St")).thenReturn(childrenList);
    
    //create a mock http request to verify the expected result
    mockMvc.perform(get("/childAlert")
    .param("address", "1509 Culver St")
    .contentType(MediaType.APPLICATION_JSON))

    .andExpect(status().isNotFound());
  }
  
  
  
  
  
  @Test
  public void findPhoneNumbersByStation_FoundTest() throws Exception {
    //mock the data return by service class

    List<PhoneNumberDTO> phoneNumberDTOs = new ArrayList<>();

    phoneNumberDTOs.add(PhoneNumberDTO.builder().firstName("Jacob").lastName("Boyd").phoneNumber("444-5555-556").build());
    phoneNumberDTOs.add(PhoneNumberDTO.builder().firstName("John").lastName("Boyd").phoneNumber("777-7777-777").build());
    
    
    when(iFireStationService.findPhoneNumbersByStation("1")).thenReturn(phoneNumberDTOs);
    
    //create a mock http request to verify the expected result
    mockMvc.perform(get("/phoneAlert")
    .param("firestation", "1")
    .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isFound());
  }
  
  
  @Test
  public void findPhoneNumbersByStation_NotFoundTest() throws Exception {
    //mock the data return by service class

    List<PhoneNumberDTO> phoneNumberDTOs = null;
    
    when(iFireStationService.findPhoneNumbersByStation("1")).thenReturn(phoneNumberDTOs);
    
    //create a mock http request to verify the expected result
    mockMvc.perform(get("/phoneAlert")
    .param("firestation", "1")
    .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isNotFound());
  }
  
 
  @Test
  public void findPersonByAddressWithInfo_FoundTest() throws Exception {
    //mock the data return by service class

    List<PersonsByAddressInfosDTO> personsByAddressInfosDTOs = new ArrayList<>();
  personsByAddressInfosDTOs.add(PersonsByAddressInfosDTO.builder().address("1509 Culver St").firstName("John").lastName("Boyd").phoneNumber("454-555-555").age(10).medications(null).allergies(null).build());
    
  personsByAddressInfosDTOs.add(PersonsByAddressInfosDTO.builder().address("1509 Culver St").firstName("Jacob").lastName("Boyd").phoneNumber("444-555-222").age(78).medications(null).allergies(null).build());
  
  PersonsListByAddressWithStationDTO personsListByAddressWithStationDTO = PersonsListByAddressWithStationDTO.builder().stationNumber("1").personsByAddressInfo(personsByAddressInfosDTOs).build();

  
  
    when(iPersonService.findPersonsByAddressWithInfos("1509 Culver St")).thenReturn(personsListByAddressWithStationDTO);
    
    //create a mock http request to verify the expected result
    mockMvc.perform(get("/fire")
    .param("address", "1509 Culver St")
    .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isFound());
  }
  
  
  @Test
  public void findPersonByAddressWithInfo_NotFoundTest() throws Exception {
    //mock the data return by service class

    PersonsListByAddressWithStationDTO personsListByAddressWithStationDTO = null; 
  
    when(iPersonService.findPersonsByAddressWithInfos("1509 Culver St")).thenReturn(personsListByAddressWithStationDTO);
    
    //create a mock http request to verify the expected result
    mockMvc.perform(get("/fire")
    .param("address", "1509 Culver St")
    .contentType(MediaType.APPLICATION_JSON))

    .andExpect(status().isNotFound());
  }
  
  
  
  
  @Test
  public void findAllPersonsSortedByAddressAndStation_FoundTest() throws Exception {
    //mock the data return by service class
    List<String> stationNumberList = new ArrayList<>();
   stationNumberList.add("1");
   stationNumberList.add("4");
    List<PersonsByAddressInfosDTO> personsByAddressInfosDTOs = new ArrayList<>();

 personsByAddressInfosDTOs.add(PersonsByAddressInfosDTO.builder().address("1509 Culver St").firstName("John").lastName("Boyd").phoneNumber("444-4444-444").age(10).medications(null).allergies(null).build());
 personsByAddressInfosDTOs.add(PersonsByAddressInfosDTO.builder().address("1509 Culver St").firstName("Jacob").lastName("Boyd").phoneNumber("777-7777-777").age(11).medications(null).allergies(null).build());
    when(iFireStationService.findAllPersonsSortedByAddressAndStation(stationNumberList)).thenReturn(personsByAddressInfosDTOs);
    
    //create a mock http request to verify the expected result
    mockMvc.perform(get("/flood")
    .param("stations","1")
    .param("stations","4")
    .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isFound());
  }
  
  

  @Test
  public void findAllPersonsSortedByAddressAndStation_NotFoundTest() throws Exception {
    //mock the data return by service class
    
    List<String> stationNumberList = new ArrayList<>();
    stationNumberList.add("1");
    stationNumberList.add("4");
    List<PersonsByAddressInfosDTO> personsByAddressInfosDTOs = null;
    
    when(iFireStationService.findAllPersonsSortedByAddressAndStation(stationNumberList)).thenReturn(personsByAddressInfosDTOs);
    
    //create a mock http request to verify the expected result
    mockMvc.perform(get("/flood")
        .param("stations","1")
        .param("stations","4")
    .contentType(MediaType.APPLICATION_JSON))

    .andExpect(status().isNotFound());
  }
  
  
  
  
  
  @Test
  public void findAllPersonInfos_FoundTest() throws Exception {
    //mock the data return by service class
    String firstName = "John";
    String lastName = "Boyd";
    
   PersonInfoDTO personInfoDTO = PersonInfoDTO.builder().firstName("John").lastName("Boyd").address("1509 Culver St").age(100).email("jhsdg@gmail.com").medications(null).allergies(null).build();

  
 
    when(iPersonService.findAllPersonInfo(firstName, lastName)).thenReturn(personInfoDTO);
    
    //create a mock http request to verify the expected result
    mockMvc.perform(get("/personInfo")
    .param("firstName", "John")
    .param("lastName","Boyd")
    .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isFound());
  }
  
  
  
  @Test
  public void findAllPersonInfos_NotFoundTest() throws Exception {
    //mock the data return by service class
    String firstName = "John";
    String lastName = "Boyd";
    
   PersonInfoDTO personInfoDTO = null;

  
    when(iPersonService.findAllPersonInfo(firstName, lastName)).thenReturn(personInfoDTO);
    
    //create a mock http request to verify the expected result
    mockMvc.perform(get("/personInfo")
    .param("firstName", "John")
    .param("lastName","Boyd")
    .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isNotFound());
  }
  
  
  
  @Test
  public void findAllEmailByCity_FoundTest() throws Exception {
    //mock the data return by service class
    List<EmailDTO> emailDTOs = new ArrayList<>();
    
  emailDTOs.add(EmailDTO.builder().email("fdher@gmail.com").build());
    
 emailDTOs.add(EmailDTO.builder().email("djfhe@gmail.com").build());
  
    
    when(iPersonService.findAllEmailByCity("Culver")).thenReturn(emailDTOs);
    
    //create a mock http request to verify the expected result
    mockMvc.perform(get("/communityEmail")
    .param("city", "Culver")
    .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isFound());
  }
  
  
  
  
  @Test
  public void findAllEmailByCity_NotFound() throws Exception {
    //mock the data return by service class
    List<EmailDTO> emailDTOs = null;
 
    
    
    when(iPersonService.findAllEmailByCity("Culver")).thenReturn(emailDTOs);
    
    //create a mock http request to verify the expected result
    mockMvc.perform(get("/communityEmail")
    .param("city", "Culver")
    .contentType(MediaType.APPLICATION_JSON))

    .andExpect(status().isNotFound());
  }
  
  
  
  
  
  
  
  
  

  
  
  
  
}
