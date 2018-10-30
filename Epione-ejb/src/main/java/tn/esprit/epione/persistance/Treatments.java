package tn.esprit.epione.persistance;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
public class Treatments implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id; 
	private String nameTreatment;
	private String dateTreatments;
	private int valider;
	private String doctorForRecommandation;
	private String justification;
	@JsonIgnore
	@ManyToOne
	private Report report ; 
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNameTreatment() {
		return nameTreatment;
	}
	public void setNameTreatment(String nameTreatment) {
		this.nameTreatment = nameTreatment;
	}
	public String getDateTreatments() {
		return dateTreatments;
	}
	public void setDateTreatments(String dateTreatments) {
		this.dateTreatments = dateTreatments;
	}
	@JsonIgnore
	public Report getReport() {
		return report;
	}
	@JsonProperty
	public void setReport(Report report) {
		this.report = report;
	}
	public int getValider() {
		return valider;
	}
	public void setValider(int valider) {
		this.valider = valider;
	}
	public String getDoctorForRecommandation() {
		return doctorForRecommandation;
	}
	public void setDoctorForRecommandation(String doctorForRecommandation) {
		this.doctorForRecommandation = doctorForRecommandation;
	}
	public String getJustification() {
		return justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
		
	
}
