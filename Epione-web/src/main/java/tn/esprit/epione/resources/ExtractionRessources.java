package tn.esprit.epione.resources;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import tn.esprit.epione.interfaces.ExtractionInt;
import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Extract;

@Path("exe")
@RequestScoped
public class ExtractionRessources {
	@EJB
	ExtractionInt extract;

	@GET
	@Path("/speciality")
	@Produces(MediaType.APPLICATION_JSON)
	public Response Searchbyspeciality(@QueryParam(value="specialite")String specialite,@QueryParam(value="pagenumber")int pagenumber) throws IOException 
	{
ArrayList<Extract> test= extract.SearchBySpeciality(specialite, pagenumber);
	//	extract.test(specialite, pagenumber);
		return Response.status(Status.OK).entity(test).build();
}
	
	@GET
	@Path("/place")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchbyplace(@QueryParam(value="place")String place,@QueryParam(value="pagenumber")int pagenumber) throws IOException 
	{
ArrayList<Extract> test= extract.SearchByPlace(place, pagenumber);
	//	extract.test(specialite, pagenumber);
		return Response.status(Status.OK).entity(test).build();
}

	@GET
	@Path("/SpecialityAndPlace")
	@Produces(MediaType.APPLICATION_JSON)
	public Response SearchByspecialtyandplace(@QueryParam(value="specialite")String specialite,@QueryParam(value="place")String place,@QueryParam(value="pagenumber")int pagenumber) throws IOException 
	{
ArrayList<Extract> test= extract.SearchBySpecialityandPlace(specialite, place, pagenumber);
	//	extract.test(specialite, pagenumber);
		return Response.status(Status.OK).entity(test).build();
}
	
	
	@GET
	@Path("/FindInDoctolib")
	@Produces(MediaType.APPLICATION_JSON)
	public Response FindDoctor(@QueryParam(value="nom")String nom,@QueryParam(value="prenom")String prenom,@QueryParam(value="specialite")String specialite) throws IOException
	{
		//Extract ex=extract.searchexistingdoctor(nom, prenom, specialite);
		Extract u=extract.searchexistingdoctor(nom, prenom, specialite);
		if(u==null)
		{
			return Response.status(Status.OK).entity("not found").build();
		}
		return Response.status(Status.OK).entity(u).build();
		
	}
	@POST
	@Path("/AddFromDoctolib")
	@Produces(MediaType.APPLICATION_JSON)
	public Response AddDoctor(@QueryParam(value="nom")String nom,@QueryParam(value="prenom")String prenom,@QueryParam(value="specialite")String specialite) throws IOException
	{
		//Extract ex=extract.searchexistingdoctor(nom, prenom, specialite);
		Doctor u=extract.AddDoctor(nom, prenom, specialite);
		if(u==null)
		{
			return Response.status(Status.OK).entity("doctor not found in doctlib, please make sure of your name and city").build();
		}
		return Response.status(Status.ACCEPTED).entity(u).build();
		
	}
	
}

