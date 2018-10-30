package tn.esprit.epione.resources;

import java.io.FileOutputStream;
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
import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Patient;
import tn.esprit.epione.persistance.User;
import tn.esprit.epione.utilities.Secured;

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
	@POST
	@Path("authentication")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateUser(@QueryParam("username") String username, @QueryParam("password") String password) {
		try {

			// Authenticate the user using the credentials provided
//			us.signIn(username, password);

			// Issue a token for the user
			String token = issueToken(username);

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
		
		return Response.status(Response.Status.FOUND).entity(us.findUserById(idUser)).build();
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
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signInWithUsername(@QueryParam("username") String username,@QueryParam("password") String password) {
		if (us.signInWithUsername(username, password)) {
			return Response.status(200).build();
		}
		return Response.status(401).entity("Login or Password unvalid !").build();
	}
	
	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signInWithEmail(@QueryParam("email") String email,@QueryParam("password") String password) {
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
	@Path("/forgotByMail")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response ForgotPasswordByMail(User u) {
		us.forgotPasswordByMail(u);
		return Response.status(Status.ACCEPTED).build();
	}

	@PUT
	@Path("forgot/{newtoken}/{newPwd}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeForgotPassword(@PathParam("newtoken") String token, @PathParam("newPwd") String newPwd,
			User u) {

		if (newPwd.length() > 8) {
			if (us.changeForgotPassword(token, newPwd, u)) {
				return Response.status(Status.ACCEPTED).entity("Your password is changed ! ").build();
			} else
				return Response.status(Status.NOT_ACCEPTABLE).entity("Token is not valid ! please verify you mail box")
						.build();
		} else
			return Response.status(Status.NOT_ACCEPTABLE).entity("Password length must be higher than 8 caracters")
					.build();

	}


	@GET
	@Path("nbrLoggedIn")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loggedinLast24() {
		return Response.status(Status.ACCEPTED).entity(us.isLoggedIn24H()).build();
	}

	@PUT
	@Consumes("multipart/form-data")
	public Response addPhoto(MultipartFormDataInput input) {
		int uid = 0;
		String finalPath = "";

		// les formats de données
		List<String> formatFile = new ArrayList<String>();
		formatFile.add("jpeg");
		formatFile.add("jpg");
		formatFile.add("png");

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("file");
		List<InputPart> userParts = uploadForm.get("user");

		for (InputPart inputPart : inputParts) {
			MultivaluedMap<String, String> headers = inputPart.getHeaders();
			try {
				InputStream inputStream = inputPart.getBody(InputStream.class, null);
				byte[] bytes = IOUtils.toByteArray(inputStream);
				String filename = getFileName(headers);

				// format file test
				String extension = "";
				int i = filename.lastIndexOf('.');
				if (i >= 0) {
					extension = filename.substring(i + 1);
				}

				if (!formatFile.contains(extension)) {
					return Response.status(Status.NOT_ACCEPTABLE)
							.entity("Format not supported  please use .jpeg .jpg .png  format").build();
				}
				// end of file format test

				String fileLocation = "C:\\epione\\images\\" + UUID.randomUUID().toString() + filename;

				int index = fileLocation.lastIndexOf("images") + 7;
				finalPath = fileLocation.substring(index);

				// create file
				FileOutputStream fileOuputStream = new FileOutputStream(fileLocation);
				fileOuputStream.write(bytes);

			} catch (IOException e) {
				return Response.status(Status.NOT_ACCEPTABLE).entity("Bad format, error parsing file.").build();
			}
		}

		// get id
		String[] contentDisposition = new String[1000];
		for (InputPart inputPart : userParts) {
			MultivaluedMap<String, String> headers = inputPart.getHeaders();
			contentDisposition = headers.getFirst("Content-Disposition").split(";");
			try {
				String userid = inputPart.getBodyAsString();
				uid = Integer.parseInt(userid);
				System.out.println(uid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Call service (persist)
		us.addUserPhoto(uid, finalPath);

		return Response.status(Status.OK).entity("Photo added.").build();
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

	private String getFileName(MultivaluedMap<String, String> headers) {
		String[] contentDisposition = headers.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = sanitizeFilename(name[1]);
				return finalFileName;
			}
		}

		return "unknown";
	}

	private String sanitizeFilename(String s) {
		return s.trim().replaceAll("\"", "");
	}


}
