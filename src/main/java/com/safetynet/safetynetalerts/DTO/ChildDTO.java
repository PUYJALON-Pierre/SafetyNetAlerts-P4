package com.safetynet.safetynetalerts.DTO;

import java.util.List;

import com.safetynet.safetynetalerts.model.Person;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChildDTO {

  private String firstName;
  private String lastName;
  private int age;
  private List<PhoneNumberDTO> personsAtSameHouse;

  
}
