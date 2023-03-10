package com.safetynet.safetynetalerts.controller;



import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.DTO.EmailDTO;
import com.safetynet.safetynetalerts.DTO.PersonCoveredByStationNumberDTO;
import com.safetynet.safetynetalerts.DTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByAddressInfosDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByStationWithCountOfAdultAndChildDTO;
import com.safetynet.safetynetalerts.DTO.PhoneNumberDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonDataBase;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.IFireStationService;
import com.safetynet.safetynetalerts.service.IMedicalRecordService;
import com.safetynet.safetynetalerts.service.IPersonService;
import com.safetynet.safetynetalerts.service.impl.IFireStationServiceImpl;



@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {
    
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private  IFireStationService iFireStationService;
    
    @MockBean
    private  JsonDataBase jsonDataBase;
    
    

    
  
    
    @Test
    public void addFireStation_CreatedTest() throws Exception {
      //mock the data return by service class
      FireStation fireStationToAdd = FireStation.builder().address("748 Townings Dr")
          .stationNumber("3").personsByStation(null).build();
  
      when(iFireStationService.addFireStation(fireStationToAdd)).thenReturn(fireStationToAdd);
      
      //create a mock http request to verify the expected result
      mockMvc.perform(post("/firestation")
      .content(new ObjectMapper().writeValueAsString (fireStationToAdd))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated());
    }
    
    
    @Test
    public void addFireStation_BadRequest() throws Exception {
      //mock the data return by service class
      FireStation fireStationToAdd = null;
  
      when(iFireStationService.addFireStation(fireStationToAdd)).thenReturn(fireStationToAdd);
      
      //create a mock http request to verify the expected result
      mockMvc.perform(post("/firestation")
      .content(new ObjectMapper().writeValueAsString (fireStationToAdd))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
    }
    
    
    
    @Test  
    public void updateFireStation_OKTest() throws Exception {
      //mock the data return by service class
      FireStation fireStationToUpdate = FireStation.builder().address("748 Townings Dr")
          .stationNumber("7").personsByStation(null).build();
  
      when(iFireStationService.updateStationNumber(fireStationToUpdate)).thenReturn(fireStationToUpdate);
      
      //create a mock htttp request to verify the expected result
      mockMvc.perform(put("/firestation")
      .content(new ObjectMapper().writeValueAsString (fireStationToUpdate))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
    }
   
    
    @Test  
    public void updateFireStation_NotFoundTest() throws Exception {
      //mock the data return by service class
      FireStation fireStationToUpdate = FireStation.builder().address("748 Townings Dr")
          .stationNumber("7").personsByStation(null).build();
      
      when(iFireStationService.updateStationNumber(fireStationToUpdate)).thenReturn(null);
      
      //create a mock http request to verify the expected result
      mockMvc.perform(put("/firestation")
      .content(new ObjectMapper().writeValueAsString (fireStationToUpdate))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
    }
    
    
   
    @Test  
    public void deleteFireStation_OKTest() throws Exception {
      //mock the data retunr by service class
    
      FireStation fireStationToDelete = FireStation.builder().address("748 Townings Dr")
          .stationNumber("7").personsByStation(null).build();
  
   
      when(iFireStationService.deleteFireStation("748 Townings Dr", "7")).thenReturn(fireStationToDelete);
      
      //create a mock htttp request to verify the expected result
      mockMvc.perform(delete("/firestation")
      .param("address", "748 Townings Dr")
      .param("stationNumber","7")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
    }
    
    
    @Test  
    public void deleteFireStation_NotFoundTest() throws Exception {
      //mock the data retunr by service class
      
      FireStation fireStationToDelete  = null;
   
      when(iFireStationService.deleteFireStation("", "")).thenReturn(fireStationToDelete);
      
      //create a mock htttp request to verify the expected result
      mockMvc.perform(delete("/firestation")
      .param("address", "")
      .param("stationNumber","")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
    }
  
  
    
    

    @Test
    public void getFireStation_FoundTest() throws Exception {
      //mock the data return by service class
      List<FireStation> firestations =new ArrayList<>();
      firestations.add(FireStation.builder().address("1509 Culver St").stationNumber("3")
          .personsByStation(null).build());
      firestations.add(FireStation.builder().address("29 15th St").stationNumber("2")
          .personsByStation(null).build());
      
      when(iFireStationService.findAll()).thenReturn(firestations);
      
      //create a mock http request to verify the expected result
      mockMvc.perform(get("/firestations"))
      .andExpect(status().isFound());
    }
    
    
    @Test
    public void getFireStation_NotFoundTest() throws Exception {
      //mock the data return by service class
      List<FireStation> firestations =new ArrayList<>();
      firestations.isEmpty();
      
      when(iFireStationService.findAll()).thenReturn(firestations);
      
      //create a mock http request to verify the expected result
      mockMvc.perform(get("/firestations"))
      .andExpect(jsonPath("$.length()", is(0)))
      .andExpect(status().isNotFound());
    }
    
    
    
    
    
    
}
