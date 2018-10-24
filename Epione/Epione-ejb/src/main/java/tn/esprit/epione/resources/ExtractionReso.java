package tn.esprit.epione.resources;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.ejb.Remote;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import en.esprit.epione.interfaces.ExtractionInt;

public class ExtractionReso implements ExtractionInt {

	public void SearchBySpeciality(String spec) {
		// https://www.doctolib.fr/medecin-generaliste/france
		Document doc;
		Elements nom;
		Elements adresse;
		Elements specialite;
		int nb = 0;
		try {
			for (int i = 1; i < 100; i++) {
				doc = Jsoup.connect("https://www.doctolib.fr/" + spec + "?page=" + i).userAgent("Mozilla").get();
				Elements tableRows = doc.getElementsByClass("dl-search-result-presentation");
				
				for (Element row : tableRows) {
					nb++;
					
					
					System.out.println(nb);
					nom = row.getElementsByClass("dl-search-result-name").addClass("js-search-result-path");
					adresse = row.getElementsByClass("dl-text").addClass("dl-text-body");
					specialite = row.getElementsByClass("dl-search-result-subtitle");

					if (!nom.isEmpty() && !specialite.isEmpty()) {
						System.out.println("nom: " + nom.get(0).text());
						System.out.println("specialité: " + specialite.get(0).text());
						System.out.println("adresse: " + adresse.get(0).text());
						System.out.println("-------------");
					}

				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("end of pages");
		}

	}


	@Override
	public void SearchByPlace(String place) {
		// https://www.doctolib.fr/medecin-generaliste/france
				Document doc;
				Elements nom;
				Elements adresse;
				Elements specialite;
				int nb = 0;
				try {
					for (int i = 1; i < 100; i++) {
						doc = Jsoup.connect("https://www.doctolib.fr/medecin-generaliste/"+place+"?page=" + i).userAgent("Mozilla").get();
						Elements tableRows = doc.getElementsByClass("dl-search-result-presentation");
						
						for (Element row : tableRows) {
							nb++;
							
							
							System.out.println(nb);
							nom = row.getElementsByClass("dl-search-result-name").addClass("js-search-result-path");
							adresse = row.getElementsByClass("dl-text").addClass("dl-text-body");
							specialite = row.getElementsByClass("dl-search-result-subtitle");

							if (!nom.isEmpty() && !specialite.isEmpty()) {
								System.out.println("nom: " + nom.get(0).text());
								System.out.println("specialité: " + specialite.get(0).text());
								System.out.println("adresse: " + adresse.get(0).text());
								System.out.println("-------------");
							}

						}

					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("end of pages");
				}
		
	}

	@Override
	public void SearchBySpecialityandPlace(String spec, String place) {
		
		// https://www.doctolib.fr/medecin-generaliste/france
				Document doc;
				Elements nom;
				Elements adresse;
				Elements specialite;
				int nb = 0;
				try {
					for (int i = 1; i < 100; i++) {
						doc = Jsoup.connect("https://www.doctolib.fr/" + spec + "/"+place+"?page=" + i).userAgent("Mozilla").get();
						Elements tableRows = doc.getElementsByClass("dl-search-result-presentation");
						
						for (Element row : tableRows) {
							nb++;
							
							
							System.out.println(nb);
							nom = row.getElementsByClass("dl-search-result-name").addClass("js-search-result-path");
							adresse = row.getElementsByClass("dl-text").addClass("dl-text-body");
							specialite = row.getElementsByClass("dl-search-result-subtitle");

							if (!nom.isEmpty() && !specialite.isEmpty()) {
								System.out.println("nom: " + nom.get(0).text());
								System.out.println("specialité: " + specialite.get(0).text());
								System.out.println("adresse: " + adresse.get(0).text());
								System.out.println("-------------");
							}

						}

					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("end of pages");
				}
	}

}