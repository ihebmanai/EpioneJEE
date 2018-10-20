package tn.esprit.epione.persistance;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Evaluation  implements Serializable{
	
	@Id
	private int id ; 
	private float note ; 
	
	@ManyToOne
	@JoinColumn(name="id_patient",referencedColumnName="id",insertable=false,updatable=false)
	private Patient patient ; 
	@ManyToOne
	@JoinColumn(name="id_appointment",referencedColumnName="id",insertable=false,updatable=false)
	private Appointment appointment ;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getNote() {
		return note;
	}
	public void setNote(float note) {
		this.note = note;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Appointment getAppointment() {
		return appointment;
	}
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	} 
	
	
	
	

}
