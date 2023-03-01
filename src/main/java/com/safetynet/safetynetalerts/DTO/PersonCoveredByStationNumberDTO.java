package com.safetynet.safetynetalerts.DTO;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonCoveredByStationNumberDTO {


  private String firstName;
  private String lastName;
  private String address;
  private String phoneNumber;

}
