package ar.org.aparcaba.simulator;

import ar.org.aparcaba.simulator.utils.ConfigurationManager;

public class Simulador
{

	private static CycleManager cycleManager = new CycleManager();

	public static void main( String[] args )
	{
		ConfigurationManager config = ConfigurationManager.getInstance();

		while ( true ) {
			try {
				cycleManager.execute();
				Thread.sleep( config.getCycleInterval() );
			} catch ( Throwable e ) {
				e.printStackTrace();
			}
		}
	}

}
