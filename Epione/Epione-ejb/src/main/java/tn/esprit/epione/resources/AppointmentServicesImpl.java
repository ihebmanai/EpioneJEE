package tn.esprit.epione.resources;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import en.esprit.epione.interfaces.AppointmentIServices;
import tn.esprit.epione.persistance.Appointment;
import tn.esprit.epione.persistance.Patient;

@Stateless
public class AppointmentServicesImpl implements AppointmentIServices {
	
	@PersistenceContext(name="Epione-ejb")
	EntityManager em ; 

	@Override
	public void addAppointment(Appointment a) {
		
		em.persist(a);
		System.out.println("rendez-vous ajouté avec succées ...");
	}

	@Override
	public List<Appointment> getAll() {
		List<Appointment> appointments =null;
		TypedQuery <Appointment> query= em.createQuery("select e from Appointment e ",Appointment.class);
		appointments = query.getResultList();
		System.out.println("rendez vous:"+appointments.size());
		return appointments;
	}

	@Override
	public void updateAppointment(Appointment a) {
		em.merge(a);
		
	}

	@Override
	public void removeAppById(int id) {
		Appointment ap = em.find(Appointment.class, id);
		em.remove(ap);
		System.out.println("Rendez vous supprimé");
	}

	@Override
	public List<Appointment> MyAppointments(int idPatient) {
		Patient pat=em.find(Patient.class, idPatient);
    	TypedQuery<Appointment> query = em.createQuery(" select m from Appointment m "+
    			" where m.patient=:sender "+" and m.state = 1 ",Appointment.class);	
		 List<Appointment> appointments = query.setParameter("sender", pat).getResultList();
		 System.out.println("size of list my appointmtnets"+appointments.size());
		 return appointments;
	}

	@Override
	public List<Appointment> MyRequests(int idPatient) {
		Patient pat=em.find(Patient.class, idPatient);
    	TypedQuery<Appointment> query = em.createQuery(" select m from Appointment m "+
    			" where m.patient=:sender "+" and m.state = 2 ",Appointment.class);	
		 List<Appointment> appointments = query.setParameter("sender", pat).getResultList();
		 System.out.println("size of list my appointmtnets"+appointments.size());
		 return appointments;
	}

	@Override
	public void cancelRequest(int idAppointment) {
		Appointment ap = em.find(Appointment.class, idAppointment);
		em.remove(ap);
		
	}

	@Override
	public List<Appointment>  acceptedRequests(int idPatient) {
		Patient pat=em.find(Patient.class, idPatient);
    	TypedQuery<Appointment> query = em.createQuery(" select m from Appointment m "+
    			" where m.patient=:sender "+" and m.state = 1 ",Appointment.class);	
		 List<Appointment> appointments = query.setParameter("sender", pat).getResultList();
		 System.out.println("size of list my appointmtnets"+appointments.size());
		 return appointments;
		
	}

	@Override
	public Appointment SelectAppByDay(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

}
