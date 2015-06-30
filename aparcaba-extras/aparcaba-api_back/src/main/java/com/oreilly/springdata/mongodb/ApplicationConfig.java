package com.oreilly.springdata.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.WriteConcern;

@Configuration
@ComponentScan
@EnableMongoRepositories
class ApplicationConfig extends AbstractMongoConfiguration {

	@Autowired
	private List<Converter<?, ?>> converters;

	@Override
	protected String getDatabaseName() {
		return "e-store";
	}

	@Override
	public Mongo mongo() throws Exception {

		Mongo mongo = new Mongo();
		mongo.setWriteConcern(WriteConcern.SAFE);

		return mongo;
	}

	@Override
	public CustomConversions customConversions() {
		return new CustomConversions(converters);
	}
}
