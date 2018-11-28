package tn.esprit.epione.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tn.esprit.epione.interfaces.ExtractionInt;
import tn.esprit.epione.persistance.Adresse;
import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Extract;
import tn.esprit.epione.persistance.Role;
import tn.esprit.epione.persistance.User;

@Stateless
public class ExtractionReso implements ExtractionInt {

	@PersistenceContext(unitName = "Epione-ejb")
	EntityManager em;

	public ArrayList<Extract> SearchBySpeciality(String spec, int pagenumber) throws IOException {
		// https://www.doctolib.fr/medecin-generaliste/france
		Document doc;

		Elements nom;
		Elements adresse;
		Elements specialite;
		String photo;
		String profil;
		int nb = 0;
		ArrayList<Extract> list = new ArrayList<>();
		String url = "https://www.doctolib.fr/" + spec + "?page=" + pagenumber;
		doc = Jsoup.connect(url).userAgent("Mozilla").timeout(25000).get();
		Elements tableRows = doc.getElementsByClass("dl-search-result-presentation");

		for (Element row : tableRows) {
			nb++;
			Extract ex = new Extract();
			System.out.println(nb);
			nom = row.getElementsByClass("dl-search-result-name").addClass("js-search-result-path");
			adresse = row.getElementsByClass("dl-text").addClass("dl-text-body");
			specialite = row.getElementsByClass("dl-search-result-subtitle");
			photo = row.getElementsByTag("img").attr("src");
			profil = row.getElementsByTag("a").attr("href");
			if (!nom.isEmpty() && !specialite.isEmpty()) {

				ex.setLastname(nom.get(0).text());
				ex.setSpeciality_s(specialite.get(0).text());

//				String fulladdres = adresse.get(0).text();
//				String villeetcode = "";
//				String ville = "";
//				String rue = "";
//				String[] naming2 = fulladdres.split(",", 2);
//				villeetcode = naming2[1];
//				String[] villes = villeetcode.split(" ", 3);
//				ville = villes[2];
//				rue = naming2[0];
//				Adresse adresses = new Adresse(rue, ville);
//				ex.setAdresse(adresses);

				ex.setPhoto(photo);
				ex.setProfile(profil);

			
			}
			list.add(ex);

		}

		return list;

	}

	@Override
	public ArrayList<Extract> SearchByPlace(String place, int pagenumber) throws IOException {
		// https://www.doctolib.fr/medecin-generaliste/france
		Document doc;
		Elements nom;
		Elements adresse;
		Elements specialite;
		String photo;
		String profil;
		int nb = 0;
		ArrayList<Extract> list = new ArrayList<>();
		String url = "https://www.doctolib.fr/medecin-generaliste/" + place + "?page=" + pagenumber;
		System.out.println(url);
		doc = Jsoup.connect(url).userAgent("Mozilla").timeout(25000).get();
		Elements tableRows = doc.getElementsByClass("dl-search-result-presentation");

		for (Element row : tableRows) {
			nb++;
			Extract ex = new Extract();
			System.out.println(nb);
			nom = row.getElementsByClass("dl-search-result-name").addClass("js-search-result-path");
			adresse = row.getElementsByClass("dl-text").addClass("dl-text-body");
			specialite = row.getElementsByClass("dl-search-result-subtitle");
			photo = row.getElementsByTag("img").attr("src");
			photo.substring(0);
			profil = row.getElementsByTag("a").attr("href");
			String p = "";

			if (!nom.isEmpty() && !specialite.isEmpty()) {

				ex.setLastname(nom.get(0).text());
				ex.setSpeciality_s(specialite.get(0).text());

//				String fulladdres = adresse.get(0).text();
//				String villeetcode = "";
//				String ville = "";
//				String rue = "";
//				String[] naming2 = fulladdres.split(",", 2);
//				villeetcode = naming2[1];	
//				String[] villes = villeetcode.split(" ", 3);
//				ville = villes[2];
//				rue = naming2[0];
//				Adresse adresses = new Adresse(rue, ville);
//				ex.setAdresse(adresses);

				ex.setPhoto(photo);
				ex.setProfile(profil);

			}
			list.add(ex);

		}

		return list;

	}

