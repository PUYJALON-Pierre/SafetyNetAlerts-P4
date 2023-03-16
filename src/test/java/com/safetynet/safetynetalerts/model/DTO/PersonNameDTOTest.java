package com.safetynet.safetynetalerts.model.DTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.jparams.verifier.tostring.ToStringVerifier;
import com.safetynet.safetynetalerts.DTO.PersonNameDTO;

@SpringBootTest
public class PersonNameDTOTest {

  @Test
  public void personNameDTOHashCodeTest() {

    PersonNameDTO personNameDTO1 = PersonNameDTO.builder().firstName("John").lastName("Boyd")
        .build();

    PersonNameDTO personNameDTO2 = PersonNameDTO.builder().firstName("John").lastName("Boyd")
        .build();
    assertNotSame(personNameDTO1, personNameDTO2);
    assertEquals(personNameDTO1.hashCode(), personNameDTO2.hashCode());
  }

  @Test
  public void personNameDTOToStringTest() {

    ToStringVerifier.forClass(PersonNameDTO.class).verify();
  }

  @Test
  public void personNameDTOEqualsTest() {

    PersonNameDTO personNameDTO1 = PersonNameDTO.builder().firstName("John").lastName("Boyd")
        .build();

    PersonNameDTO personNameDTO2 = PersonNameDTO.builder().firstName("John").lastName("Boyd")
        .build();

    assertEquals(personNameDTO1, personNameDTO2);
  }

}
