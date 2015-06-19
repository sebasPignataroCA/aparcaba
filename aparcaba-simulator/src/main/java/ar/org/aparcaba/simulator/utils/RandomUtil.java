package ar.org.aparcaba.simulator.utils;

import java.util.Random;

public class RandomUtil {

	public static int getRandom( int firstSensorId , int lastSensorId ) {
		return new Random().nextInt( lastSensorId - firstSensorId ) + firstSensorId;
	}

}
