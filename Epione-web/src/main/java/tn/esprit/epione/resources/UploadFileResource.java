package tn.esprit.epione.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import tn.esprit.epione.utilities.Util;

@Path("file")
@RequestScoped
public class UploadFileResource {

	private List<String> formatFile = new ArrayList<String>();

	public UploadFileResource() {
		
	}
	
	public UploadFileResource(List<String> formatFile) {
		this.formatFile = formatFile;
	}

	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	@Produces(MediaType.TEXT_PLAIN)
	public Response uploadFile(MultipartFormDataInput input) {

		String fileName = "";

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("file");

		for (InputPart inputPart : inputParts) {

			try {

				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = Util.getFileName(header);

				// format file
				String extension = "";
				int i = fileName.lastIndexOf('.');
				if (i >= 0)
					extension = fileName.substring(i + 1);

				if (!formatFile.isEmpty() && !formatFile.contains(extension)) {
					return Response.status(Status.NOT_ACCEPTABLE).entity("Format of file not supported !!").build();
				}
				// end of format file

				// convert the uploaded file to inputstream
				InputStream inputStream = inputPart.getBody(InputStream.class, null);

				byte[] bytes = IOUtils.toByteArray(inputStream);

				// constructs upload file path
				fileName = UUID.randomUUID().toString() + "." + extension;

				Util.writeFile(bytes, fileName);

				System.out.println("Done");

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return Response.status(200).entity(fileName).build();
	}

}
