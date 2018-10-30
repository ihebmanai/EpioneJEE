package tn.esprit.epione.resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.primefaces.push.Status.STATUS;

import tn.esprit.epione.interfaces.AppointmentIServices;
import tn.esprit.epione.persistance.Appointment;
import tn.esprit.epione.utilities.Secured;

@Path("appointments")
@RequestScoped
public class AppointmentResources  {

	@EJB
	AppointmentIServices appointmentIServices ; 
	// ajoute lannotation @autheticated user 

	
	List<Appointment> apps  = new ArrayList<Appointment>();
	
	@GET
	@Produces("application/json")
	public Response getAll () {
		 
		 return Response.status(Status.CREATED).entity(appointmentIServices.getAll()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response ajouterApp(Appointment a) throws AddressException, MessagingException {
		/*
		 * Appointment a=artsukiManager.findArtsukiById(authenticatedUser.getId());
		event.setPublisher(a);
		
		a.setPatient();
		*/
		
		if(appointmentIServices.addAppointment(a)) {
			return Response.status(Status.CREATED).entity("Appoinment created").build();
		}
		return Response.status(Status.NOT_ACCEPTABLE).entity("Failed to create this appoinment").build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateApp(Appointment ap ) {
	/*	Artsuki a=artsukiManager.findArtsukiById(authenticatedUser.getId());
		Event e = eventManager.getIdEvent(event.getId());

		if(a.getId()==e.getPublisher().getId()) {
	*/

		if(appointmentIServices.updateAppointment(ap))
		{
		return Response.status(Status.OK).entity("Appointment updated").build();
		}
		else
			
		return Response.status(Status.NOT_MODIFIED).entity("Failed to update Appointment").build();
	}
	
	@DELETE
	@Path("{appointments}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProject(@PathParam("appointments") int id) 
	{
		if(appointmentIServices.removeAppById(id)) {
		return Response.status(Status.OK).entity("le rendez vous a été supprimé").build();
		} else  
					return Response.status(Status.METHOD_NOT_ALLOWED).entity("Failed to delete").build();


	}
	
	@GET
	@Path("{app}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getApp(@PathParam(value="app") int id) {
		Appointment ap = appointmentIServices.consulterApp(id);
		return Response.status(Status.OK).entity(ap).build();
	}
	

	@GET
	@Path("/apps")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAppByDate(@QueryParam("date")String date) throws ParseException {
		
		List<Appointment> ap = appointmentIServices.SelectAppByDay(date);
		if(ap.size()==0){
			return Response.status(Status.NOT_FOUND).entity("No appointments for This date").build();
		}
		else 
			return Response.status(Status.OK).entity(appointmentIServices.SelectAppByDay(date)).build();

		}
	
	/*@GET
	@Path("/app")
	public Response getUsers(@Context UriInfo info) throws ParseException {
		String date = info.getQueryParameters().getFirst("date");
		String hour = info.getQueryParameters().getFirst("hour");
		Appointment ap = appointmentIServices.SelectAppByDateAndByHour(date, hour);
		if(ap==null){
			return Response.status(Status.NOT_FOUND).entity("No appointments for This date").build();
		}
		else 
			return Response.status(Status.OK).entity(appointmentIServices.SelectAppByDateAndByHour(date, hour)).build();

		}
		*/
	
	@GET
	@Path("/app")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUsers(
		@QueryParam("date") String date,
		@QueryParam("hour") String hour) throws ParseException {
		List<Appointment> ap = appointmentIServices.SelectAppByDateAndByHour(date, hour);
		if(ap.size()==0){
			return Response.status(Status.NOT_FOUND).entity("No appointments for This date").build();
		}
		else 
			return Response.status(Status.OK).entity(appointmentIServices.SelectAppByDateAndByHour(date, hour)).build();

	}
	
	@GET
	@Path("req/{app}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response myRequests(@PathParam(value="app") int id) {
		 appointmentIServices.MyRequests(id);
		return Response.status(Status.OK).entity("request canceled").build();
	}
	
	@GET
	@Path("mail/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response mailPatient(@PathParam(value="id") int id) throws AddressException, MessagingException {
		 appointmentIServices.mailingId(id);
		return Response.status(Status.GONE).entity("mail Sent").build();
	}
	

	
}
	
	
	
	
	
	
	
	

