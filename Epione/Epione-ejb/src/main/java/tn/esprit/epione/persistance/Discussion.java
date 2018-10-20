package tn.esprit.epione.persistance;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Discussion  implements Serializable{
	
	@Id
	private int id ; 
	private String senderOne ; 
	private String senderTwo;
	@ManyToOne
	private Patient patient ; 
	@ManyToOne
	private Doctor doctor ; 
	
	@OneToMany(mappedBy="discussion")
	private List<Message> messages;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSenderOne() {
		return senderOne;
	}
	public void setSenderOne(String senderOne) {
		this.senderOne = senderOne;
	}
	public String getSerndeTwo() {
		return senderTwo;
	}
	public void setSerndeTwo(String serndeTwo) {
		this.senderTwo = serndeTwo;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public String getSenderTwo() {
		return senderTwo;
	}
	public void setSenderTwo(String senderTwo) {
		this.senderTwo = senderTwo;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	} 
	
	
	
	

}
