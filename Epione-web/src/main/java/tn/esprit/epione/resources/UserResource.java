package tn.esprit.epione.resources;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import tn.esprit.epione.interfaces.UserServiceLocal;
import tn.esprit.epione.persistance.Administrator;
import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Patient;
import tn.esprit.epione.persistance.User;
import tn.esprit.epione.utilities.Secured;
import tn.esprit.epione.utilities.Util;

@Path("user")
@RequestScoped
public class UserResource {

	@Context
	private UriInfo uriInfo;

	@Context
	SecurityContext securityContext;

	@EJB
	UserServiceLocal us;

	/*
	 * ******************************* To get valid token !!
	 * http://localhost:8089/epione-web/user/authentication
	 */

//	@POST
//	@Path("authentication")
//	@Produces(MediaType.TEXT_PLAIN)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response authenticateUserByUsername(@QueryParam("username") String username,
//			@QueryParam("password") String password) {
//		try {
//
//			// Authenticate the user using the credentials provided
//			us.signInWithUsername(username, password);
//
//			// Issue a token for the user
//			String token = issueToken(username);
//
//			// Return the token on the response
//			return Response.ok(token).build();
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			return Response.status(Response.Status.FORBIDDEN).entity("Can't get token for the user !").build();
//		}
//	}

	@POST
	@Path("authentication")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateUserByEmail(@QueryParam("email") String email,
			@QueryParam("password") String password) {
		try {
			

			// Authenticate the user using the credentials provided
			us.signInWithEmail(email, password);

			// Issue a token for the user
			String token = issueToken(email);

			// Return the token on the response
			return Response.ok(token).build();

		} catch (Exception e) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
	}

	/*
	 * ******************************* Test @Secured *******************************
	 * http://localhost:8089/epione-web/user/TestSecured you can test with the
	 * generated token (add it to the authorization bearer token in PostMan !! ) :
	 *
	 */
	@Secured
	@POST
	@Path("/TestSecured")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getFullUser(User user) {
		return Response.status(Status.ACCEPTED).entity("teeeeeeest").build();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("id") int idUser) {

		return Response.status(Response.Status.FOUND).entity(us.getUserById(idUser)).build();
	}
	
