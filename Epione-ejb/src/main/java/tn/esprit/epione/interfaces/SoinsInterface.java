package tn.esprit.epione.interfaces;

import javax.ejb.Local;

import tn.esprit.epione.persistance.Soins;

@Local
public interface SoinsInterface {
	public void AddSoins(Soins s);
	public void DeleteSoins(int id );
	

}
