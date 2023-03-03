/*package com.safetynet.safetynetalerts.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.safetynetalerts.model.JsonDataBase;
import com.safetynet.safetynetalerts.service.IMedicalRecordService;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  IMedicalRecordService iMedicalRecordService;
  
  @MockBean
  JsonDataBase jsonDataBase;
  
  
  @Disabled
  @Test
  public void testGetMedicalRecords () throws Exception {
    
    mockMvc.perform(get("")).andExpect(status().isOk());
    
    
    
    
    
  }
  
}
*/