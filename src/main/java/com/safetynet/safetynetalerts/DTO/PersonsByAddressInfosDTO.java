package com.safetynet.safetynetalerts.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;


/**
 * PersonsByAddressInfosDTO class, data transfer object to return specific informations about a person
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
@Data
@Builder
public class PersonsByAddressInfosDTO {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String address;

  private String firstName;

  private String lastName;

  private String phoneNumber;

  private int age;

  private List <String> medications;

  private List <String> allergies;


}