	@Override
	public ArrayList<Extract> SearchBySpecialityandPlace(String spec, String place, int pagenumber) throws IOException {

		// https://www.doctolib.fr/medecin-generaliste/france
		Document doc;
		Elements nom;
		Elements adresse;
		Elements specialite;
		String photo;
		String profil;
		int nb = 0;
		ArrayList<Extract> list = new ArrayList<>();
		String url = "https://www.doctolib.fr/" + spec + "/" + place + "?page=" + pagenumber;
		doc = Jsoup.connect(url).userAgent("Mozilla").timeout(25000).get();
		Elements tableRows = doc.getElementsByClass("dl-search-result-presentation");

		for (Element row : tableRows) {
			nb++;
			Extract ex = new Extract();
			System.out.println(nb);
			nom = row.getElementsByClass("dl-search-result-name").addClass("js-search-result-path");
			adresse = row.getElementsByClass("dl-text").addClass("dl-text-body");
			specialite = row.getElementsByClass("dl-search-result-subtitle");
			photo = row.getElementsByTag("img").attr("src");
			profil = row.getElementsByTag("a").attr("href");

			if (!nom.isEmpty() && !specialite.isEmpty()) {
				
//				
//				String fulladdres = adresse.get(0).text();
//				String villeetcode = "";
//				String ville = "";
//				String rue = "";
//				String[] naming2 = fulladdres.split(",", 2);
//				villeetcode = naming2[1];
//				String[] villes = villeetcode.split(" ", 3);
//				ville = villes[2];
//				rue = naming2[0];
//				Adresse adresses = new Adresse(rue, ville);
//				ex.setAdresse(adresses);
//				
				
				ex.setLastname(nom.get(0).text());
				ex.setSpeciality_s(specialite.get(0).text());
				ex.setPhoto(photo);
				ex.setProfile("www.doctolib.fr"+profil);

			
			}
			list.add(ex);

		}
		return list;

	}