	@GET
	@Path("ByEmail/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("email") String email) {

		return Response.status(Response.Status.FOUND).entity(us.findUserByEmail(email)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		return Response.status(Response.Status.FOUND).entity(us.getAllUsers()).build();
	}

	@POST
	@Path("addDoctor")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addDoctor(Doctor user) {
		int id = us.addDoctor(user);
		return Response.status(Response.Status.CREATED).entity(id).build();
	}

	@POST
	@Path("addPatient")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPatient(Patient user) {
		int id = us.addPatient(user);

		return Response.status(Response.Status.CREATED).entity(id).build();
	}

	@POST
	@Path("addAdministrator")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAdministrator(Administrator user) {
		int id = us.addAdministrator(user);

		return Response.status(Response.Status.CREATED).entity(id).build();
	}

	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signInWithUsername(@QueryParam("username") String username,
			@QueryParam("password") String password) {
		if (us.signInWithUsername(username, password)) {
			return Response.status(200).build();
		}
		return Response.status(401).entity("Login or Password unvalid !").build();
	}

	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signInWithEmail(@QueryParam("email") String email, @QueryParam("password") String password) {
		if (us.signInWithEmail(email, password)) {
			return Response.status(200).build();
		}
		return Response.status(401).entity("Login or Password unvalid !").build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{token}")
	public Response confirmAccount(@PathParam("token") String token, User u) {
		if (us.confirmAccount(token, u)) {
			return Response.status(Status.ACCEPTED).build();
		} else {
			return Response.status(Status.NOT_ACCEPTABLE).build();
		}

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{oldpwd}/{newpwd}")
	public Response changePassword(@PathParam("oldpwd") String oldPassword, @PathParam("newpwd") String newPassword,
			User u) {
		if (newPassword.length() > 8) {
			if (us.changePassword(oldPassword, newPassword, u)) {
				return Response.status(Status.ACCEPTED).entity("Password changed !").build();
			}
			return Response.status(Status.NOT_ACCEPTABLE).entity("please check your old password !").build();

		}

		else {
			return Response.status(Status.NOT_ACCEPTABLE).entity("Password length must be higher than 8 caracters !")
					.build();
		}
	}

	@PUT
	@Path("/forgotByMail/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response forgotPasswordByMail(@PathParam("id") int idUser) {
		if (us.forgotPasswordByMail(idUser))
			return Response.ok("mail sent").build();
		return Response.ok("mail failed").build();

	}

	@PUT
	@Path("forgot/{id}/{newtoken}/{newPwd}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeForgotPassword(@PathParam("newtoken") String token, @PathParam("newPwd") String newPwd,
			@PathParam("id") int idUser) {

		if (newPwd.length() > 8) {
			if (us.changeForgotPassword(idUser, token, newPwd)) {
				return Response.status(Status.ACCEPTED).entity("Your password is changed ! ").build();
			} else
				return Response.status(Status.NOT_ACCEPTABLE).entity("Token is not valid ! please verify you mail box")
						.build();
		} else
			return Response.status(Status.NOT_ACCEPTABLE).entity("Password length must be higher than 8 caracters")
					.build();

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response desactivateAccount(User u) {
		if (us.banAccount(u)) {
			return Response.status(Status.ACCEPTED)
					.entity("Account has been desactivated :( hope to see you soon again !").build();
		} else {
			return Response.status(Status.NOT_ACCEPTABLE).entity("Account desactivation failed").build();
		}
	}

	@GET
	@Path("nbrLoggedIn")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loggedinLast24() {
		return Response.status(Status.ACCEPTED).entity(us.isLoggedIn24H()).build();
	}

	@PUT
	@Path("addPhoto/{id}")
	@Consumes("multipart/form-data")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPhoto(MultipartFormDataInput input, @PathParam(value = "id") int idUser) {

		// les formats de données
		List<String> formatFile = new ArrayList<String>();
		formatFile.add("jpeg");
		formatFile.add("jpg");
		formatFile.add("png");

		User u = us.findUserById(idUser);
		if (u == null)
			return Response.status(Status.NOT_ACCEPTABLE).entity("User not found ! ").build();

		String fileName = "";

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("file");
		InputPart inputPart = inputParts.get(0);

		try {

			MultivaluedMap<String, String> header = inputPart.getHeaders();
			fileName = Util.getFileName(header);

			// format file
			String extension = "";
			int i = fileName.lastIndexOf('.');
			if (i >= 0)
				extension = fileName.substring(i + 1);

			if (!formatFile.contains(extension))
				return Response.status(Status.NOT_ACCEPTABLE).entity("Format of file not supported !!").build();

			// end of format file

			// convert the uploaded file to inputstream
			InputStream inputStream = inputPart.getBody(InputStream.class, null);
			byte[] bytes = IOUtils.toByteArray(inputStream);

			fileName = UUID.randomUUID().toString() + "." + extension;
			Util.writeFile(bytes, fileName);// in .. Epione/Files

			System.out.println("Done");

//			Call service (persist)
			us.addUserPhoto(idUser, fileName);

			return Response.status(200).entity(fileName).build();

		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(Status.NOT_ACCEPTABLE).entity("upload failed !").build();
		}

//				// create file
//				FileOutputStream fileOuputStream = new FileOutputStream(fileLocation);
//				fileOuputStream.write(bytes);

	}

	// ======================================
	// = Private methods =
	// ======================================

	private String issueToken(String username) {
		// The issued token must be associated to a user
		// Return the issued token

		String keyString = "simplekey";
		Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
		System.out.println("the key is : " + key);

		String jwtToken = Jwts.builder().setSubject(username).setIssuer(uriInfo.getAbsolutePath().toString())
				.setIssuedAt(new Date()).setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
				.signWith(SignatureAlgorithm.HS512, key).compact();

		System.out.println("the returned token is : " + jwtToken);
		return jwtToken;
	}

	private Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
}
