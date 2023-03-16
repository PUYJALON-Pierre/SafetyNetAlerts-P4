package com.safetynet.safetynetalerts.DTO;

import java.util.List;

import lombok.Builder;
import lombok.Data;


/**
 * PersonsListByAddressWithStationDTO class, data transfer object to return specific informations about persons at an address with stationNumber
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
@Data
@Builder
public class PersonsListByAddressWithStationDTO {


  private String stationNumber;

  private List<PersonsByAddressInfosDTO> personsByAddressInfo;




}
