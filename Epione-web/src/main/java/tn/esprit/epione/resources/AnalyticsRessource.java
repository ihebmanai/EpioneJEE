package tn.esprit.epione.resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import tn.esprit.epione.interfaces.AnalyticsInterface;
import tn.esprit.epione.persistance.Appointment;
import tn.esprit.epione.persistance.Availibility;

@Path("Analytics")
public class AnalyticsRessource {

	@EJB
	AnalyticsInterface analytics;

	@GET
	public Response getNumberOfPatientsTreated() {
		long nbr = analytics.getAllPatientsTreated();
		return Response.status(Response.Status.OK).entity(nbr).build();
	}

	@GET
	@Path("/date/{dateone}/{datetwo}")
	public Response getNumberOfPatientsTreatedByDate(@javax.ws.rs.PathParam("dateone") String dateOne,
			@javax.ws.rs.PathParam("datetwo") String dateTwo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateOneParsed;
		try {
			dateOneParsed = sdf.parse(dateOne);
			Date dateTwoParsed = sdf.parse(dateTwo);
			long nbr = analytics.getAllPatientsTreatedByDate(dateOneParsed, dateTwoParsed);
			return Response.status(Response.Status.OK).entity(nbr).build();

		} catch (ParseException e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

	}

	@GET
	@Path("/{idDoctor}")
	public Response getNumberOfPatientsTreatedByDoctor(@javax.ws.rs.PathParam("idDoctor") int idDoctor) {
		long nbr = analytics.getAllPatientsTreatedByDoctor(idDoctor);
		return Response.status(Response.Status.OK).entity(nbr).build();

	}

	@GET
	@Path("/canceled")
	public Response getNumberOfCanceledAppointements() {
		long nbr = analytics.getCanceledRequest();
		return Response.status(Response.Status.OK).entity(nbr).build();
	}

	@GET
	@Path("/accepted")
	public Response getNumberOfAcceptedAppointements() {
		long nbr = analytics.getAcceptedRequest();
		return Response.status(Response.Status.OK).entity(nbr).build();
	}

	@GET
	@Path("/availability/{idDoctor}/{date}")
	public Response getavailability(@PathParam("idDoctor") int idDoctor, @PathParam("date") String date)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateParsed = sdf.parse(date);

		List<Availibility> nbravailabilities = analytics.GetAllAvabyDoc(idDoctor);
		List<Appointment> nbrAppointements = analytics.GetAppointmentByDoc(idDoctor, dateParsed);
		long duration = 0;
		for (Appointment a : nbrAppointements) {
			DateFormat formatter = new SimpleDateFormat("HH:mm");
			Date startH = formatter.parse(a.getStart_hour());
			Date endH = formatter.parse(a.getEndHour());

			duration = duration + (endH.getTime() - startH.getTime());
		}

		long allDuration = nbravailabilities.get(nbravailabilities.size() - 1).getStartDate().getTime()
				- nbravailabilities.get(0).getStartDate().getTime();

		long res = allDuration - duration;

		return Response.status(Response.Status.OK).entity(res).build();
	}
	
	@GET
	@Path("/availability/{idDoctor}/{dateone}/{datetwo}")
	public Response getavailabilityBymonth(@PathParam("idDoctor") int idDoctor, @PathParam("dateone") String dateOne, @PathParam("datetwo") String dateTwo)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateOneParsed = sdf.parse(dateOne);
		Date dateTwoParsed = sdf.parse(dateTwo);

		List<Availibility> nbravailabilities = analytics.GetAllAvabyDoc(idDoctor);
		List<Appointment> nbrAppointements = analytics.GetAppointmentByDocandMonth(idDoctor, dateOneParsed,dateTwoParsed);
		long duration = 0;
		for (Appointment a : nbrAppointements) {
			DateFormat formatter = new SimpleDateFormat("HH:mm");
			Date startH = formatter.parse(a.getStart_hour());
			Date endH = formatter.parse(a.getEndHour());

			duration = duration + (endH.getTime() - startH.getTime());
		}

		long allDuration = nbravailabilities.get(nbravailabilities.size() - 1).getStartDate().getTime()
				- nbravailabilities.get(0).getStartDate().getTime();

		
		long res = allDuration - duration;

		return Response.status(Response.Status.OK).entity(res).build();
	}

}
