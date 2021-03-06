package tn.esprit.epione.interfaces;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import tn.esprit.epione.persistance.Appointment;
import tn.esprit.epione.persistance.Course;
import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Notification;
import tn.esprit.epione.persistance.Patient;
import tn.esprit.epione.persistance.Report;
import tn.esprit.epione.persistance.Treatments;
@Remote
public interface ICourseServices {
	//notification
	public void setNotification(int appId,String appointmentType);
	public void setNotificationSeen(int id);
	public ArrayList<Notification> getNotification();
	//get rapport 
	public Report getReportById(int reportId);
	public void addReport(int appId,String description,String desease,String object);
	public List<Report> getAllReportByPatient(int patientId);
	public List<Report> getAllReportByDoctor(int patientId,int doctorId);
	public List<Report> getAllReportByDesease(int patientId,String desease);
	public List<Report> getAllReportByDeseaseAndDoctor(int patientId,String desease,int doctorId);
	//treatments
	public void addTreatement(int idReport,String doctForRecomantion,String date,String nameTreatment);
	public void validateTreatement(int treatmentId);
	public List<Treatments> getAllTreatmentsByReport(int idReport);
	public void updateTreatment(Treatments t);
	public Course getCourseByIdPatient(int idPatinet);
	public List<Appointment> getAppointmentsByIdPatient(int idPatient);
	public Appointment getAppointmentsById(int id);


}

