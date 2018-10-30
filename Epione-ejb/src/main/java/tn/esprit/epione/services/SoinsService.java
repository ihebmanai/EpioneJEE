package tn.esprit.epione.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tn.esprit.epione.interfaces.SoinsInterface;
import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Soins;

@Stateless
public class SoinsService  implements SoinsInterface{
	@PersistenceContext(unitName="Epione-ejb")
	EntityManager em;

	@Override
	public void AddSoins(Soins s) {
	 em.persist(s);
 		
	}

	@Override
	public void DeleteSoins(int id) {
em.remove(em.find(Soins.class, id));		
	}
	

}
