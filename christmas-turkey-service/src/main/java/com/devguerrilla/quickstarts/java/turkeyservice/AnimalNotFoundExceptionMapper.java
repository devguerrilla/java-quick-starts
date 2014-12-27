package com.devguerrilla.quickstarts.java.turkeyservice;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Exception mapper for our <code>AnimalNotFoundException</code>.
 * 
 * Maps this case to an HTTP 404 (Not Found) error code and also creates a simple JSON
 * object embedding the error message from the exception.
 */
public class AnimalNotFoundExceptionMapper implements ExceptionMapper<AnimalNotFoundException> {

	/**
	 * Return a 404 error with the exception message included in a JSON response payload.
	 */
	@Override
	public Response toResponse(AnimalNotFoundException exception) {
		String errorMessage = null;
		try {
			errorMessage = new JSONObject().put("message", exception.getMessage()).toString();
		} catch(JSONException eJson) {
			errorMessage = "";
		}
		return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
	}
}
