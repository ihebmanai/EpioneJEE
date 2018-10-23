package tn.esprit.epione.persistance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="specialist")
public class Specialist extends Doctor implements Serializable{
	
	@Column(name="speciality_s")
	private String speciality;
	
	@Column(name="motif_s")
	private String motif ;
	
	
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getMotif() {
		return motif;
	}
	public void setMotif(String motif) {
		this.motif = motif;
	} 
	
	

}
