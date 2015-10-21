package ar.org.aparcaba.simulator.value;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sensor {

	private int id;

	private boolean covered;

	public Sensor( int id ) {
		this.id = id;
	}

	public Sensor() {}

	public int getId() {
		return id;
	}

	public void setId( int id ) {
		this.id = id;
	}

	public boolean getCovered() {
		return covered;
	}

	public void setCovered( boolean covered ) {
		this.covered = covered;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		Sensor other = (Sensor) obj;
		if ( id != other.id )
			return false;
		return true;
	}

}
