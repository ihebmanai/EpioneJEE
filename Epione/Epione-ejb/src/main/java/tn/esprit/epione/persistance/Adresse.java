package tn.esprit.epione.persistance;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Adresse implements Serializable {

	private String rue ; 
	private String ville ;
	
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	
	public Adresse() {
		super();
	}
	public Adresse(String rue, String ville) {
		super();
		this.rue = rue;
		this.ville = ville;
	} 
	
	
	
	
}
