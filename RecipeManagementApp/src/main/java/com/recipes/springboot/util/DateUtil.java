package com.recipes.springboot.util;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	
	/**
	 * Returns current date and time
	 * 
	 * @return Date
	 */
	public static Date getCurrentDateTime() {

		return Date.from(ZonedDateTime.now(ZoneOffset.UTC).toInstant());

	}

}
