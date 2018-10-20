package tn.esprit.epione.persistance;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class Recommendation implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id; 
	private String nameDoctor; 
	private String descrition;
	@ManyToOne
	private Report report ; 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNameDoctor() {
		return nameDoctor;
	}
	public void setNameDoctor(String nameDoctor) {
		this.nameDoctor = nameDoctor;
	}
	public String getDescrition() {
		return descrition;
	}
	public void setDescrition(String descrition) {
		this.descrition = descrition;
	}
	
	
}
