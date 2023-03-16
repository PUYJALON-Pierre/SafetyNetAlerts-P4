package com.safetynet.safetynetalerts.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.jparams.verifier.tostring.ToStringVerifier;

@SpringBootTest
public class JsonDataBaseTest {

  @Test
  public void jsonDatabaseHashCodeTest() {

    JsonDataBase jsonDataBase1 = new JsonDataBase();
    JsonDataBase jsonDataBase2 = new JsonDataBase();

    assertNotSame(jsonDataBase1, jsonDataBase2);
    assertEquals(jsonDataBase1.hashCode(), jsonDataBase2.hashCode());
  }

  @Test
  public void jsonDatabaseEqualsTest() {

    JsonDataBase jsonDataBase1 = new JsonDataBase();
    JsonDataBase jsonDataBase2 = new JsonDataBase();
    
    assertEquals(jsonDataBase1, jsonDataBase2);
  }
  
  @Test
  public void JsonDataBaseToStringTest() {

    ToStringVerifier.forClass(JsonDataBase.class).verify();
  }

}
