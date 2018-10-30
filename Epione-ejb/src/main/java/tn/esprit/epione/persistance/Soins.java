package tn.esprit.epione.persistance;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Soins implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id ;
	private String typeSoins ;
	@ManyToOne
	@JoinColumn(name="id_doctor",referencedColumnName="id",insertable=true,updatable=true)
	private Doctor doctor;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypeSoins() {
		return typeSoins;
	}
	public void setTypeSoins(String typeSoins) {
		this.typeSoins = typeSoins;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	} 
	



}
