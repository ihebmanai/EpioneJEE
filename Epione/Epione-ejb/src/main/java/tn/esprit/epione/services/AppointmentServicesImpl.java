package tn.esprit.epione.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import en.esprit.epione.interfaces.AppointmentIServices;
import tn.esprit.epione.persistance.Appointment;

@Stateless
public class AppointmentServicesImpl implements AppointmentIServices {
	
	@PersistenceContext(name="Epione-ejb")
	EntityManager em ; 

	@Override
	public void addAppointment(Appointment a) {
		
		em.persist(a);
		System.out.println("rendez-vous ajouté avec succées...");
	}

	@Override
	public List<Appointment> getAll() {
		List<Appointment> appointments =null;
		TypedQuery <Appointment> query= em.createQuery("select e from appointment e ",Appointment.class);
		appointments = query.getResultList();
		System.out.println("rendez vous:"+appointments.size());
		return appointments;
	}

	@Override
	public void updateAppointment(Appointment a, int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAppById(int id) {
		Appointment ap = em.find(Appointment.class, id);
		em.remove(ap);
		System.out.println("Rendez vous supprimé");
		
	}

}
