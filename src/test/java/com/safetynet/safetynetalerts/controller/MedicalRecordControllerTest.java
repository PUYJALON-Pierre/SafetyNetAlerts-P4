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
import com.safetynet.safetynetalerts.DTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByAddressInfosDTO;
import com.safetynet.safetynetalerts.model.JsonDataBase;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.IMedicalRecordService;
import com.safetynet.safetynetalerts.service.IPersonService;


@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {
  
  
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private  IMedicalRecordService iMedicalRecordService;
  
  @MockBean
  private  JsonDataBase jsonDataBase;
  

  List<MedicalRecord> medicalRecords =new ArrayList<>();
  public List<String> medications = new ArrayList<>();
  public List<String> allergies = new ArrayList<>();
  
  
  
  @Test
  public void addMedicalRecord_CreatedTest() throws Exception {
    //mock the data return by service class
    medications.add("aznol:350mg" + "hydrapermazol:100mg");
    allergies.add("nillacilan");
    
    MedicalRecord medicalRecordToAdd = MedicalRecord.builder().firstName("John").lastName("Boyd")
        .birthdate("03/06/1993").medications(medications).allergies(allergies).build();
 
    when(iMedicalRecordService.addMedicalRecord(medicalRecordToAdd)).thenReturn(medicalRecordToAdd);
    
    //create a mock htttp request to verify the expected result
    mockMvc.perform(post("/medicalRecord")
    .content(new ObjectMapper().writeValueAsString (medicalRecordToAdd))
    .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isCreated());
  }
  
  
  @Test
  public void addMedicalRecord_BadRequestTest() throws Exception {
    
    //mock the data retunr by service class
    MedicalRecord medicalRecordToAdd = null;

    when(iMedicalRecordService.addMedicalRecord(medicalRecordToAdd)).thenReturn(medicalRecordToAdd);
    //create a mock htttp request to verify the expected result
    mockMvc.perform(post("/medicalRecord")
    .content(new ObjectMapper().writeValueAsString (medicalRecordToAdd))
    .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isBadRequest());
  }
  
  

  
  @Test  
  public void updateMedicalRecord_OKTest() throws Exception {
    //mock the data retunr by service class
    MedicalRecord medicalRecordToUpdate = MedicalRecord.builder().firstName("John").lastName("Boyd")
        .birthdate("03/06/1993").medications(medications).allergies(allergies).build();
 
    when(iMedicalRecordService.updateMedicalRecord(medicalRecordToUpdate)).thenReturn(medicalRecordToUpdate);
    
    //create a mock htttp request to verify the expected result
    mockMvc.perform(put("/medicalRecord")
    .content(new ObjectMapper().writeValueAsString (medicalRecordToUpdate))
    .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk());
  }
 
  
  @Test  
  public void updateMedicalRecord_NotFoundTest() throws Exception {
    //mock the data retunr by service class
    MedicalRecord medicalRecordToUpdate = MedicalRecord.builder().firstName("John").lastName("Boyd")
        .birthdate("03/06/1993").medications(medications).allergies(allergies).build();
 
    when(iMedicalRecordService.updateMedicalRecord(medicalRecordToUpdate)).thenReturn(null);
    
    //create a mock htttp request to verify the expected result
    mockMvc.perform(put("/medicalRecord")
    .content(new ObjectMapper().writeValueAsString (medicalRecordToUpdate))
    .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isNotFound());
  }

  
  
  @Test  
  public void deleteMedicalRecord_OKTest() throws Exception {
    //mock the data retunr by service class
  
    MedicalRecord medicalRecordToDelete = MedicalRecord.builder().firstName("John").lastName("Boyd")
        .birthdate("03/06/1993").medications(medications).allergies(allergies).build();
 
    when(iMedicalRecordService.deleteMedicalRecord("John","Boyd")).thenReturn(medicalRecordToDelete);
    
    //create a mock htttp request to verify the expected result
    mockMvc.perform(delete("/medicalRecord")
    .param("firstName", "John")
    .param("lastName","Boyd")
    .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk());
  }
  
  @Test  
  public void deleteMedicalRecord_NotFoundTest() throws Exception {
    //mock the data retunr by service class
  
    MedicalRecord medicalRecordToDelete = null;
 
    when(iMedicalRecordService.deleteMedicalRecord("","")).thenReturn(medicalRecordToDelete);
    
    //create a mock htttp request to verify the expected result
    mockMvc.perform(delete("/medicalRecord")
    .param("firstName", "")
    .param("lastName","")
    .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isNotFound());
  }
  
  
  

  
  @Test
  public void getMedicalRecords_FoundTest() throws Exception {
    //mock the data return by service class

    medications.add("aznol:350mg" + "hydrapermazol:100mg");
    allergies.add("nillacilan");
    
    medicalRecords.add(MedicalRecord.builder().firstName("John").lastName("Boyd").birthdate("03/06/1984").medications(medications).allergies(allergies).build());
    
    medicalRecords.add(MedicalRecord.builder().firstName("Jacob").lastName("Boyd").birthdate("03/06/1989").medications(medications).allergies(allergies).build());
    
    when(iMedicalRecordService.findAll()).thenReturn(medicalRecords);
    
    //create a mock http request to verify the expected result
    mockMvc.perform(get("/medicalRecords"))
    .andExpect(status().isFound());
  }
  
  
  @Test
  public void getMedicalRecords_NotFoundTest() throws Exception {
    //mock the data return by service class
   medicalRecords.isEmpty();
    
    when(iMedicalRecordService.findAll()).thenReturn(medicalRecords);
    
    //create a mock http request to verify the expected result
    mockMvc.perform(get("/medicalRecords"))
    .andExpect(jsonPath("$.length()", is(0)))
    .andExpect(status().isNotFound());
  }
  
  
  
}
