package com.safetynet.safetynetalerts.model.DTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.jparams.verifier.tostring.ToStringVerifier;
import com.safetynet.safetynetalerts.DTO.ChildDTO;

@SpringBootTest
public class ChildDTOTest {

  @Test
  public void childDTOHashCodeTest() {

    ChildDTO childDTO1 = ChildDTO.builder().firstName("John").lastName("Boyd").age(10)
        .personsAtSameHouse(null).build();
    ChildDTO childDTO2 = ChildDTO.builder().firstName("John").lastName("Boyd").age(10)
        .personsAtSameHouse(null).build();

    assertNotSame(childDTO1, childDTO2);
    assertEquals(childDTO1.hashCode(), childDTO2.hashCode());
  }

  @Test
  public void childDTOToStringTest() {

    ToStringVerifier.forClass(ChildDTO.class).verify();
  }

  @Test
  public void childDTOEqualsTest() {

    ChildDTO childDTO1 = ChildDTO.builder().firstName("John").lastName("Boyd").age(10)
        .personsAtSameHouse(null).build();
    ChildDTO childDTO2 = ChildDTO.builder().firstName("John").lastName("Boyd").age(10)
        .personsAtSameHouse(null).build();

    assertEquals(childDTO1, childDTO2);
  }

}
