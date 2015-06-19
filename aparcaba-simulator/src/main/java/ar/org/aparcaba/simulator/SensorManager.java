package ar.org.aparcaba.simulator;

import java.util.HashSet;
import java.util.Set;

import ar.org.aparcaba.simulator.value.Sensor;

public class SensorManager {

	private static SensorManager instance;

	private AparcabaApiClient apiClient = AparcabaApiClient.getInstance();
	private Set<Sensor> takenSensors = new HashSet<Sensor>();

	public void release( Sensor sensor ) {
		apiClient.release( sensor );
		takenSensors.remove( sensor );
	}

	public boolean isFree( Sensor sensor ) {
		return takenSensors.contains( sensor );
	}

	public void take( Sensor sensor ) {
		apiClient.take( sensor );
		takenSensors.add( sensor );
	}

	public static SensorManager getInstance() {
		if ( instance == null ) {
			instance = new SensorManager();
		}
		return instance;
	}

	private SensorManager() {};

}
