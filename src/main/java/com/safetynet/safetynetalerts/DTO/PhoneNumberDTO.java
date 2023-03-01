package com.safetynet.safetynetalerts.DTO;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneNumberDTO {

  private String firstName;

  private String lastName;

  private String phoneNumber;


}
