package com.safetynet.safetynetalerts.model.DTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.jparams.verifier.tostring.ToStringVerifier;
import com.safetynet.safetynetalerts.DTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.model.JsonDataBase;

@SpringBootTest
public class PersonInfoDTOTest {

  @Test
  public void personInfoDTOHashCodeTest() {

    PersonInfoDTO personInfoDTO1 = PersonInfoDTO.builder().firstName("John").lastName("Boyd")
        .address("1509 Culver St").age(20).email("fcerf@gmail.com").medications(null)
        .allergies(null).build();

    PersonInfoDTO personInfoDTO2 = PersonInfoDTO.builder().firstName("John").lastName("Boyd")
        .address("1509 Culver St").age(20).email("fcerf@gmail.com").medications(null)
        .allergies(null).build();

    assertNotSame(personInfoDTO1, personInfoDTO2);
    assertEquals(personInfoDTO1.hashCode(), personInfoDTO2.hashCode());
  }

  @Test
  public void personInfoDTOToStringTest() {

    ToStringVerifier.forClass(PersonInfoDTO.class).verify();
  }

  @Test
  public void personInfoDTOEqualsTest() {

    PersonInfoDTO personInfoDTO1 = PersonInfoDTO.builder().firstName("John").lastName("Boyd")
        .address("1509 Culver St").age(20).email("fcerf@gmail.com").medications(null)
        .allergies(null).build();

    PersonInfoDTO personInfoDTO2 = PersonInfoDTO.builder().firstName("John").lastName("Boyd")
        .address("1509 Culver St").age(20).email("fcerf@gmail.com").medications(null)
        .allergies(null).build();

    assertEquals(personInfoDTO1, personInfoDTO2);
  }

}
