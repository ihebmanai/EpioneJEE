package en.esprit.epione.interfaces;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Patient;
import tn.esprit.epione.persistance.Specialist;
import tn.esprit.epione.persistance.User;

@Remote
public interface UserServices {
	
	public void addUser (Object user );
	public void updateUser(Object user) ; 
	public User getUserById(int id);
	public void removeUser(int id);
	public void activateAccount(int id);
	public List<Patient> getAllPatient();
	public List<Doctor> getAllDoctors();
	public List <Specialist> getAllSpecialist();
	List<Specialist> getAllSpecialistByName(String speciality);
	public User authentification(String login , String password);

}
