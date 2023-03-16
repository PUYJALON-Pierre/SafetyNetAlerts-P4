package com.safetynet.safetynetalerts.model.DTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.jparams.verifier.tostring.ToStringVerifier;
import com.safetynet.safetynetalerts.DTO.PersonCoveredByStationNumberDTO;
import com.safetynet.safetynetalerts.DTO.PersonInfoDTO;

@SpringBootTest
public class PersonCoveredByStationNumberDTOTest {

  @Test
  public void personCoveredByStationNumberDTOHashCodeTest() {
    PersonCoveredByStationNumberDTO objet1 = PersonCoveredByStationNumberDTO.builder()
        .firstName("John").lastName("Boyd").address("1509 Culver St").phoneNumber("444-444-4444")
        .build();
    PersonCoveredByStationNumberDTO objet2 = PersonCoveredByStationNumberDTO.builder()
        .firstName("John").lastName("Boyd").address("1509 Culver St").phoneNumber("444-444-4444")
        .build();

    assertNotSame(objet1, objet2);
    assertEquals(objet1.hashCode(), objet2.hashCode());
  }

  @Test
  public void personCoveredByStationNumberDTOToStringTest() {

    ToStringVerifier.forClass(PersonCoveredByStationNumberDTO.class).verify();
  }

  @Test
  public void personCoveredByStationNumberDTOEqualsTest() {

    PersonCoveredByStationNumberDTO objet1 = PersonCoveredByStationNumberDTO.builder()
        .firstName("John").lastName("Boyd").address("1509 Culver St").phoneNumber("444-444-4444")
        .build();
    PersonCoveredByStationNumberDTO objet2 = PersonCoveredByStationNumberDTO.builder()
        .firstName("John").lastName("Boyd").address("1509 Culver St").phoneNumber("444-444-4444")
        .build();

    assertEquals(objet1, objet2);
  }
}
