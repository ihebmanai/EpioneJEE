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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdated = Util.getDateNowUTC();

	private boolean doctorDeleted = false;
	private boolean patientDeleted = false;

	// Navigation Properties
	@OneToMany(mappedBy = "discussion", fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE,CascadeType.PERSIST})
	private List<Message> messages;

	@ManyToOne
	@JoinColumn(name = "patient_id", referencedColumnName = "id", insertable = true, updatable = true)
	private Patient patient;

	@ManyToOne
	@JoinColumn(name = "doctor_id", referencedColumnName = "id", insertable = true, updatable = true)
	private Doctor doctor;

	public Discussion() {
		lastUpdated = Util.getDateNowUTC();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getLastUpdated() {
		return Util.NbrHour(lastUpdated, 1);// +1 hour;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
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

	public boolean isDoctorDeleted() {
		return doctorDeleted;
	}

	public void setDoctorDeleted(boolean doctorDeleted) {
		this.doctorDeleted = doctorDeleted;
	}

	public boolean isPatientDeleted() {
		return patientDeleted;
	}

	public void setPatientDeleted(boolean patientDeleted) {
		this.patientDeleted = patientDeleted;
	}



}
