package com.safetynet.safetynetalerts.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class that can calculate age
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
public class AgeCalculator {

  final static Logger logger = LogManager.getLogger(AgeCalculator.class);

  /**
   * Calculate age from a birthdate String and return age in int
   *
   * @param birthdate - String
   * @return age - int
   */
  public int CalculateAge(String birthdate) {

    Date birthdateDate = null;
    try {
      birthdateDate = (new SimpleDateFormat("MM/dd/yyyy")).parse(birthdate);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    Date actualDate = new Date();
    // Calculating age
    Long ageMillisecond = (actualDate.getTime() - birthdateDate.getTime());

    // Converting age into years
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(ageMillisecond);
    int age = c.get(Calendar.YEAR) - 1970;

    return age;

  }

}
