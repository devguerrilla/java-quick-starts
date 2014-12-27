package com.devguerrilla.quickstarts.java.turkeyservice;


/** For our simple service, each type of meat we know how to cook is modelled as 
 * an enumeration which contains the associated cooking details along with helper
 * methods to get the calculated cooking time and temperature
 */
public enum CookingTime {
	
	/** Cooking details for our festive turkey */
	TURKEY(30, 50, 180);

	/** Fixed component of our cooking time */
	private Integer baseTime = null;
	
	/** Per kilogram component of our cooking time */
	private Integer perKiloTime = null;
	
	/** Temperature in celsius to cook at */
	private Integer temperatureInCelsius = null; 
	
	/**
	 * Private, fully describing constructor 
	 * @param baseTime fixed component of the cooking time
	 * @param perKiloTime per-kilo component of the cooking time
	 * @param temperatureInCelsius oven temperature
	 */
	private CookingTime(Integer baseTime, Integer perKiloTime, Integer temperatureInCelsius) {
		this.baseTime = baseTime;
		this.perKiloTime = perKiloTime;
		this.temperatureInCelsius = temperatureInCelsius;
	}
	
	/**
	 * Calculates the required cooking time for a given weight
	 * @param weightInKilos weight to cook
	 * @return calculated time to cook
	 */
	public Integer getTotalTime(Float weightInKilos) {
		return Integer.valueOf((int)((weightInKilos * perKiloTime) + baseTime));
	}
	
	/**
	 * Recommended cooking temperature for this type of meat
	 * @return oven temperature
	 */
	public Integer getTemperatureInCelsius() {
		return temperatureInCelsius;
	}
}
