package tn.esprit.epione.resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import tn.esprit.epione.interfaces.AvailibilityInterface;
import tn.esprit.epione.persistance.Appointment;
import tn.esprit.epione.persistance.Availibility;

@Path("availibility")
public class AvailibilityResource {
	@EJB
	AvailibilityInterface availibilityService;
	
	@POST
	@Path("/avai")
	@Consumes("application/json")
	@Produces("application/octet-stream")

	public Response addAvailly(@QueryParam("idDoc")int idDoc ,@QueryParam("startDate")String startDate ,@QueryParam("endDate")String endDate) {
try {
	System.out.println(startDate);
	System.out.println(endDate);
			availibilityService.addAvaillibility((Date)(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	                .parse(startDate)),(Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	                .parse(endDate), idDoc);

			return 	 Response.status(Status.CREATED).build();
			
		} catch (Exception e) {
			
			return 	 Response.status(Status.NOT_MODIFIED).entity(e.getMessage()).build();

		}
		
	}
	@DELETE
	@Path("{idAv}")
	@Consumes
	public Response DeleteAva(@PathParam(value="idAv")int idAv) {
		try {

			availibilityService.DeleteAva(idAv);

			return 	 Response.status(Status.OK).build();

			
		} catch (Exception e) {
			return 	 Response.status(Status.NOT_MODIFIED).build();

		}
		
	}
	
	@DELETE
	@Path("delete")
	@Consumes
	public Response DeleteAvabyDoc( @QueryParam("idDoc")int idDoc,@QueryParam("startDate")String StartDate,@QueryParam("endDate")String endDate) {
		try {

			availibilityService.DeleteAvaDoc(idDoc, StartDate,endDate);

			return 	 Response.status(Status.OK).build();

			
		} catch (Exception e) {
			return 	 Response.status(Status.NOT_MODIFIED).build();

		}
	
	}
	@GET
	@Consumes("application/json")
	@Produces("application/json")

	public Response GetAvaByDay(@QueryParam("id")int idDoc ,@QueryParam("startDate")String startdate,@QueryParam("endDate")String endDate) {
		List<Availibility> avails = availibilityService.GetAllAvabyByDay(idDoc, startdate);
		if (avails != null)
			return Response.status(Status.CREATED).entity(avails).build();
		else
			return Response.status(Status.NOT_FOUND).build();
	}
	@GET
	@Path("all")
	@Consumes("application/json")
	@Produces("application/json")

	public Response GetallAvai(@QueryParam("id")int idDoc) {
		List<Availibility> avails = availibilityService.GetAllAvaby(idDoc);
		if (avails != null)
			return Response.status(Status.CREATED).entity(avails).build();
		else
			return Response.status(Status.NOT_FOUND).build();
	}

	
	
	
	
	
	
	public static Date getDateNowUTC(String date) {
		Date nowUTC = new Date();
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			nowUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return nowUTC;
	}


}
