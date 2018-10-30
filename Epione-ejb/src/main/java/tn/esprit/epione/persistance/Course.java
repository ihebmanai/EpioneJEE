package tn.esprit.epione.persistance;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Course implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id; 
	@OneToOne
	private Patient patient;
	@JsonIgnore
	@OneToMany(mappedBy="course" ,fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	private List<Report> reports; 
	
	
	
	

	public List<Report> getReports() {
		return reports;
	}
	public void setReports(List<Report> reports) {
		this.reports = reports;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Course() {
		super();
	}
	public Course(int id, Date dateOfLastModificaion) {
		super();
		this.id = id;
	}
	public Course(Date dateOfLastModificaion) {
		super();
	}
	
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	

	

	
	
}
