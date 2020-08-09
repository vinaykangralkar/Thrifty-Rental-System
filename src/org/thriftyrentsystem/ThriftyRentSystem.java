package org.thriftyrentsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ThriftyRentSystem {

	private Map<String , Vehicle> vehiclesMap = new HashMap<>();

	//The addVehicle() method is used to add a vehicle
	public void addVehicle() throws IOException {
		System.out.print("Vehicle ID: ");
		String vID = getUserInput();
		vID = vID.toUpperCase();
		
		if(vehiclesMap.containsKey(vID)) {
			//The if statement validates the vehicle ID
			System.out.println("\n Duplicate Vehicle ID \n");
			return;
		}
		System.out.print("Year of Manufacture: ");
		String yy = getUserInput();
		System.out.print("Make: ");
		String mak = getUserInput();
		System.out.print("Model: ");
		String mdl = getUserInput();
		System.out.print("No Of Seats: ");
		String seats = getUserInput();
		
		Vehicle vechile; 
		try {
			if(vID.startsWith("C_")) {
			//Vehicle ID should contain C_ if its a car
				vechile = new Car(vID, yy, mak, mdl, Integer.parseInt(seats));
			} else if(vID.startsWith("V_")) {
			//Vehicle ID should contain V_ if its a van
				vechile = new Van(vID, yy, mak, mdl, Integer.parseInt(seats));
			} else {
			//If the vehicle does not start with either V_ or C_ the user is returned back to the main menu
				System.out.println("Vehicle ID should start with V_ for Van and C_ for car");
				return;
			}
			if(!vechile.validateVehicleCreation()) {
			//If the number of seats specified by the user is invalid then the user is returned back to the main menu
				System.out.println("Invalid number of seats " + seats );
				return;
			}
			
			vehiclesMap.put(vID, vechile);
			System.out.println(" Vehicle created successfully \n \n");
		} catch(NumberFormatException nfe) {
			System.out.println(" Invalid number of passanger \n \n");
		}
	}

	

	public void rentVehicle() throws IOException {
		//This method is used when a user wants to rent a vehicle
		
		System.out.print("Vehicle ID: ");
		String vID = getUserInput();
		vID = vID.toUpperCase();
		Vehicle v  = vehiclesMap.get(vID.toUpperCase());
		if(v == null) {
			//This statement validates vehicle ID
			
			System.out.print(" invalid vehicle Id ");
			return;
		}
		if(v.getStatus() != VehicleStatus.AVAILABLE) {
			//This statement gets executed if the vehicle is not available to be rented
			
			System.out.println("Vehicle" + vID + " cannot not be rented \n");
			return;
		}
		System.out.print("Customer ID: ");
		String cID = getUserInput();
		Datetime rentDateTime;
		System.out.print("Rent date( dd/mm/yyyy): ");
		try {	
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			df.setLenient(true);
			Calendar csl = Calendar.getInstance();
			csl.setTime(df.parse(getUserInput()));
			rentDateTime = new Datetime(csl.get(Calendar.DATE), csl.get(Calendar.MONTH), csl.get(Calendar.YEAR));
		} catch(ParseException pe) {
			System.out.print("Invalid Rental date ");
			return;
		}
		Integer days;
		try {
			System.out.print("For how many days?: ");
			days = Integer.parseInt(getUserInput());
		}catch(NumberFormatException nfe) {
			System.out.print("Invalid days ");
			return;
		}
		
		v.rent(cID, rentDateTime, days);
	}

	public void returnVehicle() throws IOException {
		//This method is executed when the user is trying to return the vehicle
		
		System.out.print("Vehicle ID: ");
		String vID = getUserInput();
		Vehicle v  = vehiclesMap.get(vID.toUpperCase());
		if(v == null) {
			//This statement validates vehicle ID
			
			System.out.print("ERROR: Invalid vehicle id ");
			return;
		}
		Datetime returnDateTime;
		System.out.print("Return date( dd/mm/yyyy): ");
		try {	
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Calendar csl = Calendar.getInstance();
			csl.setTime(df.parse(getUserInput()));
			returnDateTime = new Datetime(csl.get(Calendar.DATE), csl.get(Calendar.MONTH), csl.get(Calendar.YEAR));
		} catch(ParseException pe) {
			System.out.print("ERROR: Invalid Return date ");
			return;
		}
		
		v.returnVehicle(returnDateTime);
	}

	public void vehicleMaintenance() throws IOException {
		System.out.print("Vehicle ID: ");
		String vehicleID = getUserInput();
		Vehicle vehicle  = vehiclesMap.get(vehicleID.toUpperCase());
		if(vehicle == null) {
			//This statement validates vehicle ID
			
			System.out.print("ERROR: Invalid vehicle id ");
			return;
		}
		vehicle.performMaintenance();
	}

	public void completeMaintenance() throws IOException {
		System.out.print("Vehicle ID: ");
		String vehicleID = getUserInput();
		Vehicle vehicle  = vehiclesMap.get(vehicleID.toUpperCase());
		if(vehicle == null) {
			//This statement validates vehicle ID
			
			System.out.print("ERROR: Invalid vehicle id ");
			return;
		}
		Datetime returnDateTime;
		System.out.print("Maintenance completion date (dd/mm/yyyy): ");
		try {	
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Calendar csl = Calendar.getInstance();
			csl.setTime(df.parse(getUserInput()));
			returnDateTime = new Datetime(csl.get(Calendar.DATE), csl.get(Calendar.MONTH), csl.get(Calendar.YEAR));
		} catch(ParseException pe) {
			System.out.print("ERROR: Invalid Return date ");
			return;
		}
		vehicle.completeMaintenance(returnDateTime);
	}
	
	public void displayAll() {
		//Displays all the information about vehicle
		
		vehiclesMap.forEach(new BiConsumer<String, Vehicle>() {

			@Override
			public void accept(String t, Vehicle u) {
				System.out.println(u.getDetails());
			}
		});
	}
	
	private String getUserInput() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		return br.readLine();
	}
	
	public static void main(String args[]) {
		ThriftyRentSystem system = new ThriftyRentSystem();
		system.showMenu();
	}

	public void showMenu() {
		System.out.println("\n\n****** Rental Company ******\n");
		System.out.println("Add vehicle:		1");
		System.out.println("Rent vehicle:		2");
		System.out.println("Return vehicle:		3");
		System.out.println("Vehicle Maintenance:	4");
		System.out.println("Complete Maintenance:	5");
		System.out.println("Display All Vehicles:	6");
		System.out.println("Exit Program:		7");
		System.out.print("Enter your choice: ");
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        
	        try {
	            int input = Integer.parseInt(br.readLine());
	            if(input < 0 || input > 7) {
	                System.out.println("Invalid selection, please try again\n");
	                showMenu();
	            }
	            switch(input) {
	            case 1:
	            	addVehicle();
	            	break;
	            case 2:
	            	rentVehicle();
	            	break;
	            case 3:
	            	returnVehicle();
	            	break;
	            case 4:
	            	vehicleMaintenance();
	            	break;
	            case 5:
	            	completeMaintenance();
	            	break;
	            case 6:
	            	displayAll();
	            	break;
	            case 7:
	            	System.exit(0);
	            }
	            showMenu();
	        }catch (IOException ioe) {
	            System.out.println("IO error \r\n");
	            showMenu();
	        }catch(NumberFormatException nfe) {
	        	 System.out.println("Not  valid number");
	        	 showMenu();
	        }
	}
}
