package api.services.google;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import api.services.ServicesConstants;

/**
 * Cliente de Web Service Google Directions - Singleton Stateless 
 * @author Sebastian Pignataro
 */
public class DirectionsClient
{
	private static final Logger logger = LogManager.getLogger( DirectionsClient.class );

	private static DirectionsClient instance = null;
	public static String JSON = "json";
	public static String XML = "xml";

	public static DirectionsClient getInstance()
	{
		if ( instance == null )
			instance = new DirectionsClient();
		return instance;
	}

	public String request( String origin , String destination , String returnType )
	{
		try {
			String urlStr = buildRequestUrl( origin , destination , returnType );
			logger.debug( ServicesConstants.REST_CALL + urlStr );
			URL url = new URL( urlStr );
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			logger.debug( ServicesConstants.RESPONSE_CODE + conn.getResponseCode() );
			if ( conn.getResponseCode() != 200 ) {
				logger.error( conn.getResponseCode() + " " + conn.getResponseMessage() );
				return "";
			}

			BufferedReader rd = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
			StringBuilder sb = new StringBuilder();
			String line;
			while ( (line = rd.readLine()) != null ) {
				sb.append( line );
			}
			rd.close();
			conn.disconnect();
			return sb.toString();
		} catch ( UnsupportedEncodingException e ) {
			logger.error( e );
		} catch ( MalformedURLException e ) {
			logger.error( e );
		} catch ( IOException e ) {
			logger.error( e );
		}
		return "";
	}

	private String buildRequestUrl( String origin , String destination , String returnType ) throws UnsupportedEncodingException {
		return "https://maps.googleapis.com/maps/api/directions/" + returnType
				+ "?mode=driving&origin=" + URLEncoder.encode( origin , "UTF-8" )
				+ "&destination=" + URLEncoder.encode( destination , "UTF-8" )
				// TODO reemplazar por manejo con OAuth
				+ "&key=AIzaSyCndVSH1OqnoEADytK0ZEtGWh1bukFsxNU";
	}
}