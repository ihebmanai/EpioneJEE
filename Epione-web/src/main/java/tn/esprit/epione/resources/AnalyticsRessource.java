package tn.esprit.epione.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import tn.esprit.epione.interfaces.AnalyticsInterface;

@Path("Analytics")
public class AnalyticsRessource {
	
	@EJB
	AnalyticsInterface analytics;
	
	@GET
	public Response getNumberOfPatientsTreated()
	{
		long nbr=analytics.getAllPatientsTreated();
		return Response.status(Response.Status.OK).entity(nbr).build();
	}
	
	
	@GET
	@Path("/date/{dateone}/{datetwo}")
	public Response getNumberOfPatientsTreatedByDate(@javax.ws.rs.PathParam("dateone")String dateOne,@javax.ws.rs.PathParam("datetwo")String dateTwo)
	{
		System.out.println("dkhaltdate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//surround below line with try catch block as below code throws checked exception
		Date dateOneParsed;
		try {
			dateOneParsed = sdf.parse(dateOne);
			Date dateTwoParsed = sdf.parse(dateTwo);
			long nbr=analytics.getAllPatientsTreatedByDate(dateOneParsed,dateTwoParsed);
			return Response.status(Response.Status.OK).entity(nbr).build();

		} catch (ParseException e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}


	}
	
	@GET
	@Path("/{idDoctor}")
	public Response getNumberOfPatientsTreatedByDoctor(@javax.ws.rs.PathParam("idDoctor")int idDoctor)
	{
		long nbr=analytics.getAllPatientsTreatedByDoctor(idDoctor);
		return Response.status(Response.Status.OK).entity(nbr).build();

	}
	

	
	@GET
	@Path("/canceled")
	public Response getNumberOfCanceledAppointements()
	{
		long nbr=analytics.getCanceledRequest();
		return Response.status(Response.Status.OK).entity(nbr).build();
	}
	
	@GET
	@Path("/accepted")
	public Response getNumberOfAcceptedAppointements()
	{
		long nbr=analytics.getAcceptedRequest();
		return Response.status(Response.Status.OK).entity(nbr).build();
	}


}
