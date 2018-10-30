package tn.esprit.epione.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Util {
	
	public static Date getDateNowUTC() {
		Date nowUTC = new Date();
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			nowUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formatter.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return nowUTC;
	}
	
	public static Date NbrHour(Date d,int nbrHours) {
		if(d == null)
			return null;
		return new Date(d.getTime() + nbrHours * (3600*1000)); //+ nbrHours 
	}
	public static Date NbrMinutes(Date d,int nbrMinutes) {
		if(d == null)
			return null;
		return new Date(d.getTime() + nbrMinutes * (60000)); //+ nbrHours 
	}
}