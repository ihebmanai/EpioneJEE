package tn.esprit.epione.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import tn.esprit.epione.persistance.Availibility;
import tn.esprit.epione.persistance.Report;

@Local
public interface AnalyticsInterface {
	
	public long getAllPatientsTreated();
	public long getAllPatientsTreatedByDoctor(int doctorId);
	public long getAllPatientsTreatedByDate(Date dateOne,Date dateTwo);
	long getCanceledRequest();
	long getAcceptedRequest();
	public List<Availibility> GetAllAvabyDoc(int idDoc);



}
