package com.safetynet.safetynetalerts.DTO;

import java.util.List;

import lombok.Builder;
import lombok.Data;



/**
 * PersonInfoDTO class, data transfer object to return specific informations about a person
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
@Data
@Builder
public class PersonInfoDTO {


  private String firstName;

  private String lastName;

  private String address;

  private int age;

  private String email;

  private List <String> medications;

  private List <String> allergies;





}
