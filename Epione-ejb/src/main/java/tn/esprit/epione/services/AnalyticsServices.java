package tn.esprit.epione.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import tn.esprit.epione.interfaces.AnalyticsInterface;
import tn.esprit.epione.persistance.Appointment;
import tn.esprit.epione.persistance.Availibility;
import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Patient;
import tn.esprit.epione.persistance.State;

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
		Set<Patient> hs = new HashSet<>();
		for (Appointment a:appointementsList)
		{
			hs.add(a.getPatient());

		}
		for (Patient p:hs)
		{
			if(p.getTreated()==1)
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
		Set<Patient> hs = new HashSet<>();
		for (Appointment a:appointementsList)
		{
			hs.add(a.getPatient());

		}

		for (Patient p:hs)
		{
			if(p.getTreated()==1)
			{
				nbr++;
			}
		}
		return nbr;
	}
	

	
	@Override
	public long getCanceledRequest() {
		long nbr = 0;
		long nbrAll = 0;
		long nbrCanceled = 0;

		Query query=em.createQuery("select count(a.endHour) from Appointment as a");
		nbrAll=(long) query.getSingleResult();
		
		Query query2=em.createQuery("select count(a.endHour) from Appointment as a where a.state = 0");
		nbrCanceled=(long) query2.getSingleResult();
		
		nbr=(nbrCanceled *100) / nbrAll;
		
		return nbr;
	}
	
	@Override
	public long getAcceptedRequest() {
		long nbr = 0;
		long nbrAll = 0;
		long nbrCanceled = 0;

		Query query=em.createQuery("select count(a.endHour) from Appointment as a");
		nbrAll=(long) query.getSingleResult();
		
		Query query2=em.createQuery("select count(a.endHour) from Appointment as a where a.state = 1");
		nbrCanceled=(long) query2.getSingleResult();
		
		nbr=(nbrCanceled *100) / nbrAll;
		
		return nbr;
	}
	
	@Override
	public List<Availibility> GetAllAvabyDoc(int idDoc) {
		List<Availibility> avs = new ArrayList<>();
		TypedQuery<Availibility> query = em.createQuery(
				"select e from Availibility e where e.idDoc=" + idDoc + " ORDER BY e.date",
				Availibility.class);
		avs = query.getResultList();
		System.out.println("rendez vous:" + avs.size());
		return avs;
	}
	
	
	
	

}
