package tn.esprit.epione.persistance;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import tn.esprit.epione.util.Util;

@Entity
public class Discussion  implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id ; 
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date timeSeen ; 
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Date creationTime  = Util.getDateNowUTC();
	// Navigation Properties

//	@JsonIgnore
	@OneToMany(mappedBy = "discussion", cascade = CascadeType.DETACH,fetch=FetchType.EAGER)
	private List<Message> messages;

	@ManyToOne
	@JoinColumn(name = "patient_id", referencedColumnName = "id", insertable = true, updatable = true)
	private Patient patient;

	@ManyToOne
	@JoinColumn(name = "doctor_id", referencedColumnName = "id", insertable = true, updatable = true)
	private Doctor doctor;
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public Date getTimeSeen() {
		return  Util.NbrHour(timeSeen, 1);
	}
	public void setTimeSeen(Date timeSeen) {
		this.timeSeen = timeSeen;
	} 
	public Date getCreationTime() {
		return  Util.NbrHour(creationTime, 1);
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	} 
	
	
	

}
