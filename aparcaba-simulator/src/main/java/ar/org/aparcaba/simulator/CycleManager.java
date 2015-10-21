package ar.org.aparcaba.simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import ar.org.aparcaba.simulator.utils.ConfigurationManager;
import ar.org.aparcaba.simulator.value.Comune;
import ar.org.aparcaba.simulator.value.Sensor;
import ar.org.aparcaba.simulator.value.TimeLapse;

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
			TimeLapse currentTimeLapse = comune.getCurrentTimeLapse();
			int numberOfSensorsToTake = currentTimeLapse.getNumberOfSensorsToTake();

			for ( int i = 0 ; i < numberOfSensorsToTake ; i++ ) {
				tryToTakeSensor( comune , currentTimeLapse );
			}
		}
	}

	private void tryToTakeSensor( Comune comune , TimeLapse timeLapse ) {
		for ( int i = 0 ; i < timeLapse.getNumberOfTriesToTakeSensor() ; i++ ) {
			Sensor sensor = comune.getRandomSensor();
			if ( sensorManager.isFree( sensor ) ) {
				takeSensor( sensor , timeLapse );
				return;
			}
		}
	}

	private void takeSensor( Sensor sensor , TimeLapse timeLapse ) {
		sensorManager.take( sensor );
		setSensorToRelease( sensor , timeLapse.getParkingStayTime() );
	}

	private void setSensorToRelease( Sensor sensor , int parkingStayTime ) {
		int releaseCycle = cycle + parkingStayTime;
		if ( !toRelease.containsKey( releaseCycle ) ) {
			toRelease.put( releaseCycle , new ArrayList<Sensor>() );
		}
		toRelease.get( releaseCycle ).add( sensor );
	}

	public void initializeSensorsStatusesFromApi() {
		List<Sensor> apiSensors = sensorManager.callSensorsApi();
		for ( Comune comune : config.getComunes() ) {
			for ( int i = comune.getFirstSensorId() ; i <= comune.getLastSensorId() ; i++ ) {
				initializeSensor( comune , comune.getSensor( i ) , apiSensors );
			}
		}
	}

	private void initializeSensor( Comune comune , Sensor sensor , List<Sensor> apiSensors ) {
		if ( apiSensors.get( apiSensors.indexOf( sensor ) ).getCovered() ) {
			int parkingStayTime = comune.getCurrentTimeLapse().getParkingStayTime()
					- comune.getCurrentTimeLapse().getParkingStayTimeFrom();
			setSensorToRelease( sensor , parkingStayTime );
			sensorManager.setSensorAsTaken( sensor );
		}
	}

}
