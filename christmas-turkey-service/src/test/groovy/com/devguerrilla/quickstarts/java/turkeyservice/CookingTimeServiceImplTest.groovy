package com.devguerrilla.quickstarts.java.turkeyservice;

import spock.lang.Specification


/** Unit test for our service implementation */
class CookingTimeServiceImplTest extends Specification {
	
	/** Instance of our class under test */
	def cut = new CookingTimeServiceImpl()

	/** Successful request case */
	def "Successfully gets cooking time for known animal and weight"() {
		given: "A type of meat we know how to cook"
		   	def fleshType = CookingTime.TURKEY.toString()
		  and: "a valid weight in kilograms"
		   	def fleshWeight = 1.25
		  
		when: "We calculate our cooking time"
		   	def instructions = cut.getCookingTime(fleshType, fleshWeight)
		  
		then: "The service returns the correct time of (1.25 * 50mins) + 30mins"
		   	instructions.timeToCook == 92
		  and: "the service returns the correct temperature of 180C"
		    instructions.temperatureToSet == 180 
	}
	
	/** Failed request case - an unknown type of meat is supplied */
	def "Fails to get cooking time when we don't know the type of meat"() {
		given: "An animal we don't know how to cook"
		  	def fleshType = "Koala bear"
		 and: "a valid weight in kilograms"
		   	def fleshWeight = 2.25
			   
		when: "We try to calculate our cooking time"
			def instructions = cut.getCookingTime(fleshType, fleshWeight)
			
		then: "The service throws an appropriate exception"
			thrown(AnimalNotFoundException.class) 
	}
}
