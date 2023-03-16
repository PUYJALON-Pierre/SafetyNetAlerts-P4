package com.safetynet.safetynetalerts.DTO;

import lombok.Builder;
import lombok.Data;


/**
 * EmailDTO class, data transfer object to return email
 *
 * @author PUYJALON Pierre
 * @since 11/03/2023
 */
@Data
@Builder
public class EmailDTO {

  private String email;


}
