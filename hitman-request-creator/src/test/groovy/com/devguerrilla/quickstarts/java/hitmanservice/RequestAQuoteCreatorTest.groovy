package com.devguerrilla.quickstarts.java.hitmanservice;

import javax.xml.bind.JAXBContext
import javax.xml.bind.MarshalException
import javax.xml.soap.MessageFactory
import javax.xml.soap.SOAPMessage

import org.apache.commons.io.IOUtils
import org.unitils.reflectionassert.ReflectionAssert;

import spock.lang.Specification

import com.devguerrilla.services.hitman_service.HitDetails
import com.devguerrilla.services.hitman_service.HitType
import com.devguerrilla.services.hitman_service.RequestAQuote

/** 
 * Tests the SAAJ based static RequestAQuote request creator with data that
 * satisfies and violates the schema rules in the associated WSDL. 
 */
class RequestAQuoteCreatorTest extends Specification {
	
	/** Class under test */
	def RequestAQuoteCreator cut = new RequestAQuoteCreator()

	/** Successful request case */
	def "A valid SOAP message is created for a request that satisfies the schema"() {
		given: "A valid request for a hitman"
			def hitRequest = new RequestAQuote(hitDetails: 
				new HitDetails(victimName: "Technical Architect",
							   victimHeight: 160,
							   victimWeight: 80,
							   hitType: HitType.APPARENT_AUTOEROTIC_ASPHYXIATION_IN_WOMENS_UNDERWEAR))
		  
		when: "We generate a SOAP request for that object"
			def soapRequest = cut.makeRequestAQuote(hitRequest)
		  
		then: "A valid SOAP request is returned"
			def SOAPMessage parsedSoapRequest = MessageFactory.
					newInstance().createMessage(null, IOUtils.toInputStream(soapRequest))
		 and: "it contains a valid request object"
		 	def RequestAQuote parsedHitRequest =
			 	JAXBContext.newInstance(RequestAQuote).createUnmarshaller().
				 	unmarshal(parsedSoapRequest.SOAPBody.firstChild)
		 and: "its properties match those in the original request"
		 	ReflectionAssert.assertReflectionEquals(hitRequest, parsedHitRequest)
	}

	/** Failure case - request does not meet schema constraints */
	def "An exception is thrown for a request which does not satisfy the schema"() {
		given: "An invalid request for a hitman"
			def hitRequest = new RequestAQuote(hitDetails:
				new HitDetails(victimName: "Technical Architect",
							   victimHeight: 200,
							   victimWeight: 400,
							   hitType: HitType.DROWNING))
		  
		when: "We attempt to generate a SOAP request for that object"
			cut.makeRequestAQuote(hitRequest)
		  
		then: "An exception is thrown"
			def e = thrown(MarshalException.class)
			e.printStackTrace()
		 and: "the message identifies the restriction that was not satisfied"
			e.getCause().getMessage() ==~ /(.*)cvc-maxInclusive(.*)/
	}
}
