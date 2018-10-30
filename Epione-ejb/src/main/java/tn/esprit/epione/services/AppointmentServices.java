package tn.esprit.epione.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import tn.esprit.epione.interfaces.AppointmentInterface;
import tn.esprit.epione.persistance.Appointment;
import tn.esprit.epione.persistance.Notification;
import tn.esprit.epione.persistance.State;

@Stateless
public class AppointmentServices implements AppointmentInterface {
	@PersistenceContext(unitName = "Epione-ejb")
	EntityManager em;

	@Override
	public List<Appointment> GetAppointmentByDoc(int idDoc) {
		List<Appointment> appointments = new ArrayList<>();
		TypedQuery<Appointment> query = em.createQuery(
				"select e from Appointment e where e.doctor=" + idDoc + " ORDER BY e.date_appointment",
				Appointment.class);
		appointments = query.getResultList();
		System.out.println("rendez vous:" + appointments.size());
		return appointments;
	}

	@Override
	public void AcceptAppointment(int idApp) {
		Appointment a = em.find(Appointment.class, idApp);
		a.setState(State.accepeted);
		em.merge(a);
		Notification notif=new Notification();
		notif.setDoctor(a.getDoctor());
		notif.setPatient(a.getPatient());
		notif.setTitle("Accpeted Appointment");
		notif.setObject("Mr/Mm " +a.getPatient().getFirstName()+"  You Have an Appointment with Doctor  "+a.getDoctor().getFirstName()+"  at "+ a.getDate_appointment());
		em.persist(notif);
	}

	@Override
	public void RefuseAppointment(int idApp) {
		Appointment a = em.find(Appointment.class, idApp);
		a.setState(State.refused);
		em.merge(a);
		Notification notif=new Notification();
		notif.setDoctor(a.getDoctor());
		notif.setPatient(a.getPatient());
		notif.setTitle("Refused Appointment");
		notif.setObject("Mr/Mm " +a.getPatient().getFirstName()+"  Your  Appointment with Doctor  "+a.getDoctor().getFirstName()+"  at "+ a.getDate_appointment()+" has beed Refused");
		em.persist(notif);
	}

	@Override
	public List<Appointment> DailyProgram(int idDoc) {
		List<Appointment> appointments = new ArrayList<>();
		java.util.Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(date);
		TypedQuery<Appointment> query = em.createQuery(

				"select e from Appointment e where e.doctor=" + idDoc + " and e.date_appointment BETWEEN '" + format
						+ "' AND '" + format + " 23:59:59'ORDER BY e.date_appointment",
				Appointment.class);
		appointments = query.getResultList();
		System.out.println("rendez vous:" + appointments.size());
		return appointments;

	}

	@Override
	public List<Appointment> GetAppointmentRequest(int idDoc) {
		List<Appointment> appointments = new ArrayList<>();
		TypedQuery<Appointment> query = em.createQuery(
				"select e from Appointment e where e.doctor=" + idDoc + " and e.state=1 ORDER BY e.date_appointment",
				Appointment.class);
		appointments = query.getResultList();
		System.out.println("rendez vous:" + appointments.size());
		return appointments;
		
	}

}
