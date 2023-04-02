
package com.safetynet.safetynetalerts.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;



/**
 * Model class for fireStation
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
 @Data
 @Builder
  public class FireStation {

  private String address;

  private String stationNumber;

  //In order to link FireStation and Persons
  private List<Person> personsByStation;

}
