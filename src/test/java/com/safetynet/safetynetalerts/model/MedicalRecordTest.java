package com.safetynet.safetynetalerts.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.jparams.verifier.tostring.ToStringVerifier;

@SpringBootTest
public class MedicalRecordTest {

  @Test
  public void medicalRecordHashCodeTest() {

    MedicalRecord medicalRecord1 = MedicalRecord.builder().firstName("John").lastName("Boyd")
        .birthdate("03/06/1984").medications(null).allergies(null).build();

    MedicalRecord medicalRecord2 = MedicalRecord.builder().firstName("John").lastName("Boyd")
        .birthdate("03/06/1984").medications(null).allergies(null).build();

    assertNotSame(medicalRecord1, medicalRecord2);
    assertEquals(medicalRecord1.hashCode(), medicalRecord2.hashCode());
  }

  @Test
  public void medicalRecordToStringTest() {

    ToStringVerifier.forClass(MedicalRecord.class).verify();
  }

}
