package com.mentoring.jpa.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author ivanovaolyaa
 * @version 10/27/2017
 */
@Data
@AllArgsConstructor
public class UserDto {

    private String email;

    private LocalDate birthDate;

}
