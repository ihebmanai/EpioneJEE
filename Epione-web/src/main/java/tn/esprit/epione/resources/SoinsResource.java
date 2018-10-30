package tn.esprit.epione.resources;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import tn.esprit.epione.interfaces.SoinsInterface;
import tn.esprit.epione.persistance.Soins;

@Path("soins")
public class SoinsResource {
	@EJB
	SoinsInterface soinsService;
	
	@POST
	@Consumes("application/json")
	public Response AddSoins(Soins s) {
		try {
			soinsService.AddSoins(s);
			return 	 Response.status(Status.CREATED).build();

			
		} catch (Exception e) {
			return 	 Response.status(Status.NOT_MODIFIED).build();

		}
		
		}
	
	@DELETE
	@Path("{id}")
	@Consumes("application/json")
	public Response deleteSoisn(@PathParam("id")int id ) {
		try {
			soinsService.DeleteSoins(id);
			return 	 Response.status(Status.OK).build();

			
		} catch (Exception e) {
			return 	 Response.status(Status.NOT_MODIFIED).build();

		}
		
	}



}
