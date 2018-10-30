package tn.esprit.epione.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.epione.interfaces.AvailibilityInterface;
import tn.esprit.epione.persistance.Appointment;
import tn.esprit.epione.persistance.Availibility;
import tn.esprit.epione.util.Util;

@Stateless
public class AvailibilityService implements AvailibilityInterface {
	@PersistenceContext(unitName="Epione-ejb")
		EntityManager em;

	@SuppressWarnings("deprecation")
	@Override
	public void addAvaillibility(Date startDate, Date endDate, int idDoc) {
		Availibility av = new Availibility(startDate, idDoc);
		
		em.persist(av);
		em.flush();
		
		do {
//			startDate.setMinutes(startDate.getMinutes() + 15);
			Availibility avi = new Availibility(Util.NbrMinutes(startDate, 15), idDoc);
			System.out.println(Util.NbrMinutes(startDate, 15));
			avi.setStartDate(Util.NbrMinutes(startDate, 15));
			startDate = Util.NbrMinutes(startDate, 15);
			em.persist(avi);
			em.flush();

		} while (startDate.before(endDate));
	}

	@Override
	public List<Availibility> GetAllAvabyDoc(int idDoc) {
		List<Availibility> avs = new ArrayList<>();
		TypedQuery<Availibility> query = em.createQuery(
				"select e from Availibility e where e.idDoc=" + idDoc + " ORDER BY e.startDate",
				Availibility.class);
		avs = query.getResultList();
		System.out.println("rendez vous:" + avs.size());
		return avs;
	}

	@Override
	public void DeleteAva(int idAv) {
		em.remove((Availibility)em.find(Availibility.class, idAv));
		
	}

	@Override
	public void DeleteAvaDoc(int idDoc, String  date) {
em.createQuery("Delete  from Availibility e  where e.date BETWEEN '"+date+"' AND '"+date+" 23:59:59'AND e.idDoc="+idDoc  
		).executeUpdate();
	}
	
}
