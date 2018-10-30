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
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import tn.esprit.epione.interfaces.AnalyticsInterface;
import tn.esprit.epione.persistance.Appointment;
import tn.esprit.epione.persistance.Course;
import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Patient;
import tn.esprit.epione.persistance.Report;

@LocalBean
@Stateless
public class AnalyticsServices implements AnalyticsInterface{

	@PersistenceContext(name="Epione-ejb")
	EntityManager em ; 
	
	@Override
	public long getAllPatientsTreated() {
		long nbr=-1;
		Query query=em.createQuery("SELECT count(p.id) FROM Patient as p where p.treated=:nbr");
		query.setParameter("nbr", 1);
		nbr=(long) query.getSingleResult();
		return nbr;
	}

	@Override
	public long getAllPatientsTreatedByDoctor(int doctorId) {
		Doctor d=em.find(Doctor.class, doctorId);
		long nbr = 0;
		if( d==null)
			return nbr;
		TypedQuery<Appointment> query=em.createQuery("select a from Appointment as a where a.doctor=:d ",Appointment.class);
		ArrayList<Appointment> appointementsList= (ArrayList<Appointment>) query.setParameter("d", d).getResultList();

		
		for (Appointment a:appointementsList)
		{
			if(a.getPatient().getTreated()==1)
			{
				nbr++;
			}
		}
		return nbr;
	}

	@Override
	public long getAllPatientsTreatedByDate(Date dateOne, Date dateTwo) {
		long nbr = 0;
		if( dateOne==null || dateTwo==null)
			return nbr;
		TypedQuery<Appointment> query=em.createQuery("select a from Appointment as a where a.date_appointment BETWEEN :dOne and :dTwo ",Appointment.class);
		ArrayList<Appointment> appointementsList= (ArrayList<Appointment>) query.setParameter("dOne", dateOne).setParameter("dTwo", dateTwo).getResultList();

		
		for (Appointment a:appointementsList)
		{
			if(a.getPatient().getTreated()==1)
			{
				nbr++;
			}
		}
		return nbr;
	}
	
	

}
