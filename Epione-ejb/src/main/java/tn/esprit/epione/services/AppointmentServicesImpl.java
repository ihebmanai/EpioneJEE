package tn.esprit.epione.services;

import java.security.Security;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.print.Doc;
import javax.transaction.Transactional;

import com.sun.mail.smtp.SMTPTransport;

import tn.esprit.epione.interfaces.AppointmentIServices;
import tn.esprit.epione.persistance.Appointment;
import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Patient;
import tn.esprit.epione.persistance.State;
import tn.esprit.epione.persistance.User;


@Startup
@LocalBean
@Stateless
public class AppointmentServicesImpl implements AppointmentIServices {
	
	@PersistenceContext(name="Epione-ejb")
	EntityManager em ; 
	@Schedules({
		@Schedule(dayOfWeek = "Sat", hour = "12"),
		@Schedule(dayOfWeek="Wed", hour="12"),
		@Schedule(dayOfWeek="Mon",hour="12")
		})
	public void RememberOurPatient() throws AddressException, MessagingException, ParseException{
		rememberPatient();
		System.out.println("email Thread");
	}
	
	@PostConstruct
	@Schedule(hour = "*", minute = "*", second = "*/30", persistent = false)
	public void doPeriodicCleanup() throws ParseException { 
	/*	
		List<Appointment> list_app = new ArrayList<Appointment>();
		System.out.println("thread 2 s'execute"+list_app);
		rememberPatient();
	*/
		
	}

	

	@Override
	public List<Appointment> getAll() {
		
		List<Appointment> appointments = new ArrayList<>();
		TypedQuery <Appointment> query= em.createQuery("select e from Appointment e ",Appointment.class);
		appointments = query.getResultList();
		System.out.println("rendez vous:"+appointments.size());
		return appointments ;
	}

	@Override
	public boolean updateAppointment(Appointment a)
	{
		if(a!=null) {
		Appointment ap = em.find(Appointment.class,a.getId());
		ap = a ; 
		em.merge(ap);
		System.out.println("Appointment Updated");
		return true;
		}
		return false ;
		
	}

	@Override
	public boolean removeAppById(int id) {
		
		Appointment ap = em.find(Appointment.class, id);
		if(ap!=null) {
		em.remove(ap);
		System.out.println("Appointment deleted");
		return true;
		}
		return false;
	}

	@Override
	public List<Appointment> MyAppointments(int idPatient) {
		Patient pat=em.find(Patient.class, idPatient);
    	TypedQuery<Appointment> query = em.createQuery(" select m from Appointment m "+
    			" where m.patient=:sender "+" and m.state = 1 ",Appointment.class);	
		 List<Appointment> appointments = query.setParameter("sender", pat).getResultList();
		 System.out.println("size of list my appointmtnets"+appointments.size());
		 return appointments;
	}

	@Override
	public List<Appointment> MyRequests(int idPatient) {
		Patient pat=em.find(Patient.class, idPatient);
    	TypedQuery<Appointment> query = em.createQuery(" select m from Appointment m "+
    			" where m.patient=:sender "+" and m.state = 2 ",Appointment.class);	
		 List<Appointment> appointments = query.setParameter("sender", pat).getResultList();
		 System.out.println("size of list my appointmtnets"+appointments.size());
		 return appointments;
	}

	@Override
	public void cancelRequest(int idAppointment) {
		
		Appointment ap = em.find(Appointment.class, idAppointment);
		em.remove(ap);
		
	}

	@Override
	public List<Appointment>  acceptedRequests(int idPatient) {
		Patient pat=em.find(Patient.class, idPatient);
    	TypedQuery<Appointment> query = em.createQuery(" select m from Appointment m "+
    			" where m.patient=:sender "+" and m.state = 1 ",Appointment.class);	
		 List<Appointment> appointments = query.setParameter("sender", pat).getResultList();
		 System.out.println("size of list my appointmtnets"+appointments.size());
		 return appointments;
		
	}

