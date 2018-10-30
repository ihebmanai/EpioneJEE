package tn.esprit.epione.services;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
//				System.out.println("photo "+photo);
//				System.out.println("profil "+profil);
			if (!nom.isEmpty() && !specialite.isEmpty()) {
				// System.out.println("nom: " + nom.get(0).text());
				// String[] output = nom.get(0).text().split(" ", 2);
				// String p = output[1];

				// System.out.println("nom: " + p);
				ex.setNom(nom.get(0).text());

				// System.out.println("specialité: " + specialite.get(0).text());
				ex.setSpecialite(specialite.get(0).text());

				// System.out.println("adresse: " + adresse.get(0).text());
				ex.setAdresse(adresse.get(0).text());

				ex.setPhoto(photo);
				ex.setProfile(profil);

				// System.out.println("-------------");
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
//				System.out.println("photo "+photo);
//				System.out.println("profil "+profil);
			if (!nom.isEmpty() && !specialite.isEmpty()) {
				// System.out.println("nom: " + nom.get(0).text());
				// String[] output = nom.get(0).text().split(" ", 2);
				// p = output.
				// System.out.println("nom: " + p);
				ex.setNom(nom.get(0).text());

				// System.out.println("specialité: " + specialite.get(0).text());
				ex.setSpecialite(specialite.get(0).text());

				// System.out.println("adresse: " + adresse.get(0).text());
				ex.setAdresse(adresse.get(0).text());

				ex.setPhoto(photo);
				ex.setProfile(profil);

				// System.out.println("-------------");
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

			//System.out.println("photo " + photo);
//				System.out.println("profil "+profil);
			if (!nom.isEmpty() && !specialite.isEmpty()) {
				// System.out.println("nom: " + nom.get(0).text());
				// String[] output = nom.get(0).text().split(" ", 2);
				// String p = output[1];

				// System.out.println("nom: " + p);
				ex.setNom(nom.get(0).text());

				// System.out.println("specialité: " + specialite.get(0).text());
				ex.setSpecialite(specialite.get(0).text());

				// System.out.println("adresse: " + adresse.get(0).text());
				ex.setAdresse(adresse.get(0).text());

				ex.setPhoto(photo);
				ex.setProfile(profil);

				//System.out.println("-------------");
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
			specialite = tableRows.first().getElementsByClass("dl-profile-header-speciality");
			photo = tableRows.get(1).getElementsByTag("img").attr("src");
			if (!tabrow.isEmpty())

			{
				telephone = tabrow.get(1).getElementsByClass("dl-display-flex").text();
				//System.out.println("phone= " + telephone);
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
			ex.setAdresse(adresse.get(0).text());
			ex.setNom(lastname);
			ex.setPrenom(firstname);
			ex.setSpecialite(specialite.get(0).text());
			ex.setPhoto(photo);
		//	System.out.println("photo: " + photo);
			return ex;
			// }
		} else
			System.out.println("doctor not found in doctlib, please make sure of your name and city");
		return null;

	}

	@Override
	public Doctor AddDoctor(String nom, String prenom, String specialie) throws IOException {
		Extract e = new Extract();
		e = searchexistingdoctor(nom, prenom, specialie);
		if (e != null) {
			String fulladdres = e.getAdresse();
			String villeetcode = "";
			String ville = "";
			String rue = "";
			String[] naming = fulladdres.split(",", 2);
			villeetcode = naming[1];
			String[] villes = villeetcode.split(" ", 3);
			ville = villes[2];
			rue = naming[0];
			System.out.println("ville: " + ville);
			System.out.println("rue: " + rue);

			Doctor user = new Doctor();
			Adresse adresse = new Adresse(rue, ville);
			user.setAdress(adresse);
			user.setRole(Role.doctor);
			user.setFirstName(e.getPrenom());
			user.setLastName(e.getNom());
			user.setPhoto("www.doctolib.fr" + e.getPhoto());
			user.setPhone(e.getTelephone());
			user.setEmail("b.skander@yahoo.fr");
			System.out.println(user);
			em.persist(user);
			return user;
		} else {
			System.out.println("doctor not found in doctlib, please make sure of your name and city");
		}
		return null;
	}

}