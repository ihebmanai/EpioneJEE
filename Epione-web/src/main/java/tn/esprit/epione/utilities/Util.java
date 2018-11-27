package tn.esprit.epione.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.ws.rs.core.MultivaluedMap;

public class Util {

	// save to ...\Epione\Files\
	public static void writeFile(byte[] content, String filename) throws IOException {

		String uploadedFilePath = System.getProperty("user.dir");
		uploadedFilePath = "C:\\epione\\Files\\";
		File file = new File(uploadedFilePath + filename);

		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}

		System.out.println(file.getPath() + "*********************************");

		FileOutputStream fop = new FileOutputStream(file);

		fop.write(content);
		fop.flush();
		fop.close();

	}

	/**
	 * header sample { Content-Type=[image/png], Content-Disposition=[form-data;
	 * name="file"; filename="filename.extension"] }
	 **/
	// get uploaded filename
	public static String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}
}
