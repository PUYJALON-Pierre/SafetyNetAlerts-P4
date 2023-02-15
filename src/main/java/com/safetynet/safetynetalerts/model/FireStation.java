
package com.safetynet.safetynetalerts.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;


 @Data
 @Builder
  public class FireStation {
   
  private String address;
  
  private String stationNumber; 
  
  //In order to link FireStation and persons
  private List<Person> personsByStation;

}
