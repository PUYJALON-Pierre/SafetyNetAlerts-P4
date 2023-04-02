package com.safetynet.safetynetalerts.DTO;


import lombok.Builder;
import lombok.Data;



/**
 * PersonCoveredByStationNumberDTO class, data transfer object to return specific informations about persons covered by a station
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
@Data
@Builder
public class PersonCoveredByStationNumberDTO {


  private String firstName;
  private String lastName;
  private String address;
  private String phoneNumber;

}