	@Override
	public List<Appointment> SelectAppByDay(String date1) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(date1);
		System.out.println(date);
		TypedQuery<Appointment> query = em.createQuery(" select m from Appointment m "+
    			" where m.date_appointment=:date "+" and m.state = 1" ,Appointment.class);
		List<Appointment> appointments = query.setParameter("date", date).getResultList();
		System.out.println("size of list my appointmtnets"+appointments.size());
		return appointments;
	}

	@Override
	public List<Appointment> SelectAppByDateAndByHour(String date1, String hour) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(date1);
		System.out.println(date);
		TypedQuery<Appointment> query = em.createQuery(" select m from Appointment m "+
    			" where m.date_appointment=:date "+" and m.start_hour=:hour" ,Appointment.class);
						
		query.setParameter("date", date); 
		query.setParameter("hour", hour); 
		List<Appointment> a  =null;
				a=query.getResultList();
		return a;
	}

	@Override
	public Appointment consulterApp(int id) {
		TypedQuery<Appointment> query = em.createQuery(" select m from Appointment m "+
    			" where m.id=:id " ,Appointment.class);
		query.setParameter("id", id);
		Appointment a = query.getSingleResult();
		
		return a;
	}



	@Override
	public void mailing(String email_dest,String title,String message) throws AddressException, MessagingException {

		
		        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

		        // Get a Properties object
		        Properties props = System.getProperties();
		        props.setProperty("mail.smtps.host", "smtp.gmail.com");
		        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		        props.setProperty("mail.smtp.socketFactory.fallback", "false");
		        props.setProperty("mail.smtp.port", "465");
		        props.setProperty("mail.smtp.socketFactory.port", "465");
		        props.setProperty("mail.smtps.auth", "true");

		        /*
		        If set to false, the QUIT command is sent and the connection is immediately closed. If set 
		        to true (the default), causes the transport to wait for the response to the QUIT command.

		        ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
		                http://forum.java.sun.com/thread.jspa?threadID=5205249
		                smtpsend.java - demo program from javamail
		        */
		        props.put("mail.smtps.quitwait", "false");

		        Session session = Session.getInstance(props, null);

		        // -- Create a new message --
		        final MimeMessage msg = new MimeMessage(session);

		        // -- Set the FROM and TO fields --
		        msg.setFrom(new InternetAddress("econsulting588"+ "@gmail.com"));
		        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email_dest, false));

		        msg.setSubject(title);
		        msg.setText(message, "utf-8");
		        msg.setSentDate(new Date());

		        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

		        t.connect("smtp.gmail.com", "econsulting588@gmail.com", "hexagone10");
		        t.sendMessage(msg, msg.getAllRecipients());      
		        t.close();
		}

	@Override
	public Doctor getDoctorById(String med1,String med2) {
		TypedQuery<Doctor> query = em.createQuery(" select m from Doctor m "+
    			" where m.firstName=:med1 "+" and m.lastName =:med2" ,Doctor.class);
			query.setParameter("med1", med1); 
			query.setParameter("med2", med2); 
			Doctor doc = query.getSingleResult();
		System.out.println("medecin trouvé=====>"+doc.toString());
		return doc;
	}
	
	@Override
	public boolean addAppointment(Appointment a) throws AddressException, MessagingException {
		// test sur laa date du rendez vous
		if(a!= null ) {
			
		Doctor doc = em.find(Doctor.class, a.getDoctor().getId());
		Patient pat  = em.find(Patient.class,a.getPatient().getId());
		a.setState(State.running);
		
		em.persist(a);
		//processus du mailing du patient & de doctor
		mailing(doc.getEmail(),a.getTitle(),a.getMessage());
		mailing(pat.getEmail(),"Epione","Votre demande a été enregistré");
		
		return true;
		
		}
		return false;
	}
	
	@Override
	public void rememberPatient() throws ParseException {
		// date d'ajourdh'hui - 3  java 8 library
		LocalDate today = LocalDate.now().minusDays(3);
		DateFormat formatter1;
		formatter1 = new SimpleDateFormat("dd-MMM-yy");
		java.util.Date d = new SimpleDateFormat("yyyy-MM-dd").parse(today.toString());
		
		System.out.println("date=====>"+today);
		
		//	d = new SimpleDateFormat("yyyy-MM-dd").parse(today.toString());
		

		Query q = em.createQuery("select o from Appointment o"
				+ " where o.date_appointment  >= :today ");
		q.setParameter("today",d);
		List<Appointment> apps =q.getResultList();
		for (Appointment appointment : apps) {
			Patient patient = em.find(Patient.class, appointment.getPatient().getId());
			System.out.println("le patient:=======>"+appointment.getPatient().getId());
			try {
				mailing(patient.getEmail(), "baba Ali", "Thread executing");
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Remember=====>"+q.getResultList().size()+"patient Ayant un rendez vous dans 3 jours");
		}

	@Override
	public void mailingId(int idRdv) throws AddressException, MessagingException {
		Appointment ap = em.find(Appointment.class, idRdv); 
		Patient patient = em.find(Patient.class, ap.getPatient().getId()); 
		System.out.println("patient"+patient);
		
		
		 Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	        	
	        // Get a Properties object
	        Properties props = System.getProperties();
	        props.setProperty("mail.smtps.host", "smtp.gmail.com");
	        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
	        props.setProperty("mail.smtp.socketFactory.fallback", "false");
	        props.setProperty("mail.smtp.port", "465");
	        props.setProperty("mail.smtp.socketFactory.port", "465");
	        props.setProperty("mail.smtps.auth", "true");

	        /*
	        If set to false, the QUIT command is sent and the connection is immediately closed. If set 
	        to true (the default), causes the transport to wait for the response to the QUIT command.

	        ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
	                http://forum.java.sun.com/thread.jspa?threadID=5205249
	                smtpsend.java - demo program from javamail
	        */
	        props.put("mail.smtps.quitwait", "false");

	        Session session = Session.getInstance(props, null);

	        // -- Create a new message --
	        final MimeMessage msg = new MimeMessage(session);

	        // -- Set the FROM and TO fields --
	        msg.setFrom(new InternetAddress("econsulting588"+ "@gmail.com"));
	        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(patient.getEmail(), false));

	        msg.setSubject(ap.getObject());
	        msg.setText(ap.getMessage(), "utf-8");
	        msg.setSentDate(new Date());

	        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

	        t.connect("smtp.gmail.com", "econsulting588@gmail.com", "hexagone10");
	        t.sendMessage(msg, msg.getAllRecipients());      
	        t.close();
		
	}
	
	

	

}
