package tn.esprit.epione.persistance;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value="doc")
public class Doctor extends User implements Serializable {
	@Column(name="codeDoctor_d")
	private String codeDoctor ;
	
	@Column(name="activation_account")
	private int flag=0;
	
	@OneToMany(mappedBy="doctor")
	private List<Appointment> appointments ;
	
	
	@OneToMany(mappedBy="doctor")
	private List<Notification> notifications;
	
	
	@OneToMany(mappedBy="doctor")
	private List<Discussion> discussions;
	
	
	@OneToMany(mappedBy="doctor")
	private List<Report> reports;
	
	
	
	
	
	
	
	
	public Doctor() {
		super();
	}
	
	public Doctor(String codeDoctor,int flag, List<Appointment> appointments, List<Notification> notifications) {
		super();
		this.codeDoctor = codeDoctor;
		this.flag=flag;
		this.appointments = appointments;
		this.notifications = notifications;
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
	public List<Notification> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public List<Discussion> getDiscussions() {
		return discussions;
	}

	public void setDiscussions(List<Discussion> discussions) {
		this.discussions = discussions;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	
	
	
	
	

}
