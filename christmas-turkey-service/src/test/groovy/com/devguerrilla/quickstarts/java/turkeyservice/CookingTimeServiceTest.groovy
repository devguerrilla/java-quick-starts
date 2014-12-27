package com.devguerrilla.quickstarts.java.turkeyservice;

import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient

import org.springframework.context.support.ClassPathXmlApplicationContext

import spock.lang.Specification

/** "Unit" test for our REST API. 
 *  Arguably more of an integration test since it spins up an actual service, though in a more
 *  complex, real service we would use this to stub the implementation and exercise the API in 
 *  isolation. 
 */
class CookingTimeServiceTest extends Specification {
	
	/** Spring context */
	static def ctx = null
	
	/** URL for our embedded service - will be generated when we know what port we've started on */
	static def serviceUrl = null

	/** Initialise our stubbed service before we start testing
	 *  Once our context is initialised we find the real port it was started on and build a server URL to call 
	 */
	def setupSpec() {
		ctx = new ClassPathXmlApplicationContext("test-beans.xml")
		serviceUrl = "http://localhost:" + ctx.getBean("cookingTimeServer").server.destination.engine.connector.localPort + "/cookingtime/"
	}
	
	/** Close our stubbed service once testing is completed */
	def cleanupSpec() {
		ctx.close()
	}
	
	/** Successful request - invoke the API and check for a 200 response and valid JSON properties */
	def "Cooking instructions request for a known type of meat is successfully processed"() {
		given: "A type of meat the service knows how to cook"
			def myDinner = "turkey"
		  and: "A valid weight in kilograms"
		    def weight = 1.8
			
		when: "We request cooking instructions from the server"
			def response = new RESTClient(serviceUrl).get(path : myDinner + "/" + weight)
			
		then: "the server returns successfully"
			response.status == 200			
		 and: "the instructions are correct"
		 	response.data.cookingInstruction.temperatureToSet == 180
			response.data.cookingInstruction.timeToCook == 120
	}
	
	/** Failed request - supplying a request without a weight will yield a URL the service doesn't
	 * know how to map and therefore return an undecorated 404
	 */
	def "Cooking instructions request without a weight returns fails with an apropriate error"() {
		given: "A type of meat the service knows how to cook"
			def myDinner = "turkey"
			
		when: "We request cooking instructions from the server"
			def response = new RESTClient(serviceUrl).get(path : myDinner)
			
		then: "the request fails with a 404 (not found error)"
			def error = thrown(HttpResponseException.class)
			error.statusCode == 404
	}

	/** Failed request - supplying an unknown type of meat but an otherwise valid URL yields a 
	 * 404 thanks to our exception mapping and will also include a "helpful" error message.
	 */
	def "Cooking instructions request for an unknown type of meat fails with a meaningful error"() {
		given: "A type of meat the service does not know how to cook"
			def myDinner = "poodle"
		  and: "A valid weight in kilograms"
			def weight = 2.5
			
		when: "We request cooking instructions from the server"
			def client = new RESTClient(serviceUrl)
			client.handler.failure = { resp, data -> resp.setData(data); return resp }
			def response = client.get(path : myDinner + "/" + weight)
			
		then: "the server indicates the type of meat was not found"
			response.status == 404
		 and: "an explanatory message is supplied"
			response.data.message == "Sorry, don't know how to cook poodle"
	}

}
