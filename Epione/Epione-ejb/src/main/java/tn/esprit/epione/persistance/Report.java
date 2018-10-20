package tn.esprit.epione.persistance;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Report implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id; 
	private String desease ; 
	private String objet; 
	private String description;
	@ManyToOne
	@JoinColumn(name="id_doctor",referencedColumnName="id",insertable=false,updatable=false)
	private Doctor doctor; 
	@ManyToOne
	@JoinColumn(name="id_course",referencedColumnName="id",insertable=false,updatable=false)
	private Course course;
	@OneToMany(mappedBy="report")
	private List<Treatments> treatments;
	@OneToMany(mappedBy="report")
	private List<Recommendation> recommendations;
	
	public List<Treatments> getTreatments() {
		return treatments;
	}
	public void setTreatments(List<Treatments> treatments) {
		this.treatments = treatments;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDesease() {
		return desease;
	}
	public void setDesease(String desease) {
		this.desease = desease;
	}
	public String getObjet() {
		return objet;
	}
	public void setObjet(String objet) {
		this.objet = objet;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	
	
}
