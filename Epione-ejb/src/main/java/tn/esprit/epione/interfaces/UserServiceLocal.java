package tn.esprit.epione.interfaces;

import java.util.List;

import javax.ejb.Local;

import tn.esprit.epione.persistance.Administrator;
import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Patient;
import tn.esprit.epione.persistance.User;

@Local
public interface UserServiceLocal {

	
public User getUserById(int idUser);
	
	public int addDoctor(Doctor user);
	public int addPatient(Patient user);
	public int addAdministrator(Administrator user);
	
	public boolean signInWithUsername(String username, String pwd);
	public boolean signInWithEmail(String email, String pwd);
	
	public boolean signOut(User user);
	
	public boolean changePassword(String oldPassword, String newPassword, User u);

	public boolean forgotPasswordByMail(int idUser);

	public boolean changeForgotPassword(int idUser, String token, String newPwd);

	public User findUserById(int id);
	public User findUserByEmail(String email);

	public List<User> getAllUsers();

	public void addUserPhoto(int user_id, String photo);

	public boolean banAccount(User u);


	public boolean confirmAccount(String token, User u);

	public int isLoggedIn24H();

	public List<Doctor> ListDoctor();

	public List<Patient> ListPatient();


}
