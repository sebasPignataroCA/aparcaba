package ar.org.aparcaba.simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import ar.org.aparcaba.simulator.utils.ConfigurationManager;
import ar.org.aparcaba.simulator.value.Comune;
import ar.org.aparcaba.simulator.value.Sensor;

public class CycleManager {

	private int cycle = 0;
	private Map<Integer , List<Sensor>> toRelease = new HashMap<Integer , List<Sensor>>();
	private SensorManager sensorManager = SensorManager.getInstance();
	private ConfigurationManager config = ConfigurationManager.getInstance();

	public void execute() {
		releaseSensors();
		takeSensors();
		cycle++;
	}

	private void releaseSensors() {
		List<Sensor> sensorsToRelease = toRelease.get( cycle );
		if ( CollectionUtils.isNotEmpty( sensorsToRelease ) ) {
			for ( Sensor sensor : sensorsToRelease ) {
				sensorManager.release( sensor );
			}
			toRelease.remove( cycle );
		}
	}

	private void takeSensors() {
		for ( Comune comune : config.getComunes() ) {
			int numberOfSensorsToTake = config.getNumberOfSensorsToTake();
			for ( int i = 0 ; i < numberOfSensorsToTake ; i++ ) {
				tryToTakeSensor( comune );
			}
		}
	}

	private void tryToTakeSensor( Comune comune ) {
		for ( int i = 0 ; i < config.getNumberOfTriesToTakeSensor() ; i++ ) {
			Sensor sensor = comune.getRandomSensor();
			if ( sensorManager.isFree( sensor ) ) {
				takeSensor( sensor );
				return;
			}
		}
	}

	private void takeSensor( Sensor sensor ) {
		int parkingStayTime = config.getParkingStayTime();
		sensorManager.take( sensor );
		setSensorToRelease( sensor , parkingStayTime );
	}

	private void setSensorToRelease( Sensor sensor , int parkingStayTime ) {
		int releaseCycle = cycle + parkingStayTime;
		if ( !toRelease.containsKey( releaseCycle ) ) {
			toRelease.put( releaseCycle , new ArrayList<Sensor>() );
		}
		toRelease.get( releaseCycle ).add( sensor );
	}

}
