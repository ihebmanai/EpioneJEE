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




public class ExtractionReso {

	 public static void main(String[] args) throws IOException {
//		   
//
//	        Document doc;
//	        try {
//
//	            // need http protocol
//	            doc = Jsoup.connect("https://www.doctolib.fr/medecin-generaliste/france").userAgent("Mozilla").get();
//
//	            // get page title
//	            String title = doc.title();
//	            System.out.println("title : " + title);
//
//	            // get all links
//	            //ArrayList<?> links = (ArrayList) doc.getElementsByClass("dl-search-result-name js-search-result-path");
//	            Elements links =  doc.getElementsByClass("dl-search-result-name").addClass("js-search-result-path");
//	           ArrayList<?> text=(ArrayList) links;
//	           System.out.println(links.text());
//	            for (Object link : text) {
//
//	            	System.out.println(((Elements) text).text());
////	                // get the value from href attribute
////	                System.out.println("\nlink : " + ((Node) link));
////	                System.out.println("text : " + ((org.jsoup.nodes.Element) link).text());
//
//	            }
//
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }

		 
		
		        Document doc = Jsoup.connect("https://www.doctolib.fr/medecin-generaliste/france").userAgent("Mozilla").get();
		        Elements tableRows = doc.getElementsByClass
		        		("dl-search-result-title");
		        for (Element row : tableRows) {
		            Elements cls1 = row.getElementsByClass("dl-search-result-name").addClass("js-search-result-path");
		            Elements cls2 = row.getElementsByClass("dl-search-result-subtitle");
		           // Elements cls3 = row.getElementsByClass("cls3");

		            if (!cls1.isEmpty() && !cls2.isEmpty() ) {
		                System.out.println(cls1.get(0).text());
		                System.out.println(cls2.get(0).text());
		                //System.out.println(cls3.get(0).text());
		            }
		        }
		    
	   }
	

}
