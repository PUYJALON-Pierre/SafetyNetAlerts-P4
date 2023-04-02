package com.safetynet.safetynetalerts.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AgeCalculatorTest {

  AgeCalculator ageCalculator = new AgeCalculator();

  @Test
  public void calculateAgeTest() {

    String birthdate1 = "01/03/1989" ;
    String birthdate2 = "02/18/2012";
    String birthdate3 = "03/06/1994";

   int age1 = ageCalculator.CalculateAge(birthdate1);
   int age2 = ageCalculator.CalculateAge(birthdate2);
   int age3 = ageCalculator.CalculateAge(birthdate3);

   assertEquals(34, age1);
   assertEquals(11, age2);
   assertEquals(29, age3);

  }

}
