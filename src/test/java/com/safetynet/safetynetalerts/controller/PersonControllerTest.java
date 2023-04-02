package com.safetynet.safetynetalerts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.JsonDataBase;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.IPersonService;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private IPersonService iPersonService;

  @MockBean
  private JsonDataBase jsonDataBase;

  @Test
  public void addPerson_CreatedTest() throws Exception {

    // Given
    Person personToAdd = Person.builder().firstName("Benoit").lastName("Dupont")
        .address("854 E. Tulipe Dr").city("Culver").zip("97451").phone("841-874-2648")
        .email("bendup@email.com").medicalRecord(null).build();

    // When
    when(iPersonService.addPerson(personToAdd)).thenReturn(personToAdd);

    // Then
    mockMvc.perform(post("/person").content(new ObjectMapper().writeValueAsString(personToAdd))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
  }

  @Test
  public void addPerson_BadRequestTest() throws Exception {

    // Given
    Person personToAdd = null;

    // When
    when(iPersonService.addPerson(personToAdd)).thenReturn(personToAdd);

    // Then
    mockMvc.perform(post("/person").content(new ObjectMapper().writeValueAsString(personToAdd))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
  }

  @Test
  public void updatePerson_OKTest() throws Exception {

    // Given
    Person personToAdd = Person.builder().firstName("Benoit").lastName("Dupont")
        .address("854 E. Tulipe Dr").city("Culver").zip("97451").phone("841-874-2648")
        .email("bendup@email.com").medicalRecord(null).build();

    // When
    when(iPersonService.updatePerson(personToAdd)).thenReturn(personToAdd);

    // Then
    mockMvc.perform(put("/person").content(new ObjectMapper().writeValueAsString(personToAdd))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
  }

  @Test
  public void updatePerson_NotFoundTest() throws Exception {

    // Given
    Person personToUpdate = Person.builder().firstName("Benoit").lastName("Dupont")
        .address("854 E. Tulipe Dr").city("Culver").zip("97451").phone("841-874-2648")
        .email("bendup@email.com").medicalRecord(null).build();

    // When
    when(iPersonService.updatePerson(personToUpdate)).thenReturn(null);

    // Then
    mockMvc.perform(put("/person").content(new ObjectMapper().writeValueAsString(personToUpdate))
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
  }

  @Test
  public void deletePerson_OKTest() throws Exception {

    // Given
    List<Person> personsToDelete = new ArrayList<>();
    Person personToDelete = Person.builder().firstName("Benoit").lastName("Dupont")
        .address("854 E. Tulipe Dr").city("Culver").zip("97451").phone("841-874-2648")
        .email("bendup@email.com").medicalRecord(null).build();

    // When
    when(iPersonService.deletePerson(personToDelete.getFirstName(), personToDelete.getLastName()))
        .thenReturn(personsToDelete);

    // Then
    mockMvc.perform(delete("/person").param("firstName", "Benoit").param("lastName", "Dupont")
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
  }

  @Test
  public void deletePerson_NotFoundTest() throws Exception {

    // Given
    List<Person> personsToDelete = null;

    // When
    when(iPersonService.deletePerson("", "")).thenReturn(personsToDelete);

    // Then
    mockMvc.perform(delete("/person").param("firstName", "").param("lastName", "")
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
  }

  @Test
  public void getPersons_FoundTest() throws Exception {

    // Given
    List<Person> persons = new ArrayList<>();

    persons.add(Person.builder().firstName("John").lastName("Boyd").address("1509 Culver St")
        .city("Culver").zip("97451").phone("841-874-6512").email("jaboyd@email.com")
        .medicalRecord(null).build());
    persons.add(Person.builder().firstName("Jacob").lastName("Boyd").address("1509 Culver St")
        .city("Culver").zip("97451").phone("841-874-6513").email("drk@email.com")
        .medicalRecord(null).build());

    // When
    when(iPersonService.findAll()).thenReturn(persons);

    // Then
    mockMvc.perform(get("/persons")).andExpect(status().isFound());
  }

  @Test
  public void getPersons_NotFoundTest() throws Exception {

    // Given
    List<Person> persons = new ArrayList<>();
    persons.isEmpty();

    // When
    when(iPersonService.findAll()).thenReturn(persons);

    // Then
    mockMvc.perform(get("/persons")).andExpect(status().isNotFound());
  }

}
