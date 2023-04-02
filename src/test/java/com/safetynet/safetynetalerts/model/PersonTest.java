package com.safetynet.safetynetalerts.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.jparams.verifier.tostring.ToStringVerifier;

@SpringBootTest
public class PersonTest {

  
  @Test
  public void personHashCodeTest() {

  Person person1 =  Person.builder().firstName("John").lastName("Boyd").address("1509 Culver St")
        .city("Culver").zip("97451").phone("841-874-6512").email("jaboyd@email.com")
        .medicalRecord(null).build();

        Person person2 = Person.builder().firstName("John").lastName("Boyd").address("1509 Culver St")
        .city("Culver").zip("97451").phone("841-874-6512").email("jaboyd@email.com")
        .medicalRecord(null).build();



    assertNotSame(person1, person2);
    assertEquals(person1.hashCode(), person2.hashCode());
  }
  
  @Test
  public void personToStringTest() {

  ToStringVerifier.forClass(Person.class).verify();
  }

}
