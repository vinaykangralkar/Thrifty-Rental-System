package org.thriftyrentsystem;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle {

	private final String vhlId;

	private final String year;

	private final String make;

	private final String model;

	private final int Seats;

	private VehicleStatus status;

	private final List<VehicleRentalRecord> rentalR;

	public Vehicle(String vId, String y, String m, String model, int Seats) {
		this.vhlId = vId;
		this.year = y;
		this.make = m;
		this.model = model;
		this.Seats = Seats;
		status = VehicleStatus.AVAILABLE;
		rentalR = new ArrayList<VehicleRentalRecord>();
	}

	public boolean rent(String customerId, Datetime rentDate, int numOfRentDay) {
		//This block of code checks if the vehicle is available for renting
		//Returns true if vehicle is available
		//Returns false if vehicle is not available
		if (status != VehicleStatus.AVAILABLE) {
			System.out.println("Vehicle is not available for rent");
		} else if (validateRentRequest(rentDate, numOfRentDay)) {
			rentDate.setAdvance(numOfRentDay, 0, 0);
			VehicleRentalRecord rentalRecord = new VehicleRentalRecord(
					vhlId + "_" + customerId + "_" + rentDate.getEightDigitDate(), rentDate,
					new Datetime(rentDate, numOfRentDay));
			this.status = VehicleStatus.RENTED;
			rentalR.add(0, rentalRecord);
			System.out.println("Vehicle " + vhlId + " rented successfully to Customer " + customerId);
			return true;
		}

		return false;
	}

	public boolean returnVehicle(Datetime returnDate) {
		//This method is executed when a user wants to return a vehicle
		if (status != VehicleStatus.RENTED) {
			System.out.println("Vehicle is not rented. Can not be returned \n");
			return false;
		}
		VehicleRentalRecord currentRentRecord = rentalR.get(0);
		if (Datetime.dDays(returnDate, currentRentRecord.getRentDate()) > 0) {
			currentRentRecord.setActualReturnDate(returnDate);

			// Check if current return date is greater than estimated return date
			if (Datetime.dDays(returnDate, currentRentRecord.getEstimatedReturnDate()) > 0) {
				int rentDays = Datetime.dDays(currentRentRecord.getEstimatedReturnDate(),
						currentRentRecord.getRentDate());
				int lateFeeDays = Datetime.dDays(returnDate, currentRentRecord.getEstimatedReturnDate());
				currentRentRecord.setRentalFee(rentDays * getRentPerDayRate());
				currentRentRecord.setLateFee(lateFeeDays * getLateFee());
			} else {
				int rentDays = Datetime.dDays(returnDate, currentRentRecord.getRentDate());
				currentRentRecord.setRentalFee(rentDays * getRentPerDayRate());
				currentRentRecord.setLateFee(0.00);
			}
			this.status = VehicleStatus.AVAILABLE;
			System.out.println("Vehicle returned successfully \n");
			System.out.println(currentRentRecord.getDetails());
			System.out.println("\n");
			return true;
		}
		System.out.println("Vehicle can not returned on date " + returnDate);
		return false;
	}

	public boolean performMaintenance() {
		//This method performances maintenance
		//checks the vehicle status
		//if the vehicle is available i.e. it is not under maintenance, it is put under maintenance
		if (status == VehicleStatus.AVAILABLE) {
			status = VehicleStatus.UNDER_MANTENANACE;
			System.out.println("Vehicle " + vhlId + " is now under maintenance \n");
			return true;
		}
		System.out.println("Vehicle " + vhlId + " is not available for maintenance");
		return false;
	}

	public boolean completeMaintenance(Datetime completionDate) {
		if (status == VehicleStatus.UNDER_MANTENANACE) {
			status = VehicleStatus.AVAILABLE;//Sets the vehicle as available once the maintenance is complete
			return true;
		}
		System.out.println("Vehicle " + vhlId + " cannot be returned");
		return false;
	}

	@Override
	public String toString() {
		//Returns details of the vehicle in the form of a string
		return vhlId + ":" + year + ":" + make + ":" + model + ":" + Seats + ":" + status;
	}

	public String getDetails() {
		//Returns details of the vehicle along with the rental history of the vehicle
		return vehicleDetails() + rentalDetails();
	}

	protected String rentalDetails() {
		StringBuilder builder = new StringBuilder();
		
		//Checks if the Vehicle has any rental record
		if (rentalR.size() == 0) {
			builder.append("RENTAL RECORD:	empty");
		} else {
			builder.append("-----RENTAL RECORD------- \n");
		}

		rentalR.stream().limit(10).forEach(rentalRecord -> {
			builder.append("------------------------------------- \n");
			builder.append(rentalRecord.getDetails());
		});
		return builder.toString();
	}

	protected String vehicleDetails() {
		StringBuilder toStringBuilder = new StringBuilder();
		toStringBuilder.append("Vehicle ID:				");
		toStringBuilder.append(vhlId);
		toStringBuilder.append("\n");
		toStringBuilder.append("Year:				");
		toStringBuilder.append(year);
		toStringBuilder.append("\n");
		toStringBuilder.append("Make:	");
		toStringBuilder.append(make);
		toStringBuilder.append("\n");
		toStringBuilder.append("Model:				");
		toStringBuilder.append(model);
		toStringBuilder.append("\n");
		toStringBuilder.append("Number of seats:				");
		toStringBuilder.append(Seats);
		toStringBuilder.append("\n");
		toStringBuilder.append("Status:				");
		toStringBuilder.append(status);
		toStringBuilder.append("\n");
		return toStringBuilder.toString();
	}

	public VehicleStatus getStatus() {
		return status;
	}
	
	protected int getNoOfSeats() {
		return Seats;
	}

	//Abstract methods
	public abstract Double getLateFee();

	protected abstract boolean validateRentRequest(Datetime rentDate, int numOfRentDay);

	public abstract boolean validateVehicleCreation();

	protected abstract Double getRentPerDayRate();

}
