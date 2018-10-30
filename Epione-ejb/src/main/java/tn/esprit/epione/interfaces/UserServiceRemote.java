package tn.esprit.epione.interfaces;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Patient;
import tn.esprit.epione.persistance.User;

@Remote
public interface UserServiceRemote {

	
	public int addDoctor(Doctor user);
	public int addPatient(Patient user);
	
	
	public boolean signInWithUsername(String username, String pwd);
	public boolean signInWithEmail(String email, String pwd);
	
	public boolean signOut(User user);
	
	public boolean changePassword(String oldPassword, String newPassword, User u);

	public void forgotPasswordByMail(User user);

	public boolean changeForgotPassword(String token, String newPwd, User user);

	public User findUserById(int id);


	public List<User> getAllUsers();

	public void addUserPhoto(int user_id, String photo);


	public boolean confirmAccount(String token, User u);

	public int isLoggedIn24H();


}
