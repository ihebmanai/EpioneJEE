package tn.esprit.epione.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.epione.interfaces.PatientServices;
import tn.esprit.epione.persistance.Patient;

@LocalBean
@Stateless
public class PatientServicesImpl implements PatientServices {

	@PersistenceContext(name="Epione-ejb")
	EntityManager em ;
	
	@Override
	public void addPatient(Patient p) {
		em.persist(p);
		System.out.println("patient ajouté");
		
	}

	@Override
	public void updatePatient(Patient p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePatient(int id) {
		Patient p = em.find(Patient.class, id);
		em.persist(p);
		System.out.println("patient suprimé");
		
	}

	@Override
	public List<Patient> getAll() {
		TypedQuery<Patient> query = em.createQuery("select u from Patient u ",Patient.class);	
		 List<Patient> patients = query.getResultList();
		 System.out.println(patients);
		 return patients;
		
	}

	
}
