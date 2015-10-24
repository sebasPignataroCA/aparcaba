package ar.org.aparcaba.simulator.value;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "configuration")
public class Configuration {

	@Id
	private String id;

	private int cycleInterval;

	private String apiUri;

	private List<Comune> comunes = new ArrayList<Comune>();

	public int getCycleInterval() {
		return cycleInterval;
	}

	public void setCycleInterval( int cycleInterval ) {
		this.cycleInterval = cycleInterval;
	}

	public List<Comune> getComunes() {
		return comunes;
	}

	public void setComunes( List<Comune> comunes ) {
		this.comunes = comunes;
	}

	public String getApiUri() {
		return apiUri;
	}

	public void setApiUri( String apiUri ) {
		this.apiUri = apiUri;
	}

}
