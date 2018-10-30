package tn.esprit.epione.interfaces;

import java.util.List;

import javax.ejb.Local;
import tn.esprit.epione.persistance.Patient;

@Local
public interface PatientServices {

	public void addPatient(Patient p);
	public void updatePatient(Patient p);
	public void removePatient(int id);
	public List<Patient> getAll();
}
