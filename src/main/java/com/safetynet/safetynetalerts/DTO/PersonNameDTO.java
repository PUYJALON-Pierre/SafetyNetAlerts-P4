package com.safetynet.safetynetalerts.DTO;


import lombok.Builder;
import lombok.Data;



/**
 * PersonNameDTO class, data transfer object to return specific person's name informations
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
@Data
@Builder
public class PersonNameDTO {

  private String firstName;

  private String lastName;

}
