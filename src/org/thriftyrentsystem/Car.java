package org.thriftyrentsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Car extends Vehicle {

	public static final Map<Integer, Double>availableSize = new HashMap<>();
	
	private static final List<String> listforMinTwoDayRent = new ArrayList<>();
	private static final List<String> listforMinThreeDayRent = new ArrayList<>();
	static {
		availableSize.put(4, 78d);//4 seater car
		availableSize.put(7, 113d);//7 seater car
		listforMinTwoDayRent.add("Sunday");
		listforMinTwoDayRent.add("Monday");
		listforMinTwoDayRent.add("Tuesday");
		listforMinTwoDayRent.add("Wednesday");
		listforMinTwoDayRent.add("Thursday");
		
		listforMinThreeDayRent.add("Friday");
		
		listforMinThreeDayRent.add("Saturday");
		
	}
	public Car(String vechId, String yr, String make, String mdl, int NSeats) {
		//Calling super class constructor to initialize car attributes
		super(vechId, yr, make, mdl, NSeats);
	}
	
	@Override
	protected Double getRentPerDayRate() {
		return availableSize.get(getNoOfSeats());
	}
	
	@Override
	public boolean validateVehicleCreation() {
		if(availableSize.containsKey(getNoOfSeats())) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	@Override
	protected boolean validateRentRequest(Datetime rentDate, int numOfRentDay) {
		//Check if number of days for renting a car is more than 14
		if(numOfRentDay > 14) {
			System.out.println("Car cannot be rented for more than 14 days \n");
			return false;
		}
		if(listforMinTwoDayRent.contains(rentDate.getNameOfDay()) && numOfRentDay <2) {
			//Car cannot be rented for less than 2 days if the rent day is between Sunday and Thursday inclusive 
			System.out.println("Car cannot be rented for less than 2 days on " + listforMinTwoDayRent);
			return false;
		} else if(listforMinThreeDayRent.contains(rentDate.getNameOfDay()) && numOfRentDay <3) {
			//Car cannot be rented for less than 3 days if the rent day is Friday or Saturday
			System.out.println("Car cannot be rented for less tan 3 days on " + listforMinTwoDayRent);
			return false;
		}
		return true;
	}
	
	@Override
	public Double getLateFee() {
		//If the return date of the car exceeds the estimated return date then late fee applies
		//and this block of code is executed
		return getRentPerDayRate() * 1.25;
	}

	
	
	

}
