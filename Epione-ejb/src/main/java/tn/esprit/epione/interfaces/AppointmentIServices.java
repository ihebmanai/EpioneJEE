package tn.esprit.epione.interfaces;

import java.text.ParseException;
import java.util.List;

import javax.ejb.Local;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import tn.esprit.epione.persistance.Appointment;
import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.User;

@Local
public interface AppointmentIServices {
	
	//Crud User
		public boolean addAppointment(Appointment a )throws AddressException, MessagingException;
		public boolean updateAppointment(Appointment a);
		public boolean removeAppById(int id);
		public List<Appointment> getAll();
		//Patients Services
		public List<Appointment> MyAppointments(int idPatient);
		public List<Appointment> MyRequests(int idPatient);
		public boolean cancelRequest(int idAppointment);
		public List<Appointment> MyRefusedAppointments(int idPatient);
		// doctor Services
		public List<Appointment> acceptedRequests(int idPatient);
		//
		public List<Appointment> SelectAppByDay(String date) throws ParseException;
		public List<Appointment> SelectAppByDateAndByHour(String date , String hour) throws ParseException; 
		public Appointment consulterApp(int id); 
		 
		public void mailing (String dest,String title,String message) throws AddressException, MessagingException ;
		public Doctor getDoctorById(String med1,String med2);
		public void rememberPatient () throws ParseException;
		public void mailingId (int idRdv) throws AddressException, MessagingException ;
		public List<Appointment> findAppByTitle(String title);
		public void DeleteAutomatique() throws ParseException;
		public List<User> getAllDocotor();
		public List<User> findAppByVille(String ville);
		public User findDocById(int id);
	
}
