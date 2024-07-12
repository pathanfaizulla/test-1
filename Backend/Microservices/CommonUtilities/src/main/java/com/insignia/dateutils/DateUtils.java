package com.insignia.dateutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static Date getCurrentDate() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date today = new Date();
		String currentDateStr = formatter.format(today);
		Date currentDate = formatter.parse(currentDateStr);
		return currentDate;
	}

	public static Date stringToDate(String dateStr) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = formatter.parse(dateStr);
		return date;
	}

}
