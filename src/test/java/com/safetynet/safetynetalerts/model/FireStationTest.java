package com.safetynet.safetynetalerts.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.jparams.verifier.tostring.ToStringVerifier;

@SpringBootTest
public class FireStationTest {


  @Test
  public void fireStationHashCodeTest() {

   FireStation fireStation1= FireStation.builder().address("1509 Culver St").stationNumber("3")
    .personsByStation(null).build();

   FireStation fireStation2 = FireStation.builder().address("1509 Culver St").stationNumber("3")
    .personsByStation(null).build();

    assertNotSame(fireStation1, fireStation2); 
    assertEquals(fireStation1.hashCode(), fireStation2.hashCode());
  }

  @Test
  public void fireStationToStringTest() {

  ToStringVerifier.forClass(FireStation.class).verify();
  }
  
}
