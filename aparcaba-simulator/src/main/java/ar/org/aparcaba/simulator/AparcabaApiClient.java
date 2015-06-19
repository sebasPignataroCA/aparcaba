package ar.org.aparcaba.simulator;

import ar.org.aparcaba.simulator.value.Sensor;

public class AparcabaApiClient {

	private static AparcabaApiClient instance;

	public static AparcabaApiClient getInstance() {
		if ( instance == null ) {
			instance = new AparcabaApiClient();
		}
		return instance;
	}

	private AparcabaApiClient() {}

	public void release( Sensor sensor ) {
		// TODO Auto-generated method stub

	}

	public void take( Sensor sensor ) {
		// TODO Auto-generated method stub

	};

}
