package tn.esprit.epione.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import tn.esprit.epione.interfaces.UserServiceLocal;
import tn.esprit.epione.persistance.Administrator;
import tn.esprit.epione.persistance.Course;
import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Patient;
import tn.esprit.epione.persistance.User;
import tn.esprit.epione.util.SendConfirmationMail;
import tn.esprit.epione.util.Util;

@Stateless
@LocalBean
public class UserService implements UserServiceLocal {

	@PersistenceContext(unitName = "Epione-ejb")
	EntityManager em;

	@Override
	public int addDoctor(Doctor user) {// SignUP
		String crypted_pwd = encrypt(user.getPassword());
		user.setPassword(crypted_pwd);
		em.persist(user);
		em.flush();
		return user.getId();
	}

	@Override
	public int addPatient(Patient user) {// SignUP
		String crypted_pwd = encrypt(user.getPassword());
		user.setPassword(crypted_pwd);
		Course c = new Course();
		
		em.persist(user);
		em.flush();
		c.setPatient(user);
		em.persist(c);
		em.flush();
		user.setCourse(c);
		em.merge(user);
		return user.getId();
	}

	@Override
	public int addAdministrator(Administrator user) {// SignUP
		String crypted_pwd = encrypt(user.getPassword());
		user.setPassword(crypted_pwd);
		em.persist(user);
		em.flush();
		return user.getId();
	}

	@Override
	public User getUserById(int idUser) {
		return em.find(User.class, idUser);
	}

	@Override
	public boolean signInWithUsername(String username, String pwd) {
		return signIn("username", username, pwd);
	}

	@Override
	public boolean signInWithEmail(String email, String pwd) {
		return signIn("email", email, pwd);
	}

	@Override
	public boolean signOut(User user) {
		User u = em.find(User.class, user.getId());
		em.merge(u);
		return true;
	}

	@Override
	public boolean confirmAccount(String token, User user) {// Activate account

		User u = em.find(User.class, user.getId());

		String token_db = u.getToken();
		System.out.println(token_db);
		if (token.equals(token_db)) {
			
			em.merge(u);
			System.out.println("token is valid ! Account is confirmed.");
			return true;
		} else {
			System.out.println("token not valid");
			return false;
		}
	}

	@Override
	public boolean changePassword(String oldPassword, String newPassword, User u) {
		User user = em.find(User.class, u.getId());
		String decrypted = decrypt(oldPassword);
		if (decrypted.equals(user.getPassword())) {
			String crypted_new = encrypt(newPassword);
			user.setPassword(crypted_new);
			em.merge(user);
			System.out.println("Password changed: old pwd is " + oldPassword + " new password is " + newPassword);
			return true;
		} else {
			System.out.println("password is wrong");
			return false;
		}
	}

	@Override
	public void forgotPasswordByMail(User user) {
		String token = getSaltString();
		String mail_content = "Enter the token generated and have the oppotunity to change your password -->" + token;
		SendConfirmationMail.sendMail("epioneservice@gmail.com", "epione2018", user.getEmail(), "forgot password",
				mail_content);
		User u = em.find(User.class, user.getId());
		u.setToken(token);
		em.merge(u);
	}

	@Override
	public boolean changeForgotPassword(String token, String newPwd, User user) {
		User u = em.find(User.class, user.getId());
		String crypted = encrypt(newPwd);
		if (isTokenValid(token, user.getId())) {
			u.setPassword(crypted);
			em.merge(u);
			return true;
		}
		return false;
	}

	@Override
	public User findUserById(int id) {
		User u = em.find(User.class, id);
		return u;
	}

	@Override
	public List<User> getAllUsers() {
		Query q = em.createQuery("select u from User u");
		List<User> l = q.getResultList();
		return l;
	}


//return the nbr of users that were connected in the last 24h
	@Override
	public int isLoggedIn24H() {
//
		List<User> all = getAllUsers();
		int total = 0;
		for (User u : all) {
			Date loggedin = u.getLastLogin();
			Date dt = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(dt);
			c.add(Calendar.DATE, -1);
			dt = c.getTime();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Query query = em.createNativeQuery("SELECT DATEDIFF(?1,?2)");
			query.setParameter("1", loggedin);
			query.setParameter("2", dateFormat.format(dt));
			System.out.println(query.getSingleResult().toString());
			int difference = Integer.parseInt(query.getSingleResult().toString());
			if (difference <= 1 && difference > 0) {
				total = total + 1;
			}
		}
		return total;
	}

	@Override
	public void addUserPhoto(int user_id, String photo) {
		User u = em.find(User.class, user_id);
		u.setPhoto(photo);
		em.merge(u);
	}

	// ****************** PRIVATE METHODS ***************************

	private boolean signIn(String fieldName, String fieldValue, String pwd) {
		String crypted_pwd = encrypt(pwd);

		Query tq = em
				.createQuery("select u FROM User u WHERE u." + fieldName + "=:fieldValue AND u.password=:password");
		tq.setParameter("fieldValue", fieldValue.trim());
		tq.setParameter("password", crypted_pwd);
		List<User> userlst = tq.getResultList();

		if ((userlst == null) || (userlst.isEmpty())) {
			return false;
		}
		User user = userlst.get(0);

		if (user != null) {
			User u = em.find(User.class, user.getId());
			u.setLastLogin(Util.getDateNowUTC());
			em.merge(u);
			return true;
		}
		return false;
	}

	private String encrypt(String password) {
		String crypt = "";
		for (int i = 0; i < password.length(); i++) {
			int c = password.charAt(i) ^ 48;
			crypt = crypt + (char) c;
		}
		return crypt;
	}

	private String decrypt(String password) {
		String aCrypter = "";
		for (int i = 0; i < password.length(); i++) {
			int c = password.charAt(i) ^ 48;
			aCrypter = aCrypter + (char) c;
		}
		return aCrypter;
	}

	private String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 10) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

	private boolean isTokenValid(String token, int id) {
		User u = em.find(User.class, id);

		String token_db = u.getToken();
		System.out.println(token_db);
		if (token.equals(token_db)) {
			System.out.println("token is valid !");
			return true;
		} else {
			System.out.println("token not valid");
			return false;
		}

	}

}
