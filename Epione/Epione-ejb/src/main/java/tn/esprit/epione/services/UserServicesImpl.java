package tn.esprit.epione.services;

import java.io.Console;
import java.util.List;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import en.esprit.epione.interfaces.UserServices;
import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Patient;
import tn.esprit.epione.persistance.Specialist;
import tn.esprit.epione.persistance.User;

@Singleton
public class UserServicesImpl implements UserServices{

	@PersistenceContext(unitName="Epione-ejb")
	EntityManager em ; 
	@Override
	public void addUser (Object user ) {
		if(user instanceof User){
			User u=(User) user;
			em.persist(u);
			System.out.println("User ajouté:");
		}else if(user instanceof Doctor){
			Doctor d=(Doctor) user;
			em.persist(d);
			System.out.println("Doctor ajouté:");
		}else if(user instanceof Specialist){
			Specialist sp=(Specialist) user;
			em.persist(sp);
			System.out.println("Specialist ajouté:");
		}else if (user instanceof Patient){
			Patient p =(Patient) user;
			em.persist(p);
			System.out.println("Patient ajouté:");
		}else

			System.out.println("Error we can't add this user");
		
		
	}

	@Override
	public void updateUser(Object user) {
		if(user instanceof User){
			User a=(User) user;
			User us = em.find(User.class, a.getId());
			us=a;
			em.merge(us);
			System.out.println("User ajouté:");
		}else if(user instanceof Doctor){
			Doctor a=(Doctor) user;
			Doctor us = em.find(Doctor.class, a.getId());
			us=a;
			em.merge(us);
		}else if(user instanceof Specialist){
			Specialist a=(Specialist) user;
			Specialist us = em.find(Specialist.class, a.getId());
			us=a;
			em.merge(us);
		}else if (user instanceof Patient){
			Patient a=(Patient) user;
			Patient us = em.find(Patient.class, a.getId());
			us=a;
			em.merge(us);
		}else

			System.out.println("user modifié avec succées");
		
		
		
		
		
	}

	@Override
	public User getUserById(int id) {
		User user = em.find(User.class, id);
		System.out.println("user trouvé:"+user);
		return user;
	}

	@Override
	public void removeUser(int id) {
		User u = em.find(User.class,id);
		em.remove(u);
		System.out.println("succes de suppression");
		
	}

	@Override
	public List<Patient> getAllPatient() {
		TypedQuery<Patient> query=em.createQuery("select p from Patient as p where p.userType =:type",Patient.class);
		return (List<Patient>) query.setParameter("type", "pat").getResultList();		
	}
	public void activateAccount(int id){
		
		if(em.find(Doctor.class, id) != null){
			Doctor doctor=em.find(Doctor.class, id);
			doctor.setFlag(1);
		}else{
			System.out.println("undone");
		}
		
	}



	@Override
	public List<Doctor> getAllDoctors() {
		TypedQuery<Doctor> query=em.createQuery("select d from Doctor as d where d.userType =:type",Doctor.class);
		return (List<Doctor>) query.setParameter("type", "doc").getResultList();
	}

	@Override
	public List<Specialist> getAllSpecialistByName(String speciality) {
		TypedQuery<Specialist> query=em.createQuery("select sp from Specialist as sp where sp.userType =:type and sp.speciality =:speciality",Specialist.class);
		return (List<Specialist>) query.setParameter("type", "specialist").setParameter("speciality", speciality).getResultList();
	
	}
	public List<Specialist> getAllSpecialist() {
		TypedQuery<Specialist> query=em.createQuery("select sp from Specialist as sp where sp.userType =:type",Specialist.class);
		return (List<Specialist>) query.setParameter("type", "specialist").getResultList();
	
	}
	public User authentification(String login , String password){
		TypedQuery<User> query=em.createQuery("select user from User as user where user.login =:login and user.password =:password",User.class);
		User user = query.setParameter("login", login).setParameter("password", password).getSingleResult();
		return user;
	}




	
}
