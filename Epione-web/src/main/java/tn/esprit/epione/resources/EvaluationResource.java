package tn.esprit.epione.resources;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import tn.esprit.epione.interfaces.EvaluationServiceLocal;
import tn.esprit.epione.persistance.Evaluation;

@Path("evaluation")
@RequestScoped
public class EvaluationResource {
	@Context
	private UriInfo uriInfo;

	@Context
	SecurityContext securityContext;

	@EJB
	EvaluationServiceLocal es;
	@POST
	@Path("addEvaluation")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addEvaluation(Evaluation evaluation) {
		int id = es.addEvalutation(evaluation);
		return Response.status(Response.Status.CREATED).entity(id).build();
	}
	@POST
	@Path("editEvaluation")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editEvaluation(Evaluation evaluation) {
		int id = es.editEvalutation(evaluation);
		return Response.status(Response.Status.OK).entity(id).build();
	}
	
	@DELETE
	@Path("deleteEvaluation")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteEvaluation(Evaluation evaluation) {
		int id = es.deleteEvalutation(evaluation);
		return Response.status(Response.Status.OK).entity(id).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAverage(@PathParam("id") int idUser) {
		
		return Response.status(Response.Status.FOUND).entity(es.AverageRating(idUser)).build();
	}
	@GET
	@Path("get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEvaluation(@PathParam("id") int idEvaluation) {
		
		return Response.status(Response.Status.FOUND).entity(es.getEvalutation(idEvaluation)).build();
	}
}
