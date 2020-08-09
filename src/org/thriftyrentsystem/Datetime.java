package org.thriftyrentsystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Datetime {
	private long a;
	private long T;

	public Datetime(Datetime sDate, int FDays) {
		a = ((FDays * 24L + 0) * 60L) * 60000L;
		T = sDate.getTime() + a;
	}

	public Datetime(int setClockForwardInDays) {
		a = ((setClockForwardInDays * 24L + 0) * 60L) * 60000L;
		T = System.currentTimeMillis() + a;
	}

	// returns difference in days
	public static int dDays(Datetime eDate, Datetime sDate) {
		final long Hrdays = 24L;
		final int minhr = 60;
		final int secmin = 60;
		final int millsec = 1000;
		long convertToDays = Hrdays * minhr * secmin * millsec;
		long hirePeriod = eDate.getTime() - sDate.getTime();
		double difference = (double) hirePeriod / (double) convertToDays;
		int r = (int) Math.round(difference);
		return r;
	}
	
	public Datetime() {
		T = System.currentTimeMillis();
	}

	public Datetime(int dd, int mm, int yr) throws ParseException {
		if (isValidDate(dd, mm, yr)) {
			setDate(dd, mm, yr);
		} else {
			throw new ParseException("Invalid Date entered", 0);
		}

	}

	public long getTime() {
		return T;
	}

	
	public String getNameOfDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		return sdf.format(T);
	}

	public String toString() {
		return getFormattedDate();
	}

	

	public String getFormattedDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		long currentTime = getTime();
		Date gct = new Date(currentTime);

		return sdf.format(gct);
	}
	
	public static String getCurrentTime() {
		Date d = new Date(System.currentTimeMillis()); // returns current Date/Time
		return d.toString();
	}

	public String getEightDigitDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		long cTime = getTime();
		Date gct = new Date(cTime);

		return sdf.format(gct);
	}



	private void setDate(int dd, int mm, int yr) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(yr, mm - 1, dd, 0, 0);

		java.util.Date date = calendar.getTime();

		T = date.getTime();
	}

	// Advances date/time by specified days, hours and minutes for testing purposes
	public void setAdvance(int d, int H, int m) {
		a = ((d * 24L + H) * 60L) * 60000L;
	}

	private boolean isLeap(int Y) {
		return (((Y % 4 == 0) && (Y % 100 != 0)) || (Y % 400 == 0));
	}

	private boolean isValidDate(int c, int d, int e) {
		if (e > 9999 || e < 1800)
			return false;
		if (d < 1 || d > 12)
			return false;
		if (c < 1 || c > 31)
			return false;

		if (d == 2) { // For Feb Month
			if (isLeap(e))
				return (c <= 29);
			else
				return (c <= 28);
		}
		if (d == 4 || d == 6 || d == 9 || d == 11)
			return (c <= 30);

		return true;
	}

}
