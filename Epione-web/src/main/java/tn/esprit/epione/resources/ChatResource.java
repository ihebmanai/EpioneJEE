package tn.esprit.epione.resources;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.epione.interfaces.DiscussionIServicesLocal;
import tn.esprit.epione.persistance.Discussion;
import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Message;
import tn.esprit.epione.persistance.Patient;
@Path("chat")
@RequestScoped
public class ChatResource {

	@EJB
	DiscussionIServicesLocal cs;

	@POST
	@Path("{idDoctor}/{idPatient}")
	public Response addDiscussion(@PathParam("idDoctor") int idDoctor, @PathParam("idPatient") int idPatient) {
		Discussion c = new Discussion();
		c.setDoctor(new Doctor(idDoctor));
		c.setPatient(new Patient(idPatient));
		int id = cs.addDiscussion(c);
		if (id > -1)
			return Response.status(Response.Status.CREATED).entity(id).build();
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Discussion creation failed !!").build();
	}

	@POST
	@Path("{idDiscussion}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendMessage(@PathParam("idDiscussion") int idDiscussion, Message msg) {
		int id = cs.sendMsg(idDiscussion, msg);
		if (id > -1)
			return Response.status(Response.Status.CREATED).entity(id).build();
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Message failed").build();
	}
	
	@POST
	@Path("/seen/{idDiscussion}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response seenDiscussion(@PathParam("idDiscussion") int idDiscussion) {
		if (cs.seenDiscussion(idDiscussion))
			return Response.status(Response.Status.ACCEPTED).entity("Discussion marked seen with now date UTC").build();
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("NOt Valid discussion id !").build();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDiscussionById(@PathParam("id") int idDiscussion) {
		Discussion c = cs.getDiscussionById(idDiscussion);
		if (c == null)
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("failed to get discussion !").build();
		return Response.status(Response.Status.FOUND).entity(c).build();
	}

	@GET
	@Path("/messages/{idDiscussion}/{days}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessageLastDays(@PathParam("idDiscussion") int idDiscussion, @PathParam("days") int days) {
		List<Message> l = cs.getMessageLastDays(idDiscussion, days);
		if (l != null)
			return Response.status(Response.Status.ACCEPTED).entity(l).build();
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("get messages failed !!").build();
	}

	@GET
	@Path("{idDoctor}/{idPatient}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDiscussion2Users(@PathParam("idDoctor") int idDoctor, @PathParam("idPatient") int idPatient) {

		Discussion c = cs.getDiscussion2Users(idDoctor, idPatient);
		if (c != null)
			return Response.status(Response.Status.FOUND).entity(c).build();
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Get Discussion failed !!").build();
	}

	@GET
	@Path("{idDoctor}/{idPatient}/{days}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDiscussion2UsersLastDays(@PathParam("idDoctor") int idDoctor,
			@PathParam("idPatient") int idPatient, @PathParam("days") int days) {
		Discussion c = cs.getDiscussion2UsersLastDays(idDoctor, idPatient, days);
		if (c != null)
			return Response.status(Response.Status.FOUND).entity(c).build();
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Get Discussion failed !!").build();
	}

	@GET
	@Path("all/{idUser}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMyDiscussions(@PathParam("idUser") int idUser) {

		List<Discussion> c = cs.getDiscussions(idUser);
		if (c != null)
			return Response.status(Response.Status.FOUND).entity(c).build();
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Get Discussions failed !!").build();
	}

	@GET
	@Path("all/{idUser}/{days}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDiscussionsLastDays(@PathParam("idUser") int idUser, @PathParam("days") int days) {
		List<Discussion> c = cs.getDiscussionsLastDays(idUser, days);
		if (c != null)
			return Response.status(Response.Status.FOUND).entity(c).build();
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Get Discussions failed !!").build();
	}

	@DELETE
	@Path("{idDiscussion}/{idUser}")
	public Response deleteDiscussion(@PathParam("idDiscussion") int idDiscussion,
			@PathParam("idUser") int idUser) {
		if (cs.deleteDiscussion(idDiscussion, idUser))
			return Response.status(Response.Status.ACCEPTED).entity("Discussion deleted successfully").build();
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Discussion deletion failed !!").build();

	}

}
