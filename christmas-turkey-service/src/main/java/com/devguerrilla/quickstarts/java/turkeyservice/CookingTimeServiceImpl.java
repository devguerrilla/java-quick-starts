package com.devguerrilla.quickstarts.java.turkeyservice;

/**
 * Simple implementation of our cooking time service
 */
public class CookingTimeServiceImpl implements CookingTimeService {

	/**
	 * Calculate cooking instructions and return them, throwing an exception if we 
	 * don't know how to cook the requested type of meat.
	 */
	@Override
	public CookingInstruction getCookingTime(String fleshType, Float fleshWeight)
			throws AnimalNotFoundException {
		try {
			CookingTime cookingTime = CookingTime.valueOf(fleshType.toUpperCase());
			return new CookingInstruction(cookingTime.getTotalTime(fleshWeight), cookingTime.getTemperatureInCelsius());
		} catch(IllegalArgumentException eUnknownAnimal) {
			throw new AnimalNotFoundException("Sorry, don't know how to cook " + fleshType);
		}
	}
}
