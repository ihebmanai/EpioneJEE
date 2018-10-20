package tn.esprit.epione.persistance;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue(value="pat")
@PrimaryKeyJoinColumn(name="id")
public class Patient extends User implements Serializable{

	@Column(name="numberSocialSecurity_p")
	private String numberSocialSecurity ; 
	
	@OneToMany(mappedBy="patient")
	private List<Appointment> appointments ; 
	@OneToMany(mappedBy="patient")
	private List<Notification> notifications; 
	@OneToMany(mappedBy="patient")
	private List<Evaluation> evaluations;
	@OneToMany(mappedBy="patient")
	private List<Discussion> discussions;
	@ManyToOne
	private Course course ;
	
	
	
	
	public Patient() {
		super();
	}
	
	
	public Patient(String numberSocialSecurity, List<Appointment> appointments,
			List<Notification> notifications, List<Evaluation> evaluations) {
		super();
		this.numberSocialSecurity = numberSocialSecurity;
		
		this.appointments = appointments;
		this.notifications = notifications;
		this.evaluations = evaluations;
	}


	public String getNumberSocialSecurity() {
		return numberSocialSecurity;
	}
	public void setNumberSocialSecurity(String numberSocialSecurity) {
		this.numberSocialSecurity = numberSocialSecurity;
	}
	
	public List<Appointment> getAppointments() {
		return appointments;
	}
	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}


	public List<Notification> getNotifications() {
		return notifications;
	}


	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}


	public List<Evaluation> getEvaluations() {
		return evaluations;
	}


	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}


	public List<Discussion> getDiscussions() {
		return discussions;
	}


	public void setDiscussions(List<Discussion> discussions) {
		this.discussions = discussions;
	} 
	
	
}
