package com.devguerrilla.quickstarts.java.turkeyservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * JAX-RS service interface.
 * 
 * Defines our service methods annotated with the JAX-RS mappings
 * 
 * Endpoints all sit under the root Path of /cookingtime 
 */
@Path("/cookingtime")
public interface CookingTimeService {

	/**
	 * Get cooking instructions for the specified type of meat and weight passed
	 * in as URL parameters, e.g. cookingtime/turkey/1.24
	 *  
	 * @param fleshType type of meat to cook 
	 * @param fleshWeight weight of meat to cook
	 * 
	 * @return instructions containing the oven temperature and cooking time
	 * 
	 * @throws AnimalNotFoundException if the type of meat is one we don't know how to cook
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{fleshType}/{fleshWeight}")
	public CookingInstruction getCookingTime(@PathParam("fleshType") String fleshType,
								             @PathParam("fleshWeight") Float fleshWeight) 
					throws AnimalNotFoundException;
}
