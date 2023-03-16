package com.safetynet.safetynetalerts.model.DTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.jparams.verifier.tostring.ToStringVerifier;
import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.DTO.PersonsByStationWithCountOfAdultAndChildDTO;

@SpringBootTest
public class PersonsByStationWithCountOfAdultAndChildDTOTest {

  @Test
  public void personsByStationWithCountOfAdultAndChildDTOHashCodeTest() {

    PersonsByStationWithCountOfAdultAndChildDTO personsByStationWithCountOfAdultAndChildDTO1 = PersonsByStationWithCountOfAdultAndChildDTO
        .builder().numberChildren(4).numberAdult(5).personListByStationNumber(null).build();
    PersonsByStationWithCountOfAdultAndChildDTO personsByStationWithCountOfAdultAndChildDTO2 = PersonsByStationWithCountOfAdultAndChildDTO
        .builder().numberChildren(4).numberAdult(5).personListByStationNumber(null).build();

    assertNotSame(personsByStationWithCountOfAdultAndChildDTO1,
        personsByStationWithCountOfAdultAndChildDTO2);
    assertEquals(personsByStationWithCountOfAdultAndChildDTO1.hashCode(),
        personsByStationWithCountOfAdultAndChildDTO2.hashCode());
  }

  @Test
  public void personsByStationWithCountOfAdultAndChildDTOToStringTest() {

    ToStringVerifier.forClass(PersonsByStationWithCountOfAdultAndChildDTO.class).verify();
  }

  @Test
  public void personsByStationWithCountOfAdultAndChildDTOEqualsTest() {

    PersonsByStationWithCountOfAdultAndChildDTO personsByStationWithCountOfAdultAndChildDTO1 = PersonsByStationWithCountOfAdultAndChildDTO
        .builder().numberChildren(4).numberAdult(5).personListByStationNumber(null).build();
    PersonsByStationWithCountOfAdultAndChildDTO personsByStationWithCountOfAdultAndChildDTO2 = PersonsByStationWithCountOfAdultAndChildDTO
        .builder().numberChildren(4).numberAdult(5).personListByStationNumber(null).build();

    assertEquals(personsByStationWithCountOfAdultAndChildDTO1,
        personsByStationWithCountOfAdultAndChildDTO2);
  }

}
