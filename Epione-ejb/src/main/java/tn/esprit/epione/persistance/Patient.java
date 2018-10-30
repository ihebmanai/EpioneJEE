package tn.esprit.epione.persistance;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue(value="patient")
@PrimaryKeyJoinColumn(name="id")
public class Patient extends User implements Serializable{

	@Column(name="numberSocialSecurity_p")
	private String numberSocialSecurity ; 
	@JsonIgnore
	@OneToMany(mappedBy="patient")
	private List<Appointment> appointments ; 
	/*@JsonIgnore
	@OneToMany(mappedBy="patient")
	private List<Notification> notifications;*/ 
	@JsonIgnore
	@OneToMany(mappedBy="patient")
	private List<Discussion> discussions;
	
	@OneToOne
	private Course course;
	
	private int treated=0;
	
	
	
	
	
	
	public Patient() {
		super();
	}
	
	public Patient(int id) {
		this.id = id;
	}
	
	public Patient(String numberSocialSecurity, List<Appointment> appointments,
			List<Notification> notifications) {
		super();
		this.numberSocialSecurity = numberSocialSecurity;
		
		this.appointments = appointments;
		//this.notifications = notifications;
		
	}

	@OneToOne(mappedBy="patient")
	public Course getCourse() {
		return course;
	}


	public void setCourse(Course course) {
		this.course = course;
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


	/*public List<Notification> getNotifications() {
		return notifications;
	}


	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}*/


	public List<Discussion> getDiscussions() {
		return discussions;
	}


	public void setDiscussions(List<Discussion> discussions) {
		this.discussions = discussions;
	} 
	public int getTreated() {
		return treated;
	}

	public void setTreated(int treated) {
		this.treated = treated;
	}

	
	
}
