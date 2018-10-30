package tn.esprit.epione.services;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import tn.esprit.epione.interfaces.ICourseServices;
import tn.esprit.epione.persistance.Appointment;
import tn.esprit.epione.persistance.Course;
import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Notification;
import tn.esprit.epione.persistance.Patient;
import tn.esprit.epione.persistance.Report;
import tn.esprit.epione.persistance.Treatments;

@LocalBean
@Stateless
public class CourseService implements ICourseServices {
	
	@PersistenceContext(name="Epione-ejb")
	EntityManager em ;
	private List <Report> reportByDoctor;

	@Override
	public void setNotification(int appId,String appointmentType) {
		Appointment app=em.find(Appointment.class, appId);
		Notification n=new Notification();
		if(appointmentType.equals("cancelAppointement")){
			n.setAppointment(app);
			n.setDate(new Date());
			n.setObject("Your appointement had been cancelled please contact the doctor");
			n.setSeen(0);
			n.setTitle("Appointement Cancelled");
			n.setCheckAppointmentType(appointmentType);
			em.persist(n);
		}else{
			n.setAppointment(app);
			n.setDate(new Date());
			n.setObject(app.getObject());
			n.setSeen(0);
			n.setTitle(app.getTitle());
			n.setCheckAppointmentType(appointmentType);
			em.persist(n);
		}
			
		
	}

	@Override
	public void setNotificationSeen(int id) {
		Notification n=em.find(Notification.class, id);
		n.setSeen(1);
		em.merge(n);		
	}

	@Override
	public ArrayList<Notification> getNotification() {
		TypedQuery<Notification> query=em.createQuery("select n from Notification as n ",Notification.class);
		return (ArrayList<Notification>) query.getResultList();
	}
	
	public List<Report> getAllReportByPatient(int patientId){
		
		Patient p=em.find(Patient.class, patientId);
		List<Report> l = null;
		if(p==null)
			return l;
		TypedQuery<Course> query=em.createQuery("select c from Course as c where c.patient=:p ",Course.class);
		Course c= query.setParameter("p", p).getSingleResult();
		Collections.sort(c.getReports(),new Comparator<Report>(){
			public int compare(Report r1, Report r2){
				if(r1.getDate()==null || r2.getDate()==null)
					return 0;
				return r1.getDate().compareTo(r2.getDate());
			}; 
		});
	return c.getReports();
	}
	
	public List<Report> getAllReportByDoctor(int patientId,int doctorId ){
		Patient p=em.find(Patient.class, patientId);
		Doctor d=em.find(Doctor.class, doctorId);
		System.out.println("dkhalt");
		List<Report> l = null;
		if(p==null || d==null)
			return l;
		TypedQuery<Course> query=em.createQuery("select c from Course as c where c.patient=:p ",Course.class);
		Course c= query.setParameter("p", p).getSingleResult();
		Collections.sort(c.getReports(),new Comparator<Report>(){
			public int compare(Report r1, Report r2){
				if(r1.getDate()==null || r2.getDate()==null)
					return 0;
				return r1.getDate().compareTo(r2.getDate());
			}; 
		});
		List <Report >reportByDoctor = new ArrayList<>();
		for(Report r : c.getReports()){
			if(r.getAppointment().getDoctor().equals(d)	)
				reportByDoctor.add(r);
		}
		
		return reportByDoctor;
	}
	
	public List<Report> getAllReportByDesease(int patientId,String desease){
		Patient p=em.find(Patient.class, patientId);
		List<Report> l = null;
		if(p==null)
			return l;
		TypedQuery<Course> query=em.createQuery("select c from Course as c where c.patient=:p ",Course.class);
		Course c= query.setParameter("p", p).getSingleResult();
		Collections.sort(c.getReports(),new Comparator<Report>(){
			public int compare(Report r1, Report r2){
				if(r1.getDate()==null || r2.getDate()==null)
					return 0;
				return r1.getDate().compareTo(r2.getDate());
			}; 
		});
		List <Report >reportByDesease = new ArrayList<>();
		for(Report r : c.getReports()){
			if(r.getDesease().equals(desease)	)
				reportByDesease.add(r);
		}
		
		return reportByDesease;
	}
	
	public List<Report> getAllReportByDeseaseAndDoctor(int patientId,String desease,int doctorId){
		Patient p=em.find(Patient.class, patientId);
		Doctor d=em.find(Doctor.class, doctorId);
		List<Report> l = null;
		if(p==null || d==null)
			return l;
		TypedQuery<Course> query=em.createQuery("select c from Course as c where c.patient=:p ",Course.class);
		Course c= query.setParameter("p", p).getSingleResult();
		Collections.sort(c.getReports(),new Comparator<Report>(){
			public int compare(Report r1, Report r2){
				if(r1.getDate()==null || r2.getDate()==null)
					return 0;
				return r1.getDate().compareTo(r2.getDate());
			}; 
		});
		List <Report >reportByDoctor = new ArrayList<>();
		for(Report r : c.getReports()){
			if(r.getDesease().equals(desease) && r.getAppointment().getDoctor().equals(d)	)
				reportByDoctor.add(r);
		}
		
		return reportByDoctor;
	}
	
	public void addTreatement(Treatments t){
		em.persist(t);
	}
	
	public void validateTreatement(int treatmentId){
		Treatments t=em.find( Treatments.class,treatmentId);
		t.setValider(1);
		em.merge(t);
	}
	
	public List<Treatments> getAllTreatmentsByReport(int idReport){
		TypedQuery<Report> query=em.createQuery("select r from Report as r where r.id=:r",Report.class);
		Report r=query.setParameter("r", idReport).getSingleResult();
		return r.getTreatments();
	}
	
	public void updateTreatment(Treatments t){
		
		em.merge(t);
	}
	
	public Report getReportByDate(Date d){
		TypedQuery<Report> query=em.createQuery("select r from Report as r where r.date=:date",Report.class);
		Report r=query.setParameter("date", d).getSingleResult();
		return r;
	}

	@Override
	public void addReport(Report r) {
		em.persist(r);
		
	}
}

