package ar.org.aparcaba.simulator.value;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.org.aparcaba.simulator.utils.DateUtil;
import ar.org.aparcaba.simulator.utils.RandomUtil;

public class Comune {

	private int firstSensorId;
	private int lastSensorId;

	private List<TimeLapse> timeLapses = new ArrayList<TimeLapse>();

	public Sensor getRandomSensor() {
		return new Sensor( RandomUtil.getRandom( getFirstSensorId() , getLastSensorId() ) );
	}

	public List<TimeLapse> getTimeLapses() {
		return timeLapses;
	}

	public void setTimeLapses( List<TimeLapse> timeLapses ) {
		this.timeLapses = timeLapses;
	}

	public TimeLapse getCurrentTimeLapse() {
		Date now = new Date();
		for ( TimeLapse timeLapse : timeLapses ) {
			if ( DateUtil.isWithinInterval( now , timeLapse.getDateFrom() , timeLapse.getDateTo() ) ) {
				return timeLapse;
			}
		}
		throw new RuntimeException( "No existe un rango que contenga a la fecha actual: " + now.toString() );
	}

	public int getFirstSensorId() {
		return firstSensorId;
	}

	public void setFirstSensorId( int firstSensorId ) {
		this.firstSensorId = firstSensorId;
	}

	public int getLastSensorId() {
		return lastSensorId;
	}

	public void setLastSensorId( int lastSensorId ) {
		this.lastSensorId = lastSensorId;
	}

	public Sensor getSensor( int id ) {
		return new Sensor( id );
	}
}
