package en.esprit.epione.interfaces;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.epione.persistance.User;

@Remote
public interface UserServices {
	
	public void addUser (User u ); 
	public void updateUser(User u,int id) ; 
	public User getUserById(int id);
	public void removeUser(int id);
	public List<User> getAll();

}
