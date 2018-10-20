package tn.esprit.epione.persistance;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Course implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id; 
	@Temporal(TemporalType.TIME)
	private Date dateOfLastModificaion;
	@OneToMany(mappedBy="course")
	private List<Patient> patients;
	@ManyToMany
	private List<Doctor> doctors;
	@OneToMany(mappedBy="course")
	private List<Report> reports; 
	
	
	
	
	
	public List<Doctor> getDoctors() {
		return doctors;
	}
	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}
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
	public Date getDateOfLastModificaion() {
		return dateOfLastModificaion;
	}
	public void setDateOfLastModificaion(Date dateOfLastModificaion) {
		this.dateOfLastModificaion = dateOfLastModificaion;
	}
	public Course() {
		super();
	}
	public Course(int id, Date dateOfLastModificaion) {
		super();
		this.id = id;
		this.dateOfLastModificaion = dateOfLastModificaion;
	}
	public Course(Date dateOfLastModificaion) {
		super();
		this.dateOfLastModificaion = dateOfLastModificaion;
	}
	public List<Patient> getPatients() {
		return patients;
	}
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	
	
}
