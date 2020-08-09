package org.thriftyrentsystem;

public class VehicleRentalRecord {

	private final String rentID;

	private final Datetime rentDate;

	private final Datetime estimatedReturnDate;

	private Datetime actualReturnDate;

	private Double rentalFee;

	private Double lateFee;
	
	
	public VehicleRentalRecord(String rID, Datetime rDate, Datetime eReturnDate) {
		this.rentID = rID;
		this.rentDate = rDate;
		this.estimatedReturnDate = eReturnDate;
	}

	public String getRentID() {
		return rentID;
	}

	
	public Datetime getRentDate() {
		return rentDate;
	}

	public Datetime getEstimatedReturnDate() {
		return estimatedReturnDate;
	}


	public Datetime getActualReturnDate() {
		return actualReturnDate;
	}

	public void setActualReturnDate(Datetime actualReturnDate) {
		this.actualReturnDate = actualReturnDate;
	}
	
	public void setLateFee(Double lateFee) {
		this.lateFee = lateFee;
	}
	
	public void setRentalFee(Double rentalFee) {
		this.rentalFee = rentalFee;
	}

	@Override
	public String toString() {
		StringBuilder toStringBuilder = new StringBuilder();
		toStringBuilder.append(rentID);
		toStringBuilder.append(":");
		toStringBuilder.append(rentDate);
		toStringBuilder.append(":");
		toStringBuilder.append(estimatedReturnDate);
		toStringBuilder.append(":");
		if (actualReturnDate == null) {
			toStringBuilder.append("none");
		} else {
			toStringBuilder.append(actualReturnDate);
		}
		
		toStringBuilder.append(":");
		
		if (rentalFee == null) {
			toStringBuilder.append("none");
		} else {
			toStringBuilder.append(String.format ("%.2f", rentalFee));
		}
		
		toStringBuilder.append(":");
		if (lateFee == null) {
			toStringBuilder.append("none");
		} else {
			toStringBuilder.append(String.format ("%.2f", lateFee));
		}

		return toStringBuilder.toString();
	}

	public String getDetails() {
		StringBuilder toStringBuilder = new StringBuilder();
		toStringBuilder.append("Record ID:");
		toStringBuilder.append(rentID);
		toStringBuilder.append("\n");
		toStringBuilder.append("Rent Date:");
		toStringBuilder.append(rentDate);
		toStringBuilder.append("\n");
		toStringBuilder.append("Estimated Return Date:	");
		toStringBuilder.append(estimatedReturnDate);
		toStringBuilder.append("\n");
		if (actualReturnDate != null) {
			toStringBuilder.append("Actual Return Date:		");
			toStringBuilder.append(actualReturnDate);
			toStringBuilder.append("\n");
		}
		if (rentalFee != null) {
			toStringBuilder.append("Rental Fee:	");
			toStringBuilder.append(String.format ("%.2f", rentalFee));
			toStringBuilder.append("\n");
		}
		if (lateFee != null) {
			toStringBuilder.append("Late Fee:");
			toStringBuilder.append(String.format ("%.2f", lateFee));
			toStringBuilder.append("\n");
		}

		return toStringBuilder.toString();
	}

	

}
