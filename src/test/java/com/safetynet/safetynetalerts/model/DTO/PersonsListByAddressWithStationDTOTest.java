package com.safetynet.safetynetalerts.model.DTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.jparams.verifier.tostring.ToStringVerifier;
import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.DTO.PersonsListByAddressWithStationDTO;

@SpringBootTest
public class PersonsListByAddressWithStationDTOTest {

  @Test
  public void personsListByAddressWithStationDTOHashCodeTest() {

    PersonsListByAddressWithStationDTO personsListByAddressWithStationDTO1 = PersonsListByAddressWithStationDTO
        .builder().stationNumber("1").personsByAddressInfo(null).build();
    PersonsListByAddressWithStationDTO personsListByAddressWithStationDTO2 = PersonsListByAddressWithStationDTO
        .builder().stationNumber("1").personsByAddressInfo(null).build();

    assertNotSame(personsListByAddressWithStationDTO1, personsListByAddressWithStationDTO2);
    assertEquals(personsListByAddressWithStationDTO1.hashCode(),
        personsListByAddressWithStationDTO2.hashCode());
  }

  @Test
  public void personsListByAddressWithStationDTOToStringTest() {

    ToStringVerifier.forClass(PersonsListByAddressWithStationDTO.class).verify();
  }

  @Test
  public void personsListByAddressWithStationDTOEqualsTest() {

    PersonsListByAddressWithStationDTO personsListByAddressWithStationDTO1 = PersonsListByAddressWithStationDTO
        .builder().stationNumber("1").personsByAddressInfo(null).build();
    PersonsListByAddressWithStationDTO personsListByAddressWithStationDTO2 = PersonsListByAddressWithStationDTO
        .builder().stationNumber("1").personsByAddressInfo(null).build();

    assertEquals(personsListByAddressWithStationDTO1, personsListByAddressWithStationDTO2);
  }

}
