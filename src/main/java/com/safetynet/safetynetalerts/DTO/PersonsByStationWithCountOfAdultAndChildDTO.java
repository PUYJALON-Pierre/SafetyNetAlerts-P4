package com.safetynet.safetynetalerts.DTO;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonsByStationWithCountOfAdultAndChildDTO {

  private List<PersonCoveredByStationNumberDTO> personListByStationNumber;

  private int numberChildren ;

  private int numberAdult;

}
