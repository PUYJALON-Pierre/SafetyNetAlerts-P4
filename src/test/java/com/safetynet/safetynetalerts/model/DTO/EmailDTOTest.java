package com.safetynet.safetynetalerts.model.DTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.jparams.verifier.tostring.ToStringVerifier;
import com.safetynet.safetynetalerts.DTO.ChildDTO;
import com.safetynet.safetynetalerts.DTO.EmailDTO;

@SpringBootTest
public class EmailDTOTest {

  @Test
  public void emailDTOHashCodeTest() {

    EmailDTO email1 = EmailDTO.builder().email("dferf@gmail.com").build();

    EmailDTO email2 = EmailDTO.builder().email("dferf@gmail.com").build();

    assertNotSame(email1, email2);
    assertEquals(email1.hashCode(), email2.hashCode());
  }

  @Test
  public void emailDTOToStringTest() {

    ToStringVerifier.forClass(EmailDTO.class).verify();
  }

  @Test
  public void emailDTOEqualsTest() {

    EmailDTO email1 = EmailDTO.builder().email("dferf@gmail.com").build();

    EmailDTO email2 = EmailDTO.builder().email("dferf@gmail.com").build();

    assertEquals(email1, email2);
  }

}
