package com.safetynet.safetynetalerts.model.DTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.jparams.verifier.tostring.ToStringVerifier;
import com.safetynet.safetynetalerts.DTO.PersonInfoDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByAddressInfosDTO;

@SpringBootTest
public class PersonsByAddressInfosDTOTest {

  @Test
  public void personsByAddressInfosDTOHashCodeTest() {

    PersonsByAddressInfosDTO personByAddressInfosDTO1 = PersonsByAddressInfosDTO.builder()
        .address("1509 Culver St").firstName("John").lastName("Boyd").phoneNumber("444-4444-444")
        .age(20).medications(null).allergies(null).build();
    PersonsByAddressInfosDTO personByAddressInfosDTO2 = PersonsByAddressInfosDTO.builder()
        .address("1509 Culver St").firstName("John").lastName("Boyd").phoneNumber("444-4444-444")
        .age(20).medications(null).allergies(null).build();

    assertNotSame(personByAddressInfosDTO1, personByAddressInfosDTO2);
    assertEquals(personByAddressInfosDTO1.hashCode(), personByAddressInfosDTO2.hashCode());
  }

  @Test
  public void personsByAddressInfosDTOToStringTest() {

    ToStringVerifier.forClass(PersonsByAddressInfosDTO.class).verify();
  }

  @Test
  public void personsByAddressInfosDTOEqualsTest() {

    PersonsByAddressInfosDTO personByAddressInfosDTO1 = PersonsByAddressInfosDTO.builder()
        .address("1509 Culver St").firstName("John").lastName("Boyd").phoneNumber("444-4444-444")
        .age(20).medications(null).allergies(null).build();
    PersonsByAddressInfosDTO personByAddressInfosDTO2 = PersonsByAddressInfosDTO.builder()
        .address("1509 Culver St").firstName("John").lastName("Boyd").phoneNumber("444-4444-444")
        .age(20).medications(null).allergies(null).build();

    assertEquals(personByAddressInfosDTO1, personByAddressInfosDTO2);
  }

}
