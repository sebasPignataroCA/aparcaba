package ar.org.aparcaba.simulator.value;

import java.util.Date;

import ar.org.aparcaba.simulator.utils.RandomUtil;

public class TimeLapse {

	private Date dateFrom;

	private Date dateTo;

	private int numberOfSensorsToTake;

	private int numberOfTriesToTakeSensor;

	private int parkingStayTimeFrom;

	private int parkingStayTimeTo;

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom( Date dateFrom ) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo( Date dateTo ) {
		this.dateTo = dateTo;
	}

	public int getNumberOfSensorsToTake() {
		return numberOfSensorsToTake;
	}

	public void setNumberOfSensorsToTake( int numberOfSensorsToTake ) {
		this.numberOfSensorsToTake = numberOfSensorsToTake;
	}

	public int getNumberOfTriesToTakeSensor() {
		return numberOfTriesToTakeSensor;
	}

	public void setNumberOfTriesToTakeSensor( int numberOfTriesToTakeSensor ) {
		this.numberOfTriesToTakeSensor = numberOfTriesToTakeSensor;
	}

	public int getParkingStayTime() {
		return RandomUtil.getRandom( parkingStayTimeFrom , parkingStayTimeTo );
	}

	public int getParkingStayTimeFrom() {
		return parkingStayTimeFrom;
	}

	public void setParkingStayTimeFrom( int parkingStayTimeFrom ) {
		this.parkingStayTimeFrom = parkingStayTimeFrom;
	}

	public int getParkingStayTimeTo() {
		return parkingStayTimeTo;
	}

	public void setParkingStayTimeTo( int parkingStayTimeTo ) {
		this.parkingStayTimeTo = parkingStayTimeTo;
	}
}
