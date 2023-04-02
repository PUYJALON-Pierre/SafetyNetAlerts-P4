package com.safetynet.safetynetalerts.DTO;

import java.util.List;

import lombok.Builder;
import lombok.Data;




/**
 * ChildDTO class, data transfer object to return specific informations about a child
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
@Data
@Builder
public class ChildDTO {

  private String firstName;
  private String lastName;
  private int age;
  private List<PersonNameDTO> personsAtSameHouse;


}
