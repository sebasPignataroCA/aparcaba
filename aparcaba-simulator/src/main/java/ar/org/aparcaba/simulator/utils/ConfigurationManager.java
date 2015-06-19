package ar.org.aparcaba.simulator.utils;

import java.util.List;

import ar.org.aparcaba.simulator.value.Comune;

public class ConfigurationManager {

	private static ConfigurationManager instance;

	public static ConfigurationManager getInstance() {
		if ( instance == null ) {
			instance = new ConfigurationManager();
		}
		return instance;
	}

	private ConfigurationManager() {}

	public long getCycleInterval() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Comune> getComunes() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getNumberOfSensorsToTake() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNumberOfTriesToTakeSensor() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getParkingStayTime() {
		// TODO Auto-generated method stub
		return 0;
	};

}
