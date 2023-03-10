package com.safetynet.safetynetalerts.DTO;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonsListByAddressWithStationDTO {

  
  private String stationNumber;
  
  private List<PersonsByAddressInfosDTO> personsByAddressInfo;
  
  
  
  
}
