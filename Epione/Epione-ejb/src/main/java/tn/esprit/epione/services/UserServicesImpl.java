package tn.esprit.epione.services;

import java.io.Console;
import java.util.List;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import en.esprit.epione.interfaces.UserServices;
import tn.esprit.epione.persistance.User;

@Singleton
public class UserServicesImpl implements UserServices{

	@PersistenceContext(unitName="Epione-ejb")
	EntityManager em ; 
	@Override
	public void addUser (User u) {
		em.persist(u);
		System.out.println("User ajouté:");
		
	}

	@Override
	public void updateUser(User u,int id) {
		User us = em.find(User.class, id);
		us.setFirstName(u.getFirstName());
		System.out.println("user modifié avec succées");
		
	}

	@Override
	public User getUserById(int id) {
		User u = em.find(User.class, id);
		System.out.println("user trouvé:"+u);
		return u;
	}

	@Override
	public void removeUser(int id) {
		User u = em.find(User.class,id);
		em.remove(u);
		System.out.println("succes de suppression");
		
	}

	@Override
	public List<User> getAll() {
		return null;
		// TODO Auto-generated method stub
		
	}

	
}
