package en.esprit.epione.interfaces;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.epione.persistance.Patient;

@Remote
public interface PatientServices {

	public void addPatient(Patient p);
	public void updatePatient(Patient p);
	public void removePatient(int id);
	public List<Patient> getAll();
}
