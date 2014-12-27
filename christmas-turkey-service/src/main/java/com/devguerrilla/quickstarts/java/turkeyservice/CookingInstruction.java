package com.devguerrilla.quickstarts.java.turkeyservice;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Simple value object for our service's response
 * 
 * For CXF's JAXB -> JSON process this needs to be annotated as an XML root element
 * and must adhere to JAXB class rules. 
 */
@XmlRootElement
public class CookingInstruction {

	/** Calculated cooking time */
	private Integer timeToCook = null;
	
	/** Associated cooking temperature */
	private Integer temperatureToSet = null;
	
	/** Default, no-arg constructor to satisfy JAXB */
	public CookingInstruction() {
	}
	
	/**
	 * Fully describing constructor 
	 * @param timeToCook cooking time
	 * @param temperatureToSet cooking temperature
	 */
	public CookingInstruction(Integer timeToCook, Integer temperatureToSet) {
		this.timeToCook = timeToCook;
		this.temperatureToSet = temperatureToSet;
	}

	/** Getter for the cooking time */
	public Integer getTimeToCook() {
		return timeToCook;
	}
	
	/** Getter for the cooking temperature */
	public Integer getTemperatureToSet() {
		return temperatureToSet;
	}

	/** Setter for the cooking time */
	public void setTimeToCook(Integer timeToCook) {
		this.timeToCook = timeToCook;
	}
	
	/** Setter for the cooking temperature */
	public void setTemperatureToSet(Integer temperatureToSet) {
		this.temperatureToSet = temperatureToSet;
	}
}
