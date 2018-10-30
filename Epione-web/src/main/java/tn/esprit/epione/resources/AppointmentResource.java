package tn.esprit.epione.resources;

import java.util.List;

import javax.ejb.EJB;
import javax.print.attribute.standard.RequestingUserName;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.xml.stream.xerces.util.STAXAttributesImpl;

import tn.esprit.epione.interfaces.AppointmentInterface;
import tn.esprit.epione.persistance.Appointment;

@Path("appointment")
public class AppointmentResource {
	@EJB
	AppointmentInterface appointmentInterface;

	@GET
	@Path("{idDoc}")
	@Produces("application/json")
	public Response GetAppointmentByDoc(@PathParam(value = "idDoc") int idDoc) {
		List<Appointment> appointment = appointmentInterface.GetAppointmentByDoc(idDoc);
		if (appointment != null)
			return Response.status(Status.CREATED).entity(appointment).build();
		else
			return Response.status(Status.NOT_FOUND).build();
	}
	@PUT
	@Consumes("application/json")
	public Response AcceptAppoint(@QueryParam("idAppa")int idApp) {
		try {
			appointmentInterface.AcceptAppointment(idApp);
			return 	 Response.status(Status.CREATED).build();

			
		} catch (Exception e) {
			return 	 Response.status(Status.NOT_MODIFIED).build();

		}}
		@PUT
		@Path("{idAppr}")
		@Consumes("application/json")
		public Response RefuseAppoint(@PathParam("idAppr")int idApp) {
			try {
				appointmentInterface.RefuseAppointment(idApp);
				return 	 Response.status(Status.CREATED).build();

				
			} catch (Exception e) {
				return 	 Response.status(Status.NOT_MODIFIED).build();

			}
		
	}
		@GET
		@Path("idDoc")
		@Produces("application/json")
		public Response AppointmentRequest(@QueryParam( "idDoc") int idDoc) {
			List<Appointment> appointment = appointmentInterface.GetAppointmentRequest(idDoc);
			if (appointment != null)
				return Response.status(Status.CREATED).entity(appointment).build();
			else
				return Response.status(Status.NOT_FOUND).build();
		}


@GET
@Path("idDocd")
@Produces("application/json")
public Response Dailyprogram(@QueryParam( "idDocd") int idDoc) {
	List<Appointment> appointment = appointmentInterface.DailyProgram(idDoc);
	if (appointment != null)
		return Response.status(Status.CREATED).entity(appointment).build();
	else
		return Response.status(Status.NOT_FOUND).build();
}

}
