package ar.org.aparcaba.simulator.utils;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import ar.org.aparcaba.simulator.value.Comune;
import ar.org.aparcaba.simulator.value.Configuration;

public class ConfigurationManager {

	private static ConfigurationManager instance;

	private Configuration configuration;

	public static ConfigurationManager getInstance() {
		if ( instance == null ) {
			instance = new ConfigurationManager();
		}
		return instance;
	}

	private ConfigurationManager() {}

	public long getCycleInterval() {
		return getConfiguration().getCycleInterval();
	}

	public List<Comune> getComunes() {
		return getConfiguration().getComunes();
	}

	public String getApiUri() {
		return getConfiguration().getApiUri();
	}

	public Configuration getConfiguration() {
		if ( configuration == null ) {
			configuration = getConfigurationsFromDB();
		}
		return configuration;
	}

	@SuppressWarnings("resource")
	private Configuration getConfigurationsFromDB() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext( SpringMongoConfig.class );
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean( "mongoTemplate" );

		List<Configuration> config = mongoOperation.findAll( Configuration.class );

		if ( CollectionUtils.isEmpty( config ) ) {
			throw new RuntimeException( "Configuration not found in mongodb" );
		}
		return config.get( 0 );
	}

	public void setConfiguration( Configuration configuration ) {
		this.configuration = configuration;
	}

}
