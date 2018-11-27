package tn.esprit.epione.persistance;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="Appointment")
public class Appointment implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id ; 
	private String title ; 
	private String object ;
	private String message ;
	// type : urgence
   @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd",timezone="GMT+01")
	@Temporal(TemporalType.DATE)
	private Date date_appointment ;
	private String start_hour ; 
	private String endHour ; 
	private float note;
	@Enumerated
	private State state ;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_patient",referencedColumnName="id",insertable=true,updatable=true)
	private Patient patient ;
	@ManyToOne
	@JoinColumn(name="id_doctor",referencedColumnName="id",insertable=true,updatable=true)
	private Doctor doctor ; 
	
	@OneToOne(mappedBy="appointment", cascade = CascadeType.REMOVE)
	@JoinColumn(name="id_evaluation",referencedColumnName="id",insertable=true,updatable=true)
	private Evaluation evaluation;
	
	
	
	
	public Appointment() {
		super();
	}
	
	
	public Appointment(String title, String object, String message, Date date_appointment, String start_hour,
			String endHour, float note, State state, Patient patient, Doctor doctor) {
		super();
		this.title = title;
		this.object = object;
		this.message = message;
		this.date_appointment = date_appointment;
		this.start_hour = start_hour;
		this.endHour = endHour;
		this.note = note;
		this.state = state;
		this.patient = patient;
		this.doctor = doctor;
	}


	public float getNote() {
		return note;
	}


	public void setNote(float note) {
		this.note = note;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDate_appointment() {
		return date_appointment;
	}
	public void setDate_appointment(Date date_appointment) {
		this.date_appointment = date_appointment;
	}
	public String getStart_hour() {
		return start_hour;
	}
	public void setStart_hour(String start_hour) {
		this.start_hour = start_hour;
	}
	public String getEndHour() {
		return endHour;
	}
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
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
	

	public Evaluation getEvaluation() {
		return evaluation;
	}


	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}


	@Override
	public String toString() {
		return "Appointment [id=" + id + ", title=" + title + ", object=" + object + ", message=" + message
				+ ", date_appointment=" + date_appointment + ", start_hour=" + start_hour + ", endHour=" + endHour
				+ ", state=" + state + ", patient=" + patient + ", doctor=" + doctor 
				+ "]";
	} 
	
	
	

}
