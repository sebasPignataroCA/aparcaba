package ar.org.aparcaba.simulator.utils;

import java.util.Date;

public class DateUtil {

	public static boolean isWithinInterval( Date date , Date dateFrom , Date dateTo ) {
		return (dateFrom.before( date ) && dateTo.after( date ))
				|| dateFrom.equals( date )
				|| dateTo.equals( date );
	}

}
