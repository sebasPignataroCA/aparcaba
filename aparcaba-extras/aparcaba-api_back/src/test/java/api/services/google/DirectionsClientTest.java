package api.services.google;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class DirectionsClientTest {

	private DirectionsClient directionsClient = DirectionsClient.getInstance();
	private final String origin = "Congreso 2171, Ciudad Autónoma Buenos Aires";
	private final String destination = "Medrano 951, Ciudad Autónoma Buenos Aires";
	private final String EXPECTED_MODE = "DRIVING";
	private final String EXPECTED_STATUS = "OK";

	/**
	 * Testea instancia singleton.
	 */
	@Test
	public void test_singleton() {
		assertEquals( directionsClient , DirectionsClient.getInstance() );
	}

	// JSON TESTS ----------------------------------------------------
	/**
	 * Testea que el servicio haya corrido con status ok, en JSON
	 */
	@Test
	public void test_request_json_statusOK() {
		String response = directionsClient.request( origin , destination , DirectionsClient.JSON );
		String statusResultSeparation[] = response.split( "\"status\"\\s:\\s\"" );
		if ( statusResultSeparation.length > 1 ) {
			assertEquals( EXPECTED_STATUS , statusResultSeparation[1].split( "\"" )[0] );
			return;
		}
		fail( "JSON STATUS" );
	}

	/**
	 * Testea que exista una ruta, en la respuesta JSON
	 */
	@Test
	public void test_request_json_hasRoute() {
		String response = directionsClient.request( origin , destination , DirectionsClient.JSON );
		assertTrue( response.contains( "\"routes\" : [" ) );
	}

	/**
	 * Testea que existan legs, en la respuesta JSON
	 */
	@Test
	public void test_request_json_hasLegs() {
		String response = directionsClient.request( origin , destination , DirectionsClient.JSON );
		assertTrue( response.contains( "\"legs\" : [" ) );
	}

	/**
	 * Testea que existan datos de distancia en respuesta JSON
	 */
	@Test
	public void test_request_json_hasDistanceData() {
		String response = directionsClient.request( origin , destination , DirectionsClient.JSON );
		String regex = "\"distance\"\\s*:\\s*\\{\\s*\"text\"\\s*:\\s*\"\\d+(?:.\\d+)?\\s*km\",\\s*\"value\"\\s*:\\s*\\d+\\s*\\},";
		Pattern pat = Pattern.compile( regex , Pattern.DOTALL );
		Matcher mat = pat.matcher( response );
		assertTrue( mat.find() );
	}

	/**
	 * Testea que existan datos de tiempos en respuesta JSON
	 */
	@Test
	public void test_request_json_hasDurationData() {
		String response = directionsClient.request( origin , destination , DirectionsClient.JSON );
		String regex = "\"duration\"\\s*:\\s*\\{\\s*\"text\"\\s*:\\s*\"\\d+\\s*min\",\\s*\"value\"\\s*:\\s*\\d+\\s*\\},";
		Pattern pat = Pattern.compile( regex , Pattern.DOTALL );
		Matcher mat = pat.matcher( response );
		assertTrue( mat.find() );
	}

	/**
	 * Testea que existan datos de start location, en respuesta JSON
	 */
	@Test
	public void test_request_json_hasStartLocation() {
		String response = directionsClient.request( origin , destination , DirectionsClient.JSON );
		String regex = "\"start_location\"\\s*:\\s*\\{\\s*\"lat\"\\s*:\\s*-?\\d+.\\d*,\\s*\"lng\"\\s*:\\s*-?\\d+.\\d*\\s*\\},";
		Pattern pat = Pattern.compile( regex , Pattern.DOTALL );
		Matcher mat = pat.matcher( response );
		assertTrue( mat.find() );
	}

	/**
	 * Testea que existan datos de end location, en respuesta JSON
	 */
	@Test
	public void test_request_json_hasEndtLocation() {
		String response = directionsClient.request( origin , destination , DirectionsClient.JSON );
		String regex = "\"end_location\"\\s*:\\s*\\{\\s*\"lat\"\\s*:\\s*-?\\d+.\\d*,\\s*\"lng\"\\s*:\\s*-?\\d+.\\d*\\s*\\},";
		Pattern pat = Pattern.compile( regex , Pattern.DOTALL );
		Matcher mat = pat.matcher( response );
		assertTrue( mat.find() );
	}

	/**
	 * Testea que todos los modos de viaje devueltos sean DRIVING, en JSON response.
	 */
	@Test
	public void test_request_json_modeDriving() {
		String response = directionsClient.request( origin , destination , DirectionsClient.JSON );
		Pattern pat = Pattern.compile( "travel_mode\"\\s*:\\s*\"([^\"]+)\"" , Pattern.DOTALL );
		Matcher mat = pat.matcher( response );
		while ( mat.find() )
			assertEquals( EXPECTED_MODE , mat.group( 1 ) );
	}

	// XML TESTS -----------------------------------------------------
	/**
	 * Testea que el servicio haya corrido con status ok, en XML
	 */
	@Test
	public void test_request_xml_statusOK() {
		String response = directionsClient.request( origin , destination , DirectionsClient.XML );
		assertTrue( response.contains( "<status>OK</status>" ) );
	}

	/**
	 * Testea que exista una ruta, en la respuesta XML
	 */
	@Test
	public void test_request_xml_hasRoute() {
		String response = directionsClient.request( origin , destination , DirectionsClient.XML );
		assertTrue( response.contains( "<route>" ) && response.contains( "</route>" ) );
	}

	/**
	 * Testea que existan legs, en la respuesta XML
	 */
	@Test
	public void test_request_xml_hasLegs() {
		String response = directionsClient.request( origin , destination , DirectionsClient.XML );
		assertTrue( response.contains( "<leg>" ) && response.contains( "</leg>" ) );
	}

	/**
	 * Testea que existan datos de distancia en respuesta XML
	 */
	@Test
	public void test_request_xml_hasDistanceData() {
		String response = directionsClient.request( origin , destination , DirectionsClient.XML );
		String regex = "<distance>\\s*<value>\\d+</value>\\s*<text>\\d+(?:.\\d+)?\\s*km</text>\\s*</distance>";
		Pattern pat = Pattern.compile( regex , Pattern.DOTALL );
		Matcher mat = pat.matcher( response );
		assertTrue( mat.find() );
	}

	/**
	 * Testea que existan datos de tiempos en respuesta XML
	 */
	@Test
	public void test_request_xml_hasDurationData() {
		String response = directionsClient.request( origin , destination , DirectionsClient.XML );
		String regex = "<duration>\\s*<value>\\d+</value>\\s*<text>\\d+\\s*min</text>\\s*</duration>";
		Pattern pat = Pattern.compile( regex , Pattern.DOTALL );
		Matcher mat = pat.matcher( response );
		assertTrue( mat.find() );
	}

	/**
	 * Testea que existan datos de start location, en respuesta XML
	 */
	@Test
	public void test_request_xml_hasStartLocation() {
		String response = directionsClient.request( origin , destination , DirectionsClient.XML );
		String regex = "<start_location>\\s*<lat>-?\\d+.\\d*</lat>\\s*<lng>-?\\d+.\\d*</lng>\\s*</start_location>";
		Pattern pat = Pattern.compile( regex , Pattern.DOTALL );
		Matcher mat = pat.matcher( response );
		assertTrue( mat.find() );
	}

	/**
	 * Testea que existan datos de end location, en respuesta XML
	 */
	@Test
	public void test_request_xml_hasEndLocation() {
		String response = directionsClient.request( origin , destination , DirectionsClient.XML );
		String regex = "<end_location>\\s*<lat>-?\\d+.\\d*</lat>\\s*<lng>-?\\d+.\\d*</lng>\\s*</end_location>";
		Pattern pat = Pattern.compile( regex , Pattern.DOTALL );
		Matcher mat = pat.matcher( response );
		assertTrue( mat.find() );
	}

	/**
	 * Testea que todos los modos de viaje devueltos sean DRIVING, en XML response.
	 */
	@Test
	public void test_request_xml_modeDriving() {
		String response = directionsClient.request( origin , destination , DirectionsClient.XML );
		Pattern pat = Pattern.compile( "\\Q<travel_mode>\\E([^<]+)<" , Pattern.DOTALL );
		Matcher mat = pat.matcher( response );
		while ( mat.find() )
			assertEquals( EXPECTED_MODE , mat.group( 1 ) );
	}
}
