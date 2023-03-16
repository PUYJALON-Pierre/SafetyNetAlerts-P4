package com.safetynet.safetynetalerts.DTO;

import java.util.List;

import lombok.Builder;
import lombok.Data;


/**
 * PersonsByStationWithCountOfAdultAndChildDTO class, data transfer object to return specific informations
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
@Data
@Builder
public class PersonsByStationWithCountOfAdultAndChildDTO {

  private int numberChildren ;

  private int numberAdult;

  private List<PersonCoveredByStationNumberDTO> personListByStationNumber;

}
