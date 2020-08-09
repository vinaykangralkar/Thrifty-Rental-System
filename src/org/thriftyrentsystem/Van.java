package org.thriftyrentsystem;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Van extends Vehicle {

	public static final Map<Integer, Double> availableSize = new HashMap<>();
	private Datetime oldMaintenanceDate;

	static {
		availableSize.put(15, 235d);
	}

	

	@Override
	public Double getLateFee() {
		return 299d;
	}

	@Override
	protected boolean validateRentRequest(Datetime rentDate, int numOfRentDay) {
		//Checks if the estimated return date exceeds the next maintenance date
		//if so returns false indicating van cannot be rented
		//else returns true indicating van can be rented
		if (numOfRentDay < 1) {
			return false;
		}
		Datetime nextMaintenanceDate = new Datetime(oldMaintenanceDate, 12);
		Datetime estimatedReturnDate = new Datetime(rentDate, numOfRentDay);
		if (Datetime.dDays(estimatedReturnDate, nextMaintenanceDate) > 0) {
			System.out.println("Estimated return date is after the next maintenance date. Cannot be rented");
			return false;
		}

		return true;
	}

	@Override
	public boolean completeMaintenance(Datetime completionDate) {

		if (super.completeMaintenance(completionDate)) {
			this.oldMaintenanceDate = completionDate;
			return true;
		} else {
			return false;
		}
	}

	protected String vehicleDetails() {
		StringBuilder builder = new StringBuilder(super.vehicleDetails());
		builder.append("Last maintenance date:				");
		builder.append(oldMaintenanceDate);
		builder.append("\n");
		return builder.toString();

	}

	@Override
	public boolean validateVehicleCreation() {
		if (availableSize.containsKey(getNoOfSeats())) {
			return true;
		}
		return false;
	}

	@Override
	protected Double getRentPerDayRate() {
		return availableSize.get(getNoOfSeats());
	}

	@Override
	public String toString() {
		return super.toString() + ":" + oldMaintenanceDate;
	}

	public Van(String vhId, String y, String m, String model, int Seats) {
		//Calling the super class constructor to initialize van attributes
		super(vhId, y, m, model, Seats);
		Calendar calendar = Calendar.getInstance();
		try {
			oldMaintenanceDate = new Datetime(calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.YEAR));
		} catch (ParseException e) {
			System.out.print("Invalid date ");
			return;
		}

	}
}
