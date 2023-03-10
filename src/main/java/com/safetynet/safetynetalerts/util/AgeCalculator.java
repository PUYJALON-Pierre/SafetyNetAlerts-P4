package com.safetynet.safetynetalerts.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AgeCalculator {

  final static Logger logger = LogManager.getLogger(AgeCalculator.class);
  
  
  public int CalculateAge(String birthdate) {
    
    
    
    
    Date birthdateDate = null;
    try {
      birthdateDate = (new SimpleDateFormat("MM/dd/yyyy"))
          .parse(birthdate);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    Date actualDate = new Date();
    // Calculating age
    Long ageMillisecond = (actualDate.getTime() - birthdateDate.getTime());

    // converting age into years
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(ageMillisecond);
    int age = c.get(Calendar.YEAR) - 1970;
    
   
    
    return age;
    
  }
  
  
  
  
  
  
  
  
  
  
}