	@Override
	public void test(String e, int b) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("string: " + e + " int: " + b);
	}

	@Override
	public Extract searchexistingdoctor(String nom, String prenom, String specialie) throws IOException {

		String url = "https://www.doctolib.fr/" + specialie + "/france" + "/" + prenom + "-" + nom;
		System.out.println(url);
		Document doc;
		Elements fullname;
		String lastname = "";
		String firstname = "";
		Elements adresse;
		Elements ad;
		Elements specialite;
		String photo;
		String telephone = "";

		Extract ex = new Extract();

		doc = Jsoup.connect(url).userAgent("Mozilla").timeout(25000).get();

		Elements tableRows = doc.getElementsByClass("dl-profile-wrapper");
		Elements tableRows2 = doc.getElementsByClass("dl-profile-card");
		Elements tabrow = doc.getElementsByClass("dl-profile-box");
		if (!tableRows.isEmpty() && !tableRows2.isEmpty())

		{
			fullname = tableRows.first().select("span");
			adresse = tableRows2.get(1).getElementsByClass("dl-profile-text");
			ad = tableRows2.get(1).getElementsByClass("dl-profile-doctor-place-map");
			String lat = ""
					+ Double.valueOf(doc.select(".dl-profile-doctor-place-map-img").attr("data-map-modal").substring(
							doc.select(".dl-profile-doctor-place-map-img").attr("data-map-modal").indexOf("\"lat\":")
									+ 6,
							doc.select(".dl-profile-doctor-place-map-img").attr("data-map-modal").indexOf(",",
									doc.select(".dl-profile-doctor-place-map-img").attr("data-map-modal")
											.indexOf("\"lat\":") + 6)));
			System.out.println(lat);
			String lng = ""
					+ Double.valueOf(doc.select(".dl-profile-doctor-place-map-img").attr("data-map-modal").substring(
							doc.select(".dl-profile-doctor-place-map-img").attr("data-map-modal").indexOf("\"lng\":")
									+ 6,
							doc.select(".dl-profile-doctor-place-map-img").attr("data-map-modal").indexOf("}",
									doc.select(".dl-profile-doctor-place-map-img").attr("data-map-modal")
											.indexOf("\"lng\":") + 6)));
			System.out.println(lng);
			specialite = tableRows.first().getElementsByClass("dl-profile-header-speciality");
			photo = tableRows.get(0).getElementsByTag("img").attr("src");
			if (!tabrow.isEmpty())

			{
				telephone = tabrow.get(1).getElementsByClass("dl-display-flex").text();
				// System.out.println("phone= " + telephone);
				ex.setTelephone(telephone);
			}
			System.out.println("phone= " + telephone);
			// if (!nom.isEmpty() && !specialite.isEmpty()) {
			String[] naming = fullname.get(0).text().split("[^A-Z]");
			for (String e : naming) {
				if (e.length() > 1)
					lastname = lastname + " " + e;
			}
			firstname = fullname.get(0).text().substring(0, (fullname.get(0).text().length() - lastname.length()));
			// ex.setTelephone(telephone);

			String fulladdres = adresse.get(0).text();
			String villeetcode = "";
			String ville = "";
			String rue = "";
			String[] naming2 = fulladdres.split(",", 2);
			villeetcode = naming2[1];
			String[] villes = villeetcode.split(" ", 3);
			ville = villes[2];
			rue = naming2[0];
			Adresse adresses = new Adresse(rue, ville);
			ex.setAdresse(adresses);

			ex.setLastname(lastname);
			ex.setFirstname(firstname);
			ex.setSpeciality_s(specialite.get(0).text());
			ex.setPhoto(photo);
			ex.setLat(lat);
			ex.setLng(lng);

			return ex;
			// }
		} else
			System.out.println("doctor not found in doctlib, please make sure of your name and city");
		return null;

	}

	@Override
	public Doctor AddDoctor(String nom, String prenom, String specialie, String password, String email)
			throws IOException {
		Extract e = new Extract();
		e = searchexistingdoctor(nom, prenom, specialie);
		if (e != null) {

			Doctor user = new Doctor();

			user.setAdress(e.getAdresse());
			user.setRole(Role.doctor);
			user.setFirstName(e.getFirstname());
			user.setLastName(e.getLastname());
			user.setPhoto("www.doctolib.fr" + e.getPhoto());
			user.setPhone(e.getTelephone());
			user.setEmail(email);
			user.setPassword(password);
			System.out.println(user);
			em.persist(user);
			return user;
		} else {
			System.out.println("doctor not found in doctlib, please make sure of your name and city");
		}
		return null;
	}

	@Override
	public Doctor AddDoctor(String nom, String prenom, String specialie, String password) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Doctor> getdoctors() {
		List<Doctor> doctors = new ArrayList<>();
		TypedQuery<Doctor> query = em.createQuery("SELECT d FROM Doctor d", Doctor.class);

		return doctors = query.getResultList();

	}

	@Override
	public Extract profile(String url) throws IOException {
		
		System.out.println(url);
		Document doc;
		Elements fullname;
		String lastname = "";
		String firstname = "";
		Elements adresse;
		Elements ad;
		Elements specialite;
		String photo;
		String telephone = "";
		Extract ex = new Extract();
		doc = Jsoup.connect(url).userAgent("Mozilla").timeout(25000).get();

		Elements tableRows = doc.getElementsByClass("dl-profile-wrapper");
		Elements tableRows2 = doc.getElementsByClass("dl-profile-card");
		Elements tabrow = doc.getElementsByClass("dl-profile-box");
		if (!tableRows.isEmpty() && !tableRows2.isEmpty())

		{
			fullname = tableRows.first().select("span");
			adresse = tableRows2.get(1).getElementsByClass("dl-profile-text");
			ad = tableRows2.get(1).getElementsByClass("dl-profile-doctor-place-map");
			String lat = ""
					+ Double.valueOf(doc.select(".dl-profile-doctor-place-map-img").attr("data-map-modal").substring(
							doc.select(".dl-profile-doctor-place-map-img").attr("data-map-modal").indexOf("\"lat\":")
									+ 6,
							doc.select(".dl-profile-doctor-place-map-img").attr("data-map-modal").indexOf(",",
									doc.select(".dl-profile-doctor-place-map-img").attr("data-map-modal")
											.indexOf("\"lat\":") + 6)));
			System.out.println(lat);
			String lng = ""
					+ Double.valueOf(doc.select(".dl-profile-doctor-place-map-img").attr("data-map-modal").substring(
							doc.select(".dl-profile-doctor-place-map-img").attr("data-map-modal").indexOf("\"lng\":")
									+ 6,
							doc.select(".dl-profile-doctor-place-map-img").attr("data-map-modal").indexOf("}",
									doc.select(".dl-profile-doctor-place-map-img").attr("data-map-modal")
											.indexOf("\"lng\":") + 6)));
			System.out.println(lng);
			specialite = tableRows.first().getElementsByClass("dl-profile-header-speciality");
			photo = tableRows.get(0).getElementsByTag("img").attr("src");
			if (!tabrow.isEmpty() && tabrow.size()>1)

			{
				telephone = tabrow.get(1).getElementsByClass("dl-display-flex").text();
				ex.setTelephone(telephone);
			}
			System.out.println("phone= " + telephone);

			String[] naming = fullname.get(0).text().split("[^A-Z]");
			for (String e : naming) {
				if (e.length() > 1)
					lastname = lastname + " " + e;
			}
			firstname = fullname.get(0).text().substring(0, (fullname.get(0).text().length() - lastname.length()));

			String fulladdres = adresse.get(0).text();
			String villeetcode = "";
			String ville = "";
			String rue = "";
			String[] naming2 = fulladdres.split(",", 2);
			villeetcode = naming2[1];
			String[] villes = villeetcode.split(" ", 3);
			ville = villes[2];
			rue = naming2[0];
			Adresse adresses = new Adresse(rue, ville);
			ex.setAdresse(adresses);

			ex.setLastname(lastname);
			ex.setFirstname(firstname);
			ex.setSpeciality_s(specialite.get(0).text());
			ex.setPhoto(photo);
			ex.setLat(lat);
			ex.setLng(lng);

			return ex;
			// }
		} else
			System.out.println("doctor not found in doctlib, please make sure of your name and city");
		return null;

	}

}