package tn.esprit.epione.persistance;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value="doctor")
@PrimaryKeyJoinColumn(name="id")
public class Doctor extends User implements Serializable {
	@Column(name="codeDoctor_d")
	private String codeDoctor ;
	@Column(name="activation_account")
	private int flag=0;
	@JsonIgnore
	@OneToMany(mappedBy="doctor")
	private List<Appointment> appointments ;
	/*
	 *zid choufha hethi
	@JsonIgnore
	@OneToMany(mappedBy="doctor")
	private List<Notification> notifications;*/
	@JsonIgnore
	@OneToMany(mappedBy="doctor")
	private List<Discussion> discussions;
	
	
	public Doctor() {
		super();
	}
	
	public Doctor(int id) {
		this.id = id;
	}
	
	public Doctor(String codeDoctor, List<Appointment> appointments, List<Notification> notifications) {
		super();
		this.codeDoctor = codeDoctor;
		
		this.appointments = appointments;
		//this.notifications = notifications;
	}

	public String getCodeDoctor() {
		return codeDoctor;
	}
	public void setCodeDoctor(String codeDoctor) {
		this.codeDoctor = codeDoctor;
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



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Doctor other = (Doctor) obj;
		if (codeDoctor == null) {
			if (other.codeDoctor != null)
				return false;
		} else if (!codeDoctor.equals(other.codeDoctor))
			return false;
		return true;
	}

	
	
	
	
	

}
