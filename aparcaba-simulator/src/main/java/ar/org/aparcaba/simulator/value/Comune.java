package ar.org.aparcaba.simulator.value;

import ar.org.aparcaba.simulator.utils.RandomUtil;

public class Comune {

	private int firstSensorId;
	private int lastSensorId;

	public Sensor getRandomSensor() {
		return new Sensor( RandomUtil.getRandom( firstSensorId , lastSensorId ) );
	}

}
