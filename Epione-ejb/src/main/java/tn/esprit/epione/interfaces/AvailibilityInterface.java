package tn.esprit.epione.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import tn.esprit.epione.persistance.Availibility;
import tn.esprit.epione.persistance.Doctor;

@Local
public interface AvailibilityInterface {
	public void addAvaillibility(Date startDate , Date endDate , int idDoc);
	public List<Availibility> GetAllAvabyDoc(int idDoc);
	public void DeleteAva(int  idAv);
	public void DeleteAvaDoc(int idDoc, String  startDate,String EndDate);
	public List<Availibility> GetAllAvabyByDay(int idDoc ,String  date);
	public List<Availibility> GetAllAvaby(int idDoc);


}

