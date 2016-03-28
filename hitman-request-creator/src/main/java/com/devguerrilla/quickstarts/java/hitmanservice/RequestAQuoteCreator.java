package com.devguerrilla.quickstarts.java.hitmanservice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.devguerrilla.services.hitman_service.RequestAQuote;

/**
 * Creates valid SOAP requests based on JAXB request objects defined in the WSDL
 */
public class RequestAQuoteCreator {

	/** JAXB Context for marshalling the schema objects */
	private JAXBContext JAXB_CONTEXT = null;
	
	/** Associated XML Schema from the WSDL - used to enforce schema restrictions before rendering requests */
	private Schema SCHEMA = null;
	
	/**
	 * Default, no argument constructor. Creates and caches both the required JAXB context
	 * and schema instance used for validation
	 */
	public RequestAQuoteCreator() throws JAXBException, SAXException {
		JAXB_CONTEXT = JAXBContext.newInstance(RequestAQuote.class);
		SCHEMA = SchemaFactory.
					newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).
					newSchema(new StreamSource(this.getClass().getResourceAsStream("/hitmanService.xsd")));
	}

	/**
	 * Renders a Quote Request as a SOAP request and returns it as a string. Validates 
	 * that the the resulting SOAP message passes any type restrictions in the XML Schema.
	 * 
	 * @param quoteRequest JAXB model object for the request payload in the SOAP message
	 * 
	 * @return Properly formed and validated SOAP message string
	 */
	public String makeRequestAQuote(RequestAQuote quoteRequest) 
			throws SOAPException, JAXBException, IOException {
		
		// Construct a SOAP message wrapper
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPBody messageBody = soapMessage.getSOAPBody();
		
		// Marshall the quoteRequest into the SOAP Body
		Marshaller marshaller = JAXB_CONTEXT.createMarshaller();
		marshaller.setSchema(SCHEMA);
		marshaller.marshal(quoteRequest, messageBody);
		soapMessage.saveChanges();
		
		// Write to a string and return
		ByteArrayOutputStream soapOutputStream = new ByteArrayOutputStream(); 
		soapMessage.writeTo(soapOutputStream);
		return soapOutputStream.toString();
	}
}
