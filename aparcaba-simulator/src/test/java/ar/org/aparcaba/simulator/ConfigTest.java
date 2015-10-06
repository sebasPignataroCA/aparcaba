package ar.org.aparcaba.simulator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import ar.org.aparcaba.simulator.utils.ConfigurationManager;
import ar.org.aparcaba.simulator.utils.SpringMongoConfig;
import ar.org.aparcaba.simulator.value.Comune;
import ar.org.aparcaba.simulator.value.Configuration;
import ar.org.aparcaba.simulator.value.TimeLapse;

public class ConfigTest {

	private MongoOperations mongoOperation;

	@SuppressWarnings("resource")
	@Before
	public void setUp() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext( SpringMongoConfig.class );
		mongoOperation = (MongoOperations) ctx.getBean( "mongoTemplate" );
	}

	@After
	public void tearDown() {
		mongoOperation.dropCollection( Configuration.class );
	}

	@Test
	public void testReadConfigurations() {
		Configuration config = buildConfig();
		mongoOperation.insert( config );

		Configuration configuration = ConfigurationManager.getInstance().getConfiguration();

		assertNotNull( configuration );
		assertEquals( 10 , configuration.getCycleInterval() );
		assertNotNull( configuration.getComunes() );
		assertEquals( 2 , configuration.getComunes().size() );
		Comune comune = configuration.getComunes().get( 0 );
		assertEquals( 1 , comune.getFirstSensorId() );
		assertEquals( 99 , comune.getLastSensorId() );
		assertNotNull( comune.getTimeLapses() );
		assertEquals( 2 , comune.getTimeLapses().size() );
		assertNotNull( comune.getTimeLapses().get( 0 ).getDateFrom() );
		assertNotNull( comune.getTimeLapses().get( 0 ).getDateTo() );
		assertEquals( 1 , comune.getTimeLapses().get( 0 ).getNumberOfSensorsToTake() );
		assertEquals( 2 , comune.getTimeLapses().get( 0 ).getNumberOfTriesToTakeSensor() );
		assertEquals( 3 , comune.getTimeLapses().get( 0 ).getParkingStayTimeFrom() );
		assertEquals( 4 , comune.getTimeLapses().get( 0 ).getParkingStayTimeTo() );
		assertEquals( 5 , comune.getTimeLapses().get( 1 ).getNumberOfSensorsToTake() );
		assertEquals( 6 , comune.getTimeLapses().get( 1 ).getNumberOfTriesToTakeSensor() );
		assertEquals( 7 , comune.getTimeLapses().get( 1 ).getParkingStayTimeFrom() );
		assertEquals( 8 , comune.getTimeLapses().get( 1 ).getParkingStayTimeTo() );
		Comune comune2 = configuration.getComunes().get( 1 );
		assertEquals( 100 , comune2.getFirstSensorId() );
		assertEquals( 199 , comune2.getLastSensorId() );

	}

	private Configuration buildConfig() {
		Configuration config = new Configuration();
		config.setCycleInterval( 10 );
		config.setComunes( buildComunes() );
		return config;
	}

	private List<Comune> buildComunes() {
		List<Comune> comunes = new ArrayList<Comune>();
		List<TimeLapse> timeLapses = Arrays.asList( buildTimeLapses( 1 , 2 , 3 , 4 ) , buildTimeLapses( 5 , 6 , 7 , 8 ) );
		comunes.add( buildComune( 1 , 99 , timeLapses ) );
		comunes.add( buildComune( 100 , 199 , timeLapses ) );
		return comunes;
	}

	private Comune buildComune( int i , int j , List<TimeLapse> timeLapses ) {
		Comune comune = new Comune();
		comune.setFirstSensorId( i );
		comune.setLastSensorId( j );
		comune.setTimeLapses( timeLapses );
		return comune;
	}

	private TimeLapse buildTimeLapses( int i , int j , int k , int l ) {
		TimeLapse timeLapse = new TimeLapse();
		timeLapse.setDateFrom( new Date() );
		timeLapse.setDateTo( new Date() );
		timeLapse.setNumberOfSensorsToTake( i );
		timeLapse.setNumberOfTriesToTakeSensor( j );
		timeLapse.setParkingStayTimeFrom( k );
		timeLapse.setParkingStayTimeTo( l );
		return timeLapse;
	}
}
