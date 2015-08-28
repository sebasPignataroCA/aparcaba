package ar.org.aparcaba.simulator;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import ar.org.aparcaba.simulator.value.Sensor;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class AparcabaApiClient {

	private static AparcabaApiClient instance;

	private Client service = null;

	public static AparcabaApiClient getInstance() {
		if ( instance == null ) {
			instance = new AparcabaApiClient();
		}
		return instance;
	}

	private AparcabaApiClient() {}

	public void release( Sensor sensor ) {
		callAPI( sensor , true );
		return;
	}

	public void take( Sensor sensor ) {
		callAPI( sensor , false );
		return;
	};

	private void callAPI( Sensor sensor , Boolean covered ) {
		WebResource webResource = getService( "http://localhost:5050/api/sensors/" + sensor.getId() + "?covered=" + covered );

		String response = webResource.put( String.class );
		System.out.println( response );
	}

	public WebResource getService( String url ) {
		if ( this.service == null ) {
			ClientConfig config = new DefaultClientConfig();
			config.getClasses().add( JacksonJsonProvider.class );

			this.service = Client.create( config );
		}

		return this.service.resource( url );
	}

}
