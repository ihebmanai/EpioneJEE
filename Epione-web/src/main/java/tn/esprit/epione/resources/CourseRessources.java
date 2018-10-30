package tn.esprit.epione.resources;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.StringUtils;

import tn.esprit.epione.interfaces.ICourseServices;
import tn.esprit.epione.persistance.*;


@Path("ressources")
public class CourseRessources {

	@EJB
	ICourseServices courseService;
	
	

	@POST
	@Path("addreport")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addReport(Report r){
		if(r!=null){
			courseService.addReport(r);
			return Response.status(Response.Status.OK).entity("createed").build();
		}else
			return Response.status(Response.Status.NOT_FOUND).entity("empty").build();	
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addTreatments(Treatments t){
		if(t!=null){
			courseService.addTreatement(t);
			return Response.status(Response.Status.OK).entity("createed").build();
		}else
			return Response.status(Response.Status.NOT_FOUND).entity("empty").build();	
	}
	@POST
	@Path("/{appId}/{type}")
	public Response addNotif(@PathParam("appId")int appId,@PathParam("type")String appointmentType){
		if(appId!=0 && appointmentType!=null){
			courseService.setNotification(appId, appointmentType);
			return Response.status(Response.Status.OK).entity("request Created").build();
		}else
			return Response.status(Response.Status.NO_CONTENT).entity("invalid request").build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMethods(@QueryParam("idR")int reportId){
		if(reportId==0)	{
			ArrayList<Notification>notifList=courseService.getNotification();
			if(notifList!=null)
				return Response.status(Response.Status.OK).entity(notifList).build();
			else
				return Response.status(Response.Status.NOT_FOUND).entity("empty").build();
			
		}else if(reportId!=0){
			List<Treatments>TreatmentsByReport=courseService.getAllTreatmentsByReport(reportId);
			if(!TreatmentsByReport.isEmpty())
				return Response.status(Response.Status.OK).entity(TreatmentsByReport).build();
			else
				return Response.status(Response.Status.NOT_FOUND).entity("empty").build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("empty").build();
		
	}
	
	@PUT
	@Path("/{id}")
	public Response setNotifSeen(@PathParam("id")int id){
		if(id!=0){
			courseService.setNotificationSeen(id);
			return Response.status(Response.Status.OK).entity("notification sets seen").build();
		}else
			return Response.status(Response.Status.NOT_FOUND).entity("no id").build();
	}
	// get reports
	
	@GET
	@Path("/{idP}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReportsPatient(@PathParam("idP")int patientId){
		List<Report>reportListForPatient=courseService.getAllReportByPatient(patientId);
		if(reportListForPatient!=null)
			return Response.status(Response.Status.OK).entity(reportListForPatient).build();
		else
			return Response.status(Response.Status.NOT_FOUND).entity("empty").build();
	}
	
	/*@GET
	@Path("/{idP}/{idD}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReportsPatientAndDoctor(@PathParam("idP")int patientId,@PathParam("idD")int doctorId){
		List<Report>reportListForPatient=courseService.getAllReportByDoctor(patientId, doctorId);
		if(reportListForPatient!=null)
			return Response.status(Response.Status.OK).entity(reportListForPatient).build();
		else
			return Response.status(Response.Status.NOT_FOUND).entity("empty").build();
	}*/
	@GET
	@Path("/{idP}/{desease}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReportsPatientAndDesease(@PathParam("idP")int patientId,@PathParam("desease")String desease){
		try{
			int idDoctor =Integer.parseInt(desease);
			List<Report>reportListForPatient=courseService.getAllReportByDoctor(patientId, idDoctor);
			if(reportListForPatient!=null)
				return Response.status(Response.Status.OK).entity(reportListForPatient).build();
			else
				return Response.status(Response.Status.NOT_FOUND).entity("empty").build();
		}catch (NumberFormatException e) {
			List<Report>reportListForPatient=courseService.getAllReportByDesease(patientId, desease);
			if(reportListForPatient!=null)
				return Response.status(Response.Status.OK).entity(reportListForPatient).build();
			else
				return Response.status(Response.Status.NOT_FOUND).entity("empty").build();
		}
		
	}
	@GET
	@Path("/{idP}/{desease}/{idD}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReportsPatientAndDesease(@PathParam("idP")int patientId,@PathParam("desease")String desease,@PathParam("idD") int doctorId){
		List<Report>reportListForPatient=courseService.getAllReportByDeseaseAndDoctor(patientId, desease, doctorId);
		if(reportListForPatient!=null)
			return Response.status(Response.Status.OK).entity(reportListForPatient).build();
		else
			return Response.status(Response.Status.NOT_FOUND).entity("empty").build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTreatments(Treatments t){
		if(t!=null){
			courseService.updateTreatment(t);
			return Response.status(Response.Status.OK).entity("treatment updating").build();
		}else
			return Response.status(Response.Status.NOT_FOUND).entity("no id").build();
	}
	
	@PUT
	@Path("vadidateTreatment")
	public Response validateTreatment(@QueryParam("id")int id){
		if(id!=0){
			courseService.validateTreatement(id);
			return Response.status(Response.Status.OK).entity("treatment is valide now").build();
		
		}else
			return Response.status(Response.Status.NOT_FOUND).entity("no id").build();
	}
	/*@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response validateTreatment(Treatments t){
		if(t!=null){
			courseService.updateTreatment(t);
			return Response.status(Response.Status.OK).entity("treatment updating").build();
		}else
			return Response.status(Response.Status.NOT_FOUND).entity("no valid treatments").build();
	}*/
	
	
}
