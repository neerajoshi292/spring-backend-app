package com.example.utility;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommanUtilityClass {

	private static final Logger log = LoggerFactory.getLogger(CommanUtilityClass.class);
	
	public static Integer calculateAge(LocalDate dob) {
		try {
			if(dob != null && dob.isAfter(LocalDate.now())) throw new DateTimeException("Date of birth can't be greater than current date");
			return dob != null ? Period.between(dob, LocalDate.now()).getYears() : 0;
		} catch (Exception e) {
			log.error("Error calculating age : {}",e.getMessage());
			return 0;
		}
	}
}
