package tn.esprit.epione.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


import tn.esprit.epione.interfaces.AppointmentInterface;
import tn.esprit.epione.persistance.Appointment;
import tn.esprit.epione.persistance.Notification;
import tn.esprit.epione.persistance.State;

@Stateless
public class AppointmentServices implements AppointmentInterface {
	@PersistenceContext(unitName = "Epione-ejb")
	EntityManager em;

	@Override
	public List<Appointment> GetAppointmentByDoc(int idDoc) {
		List<Appointment> appointments = new ArrayList<>();
		TypedQuery<Appointment> query = em.createQuery(
				"select e from Appointment e where e.doctor=" + idDoc + " AND e.state=0 ORDER BY e.date_appointment",
				Appointment.class);
		appointments = query.getResultList();
		System.out.println("rendez vous:" + appointments.size());
		return appointments;
	}

	@Override
	public void AcceptAppointment(int idApp) {
		Appointment a = em.find(Appointment.class, idApp);
		a.setState(State.accepeted);
		em.merge(a);
		Notification notif=new Notification();
		notif.setDoctor(a.getDoctor());
		notif.setPatient(a.getPatient());
		notif.setTitle("Accpeted Appointment");
		notif.setObject("Mr/Mm " +a.getPatient().getFirstName()+"  You Have an Appointment with Doctor  "+a.getDoctor().getFirstName()+"  at "+ a.getDate_appointment());
		em.persist(notif);
	}

	@Override
	public void RefuseAppointment(int idApp) {
		Appointment a = em.find(Appointment.class, idApp);
		a.setState(State.refused);
		em.merge(a);
		Notification notif=new Notification();
		notif.setDoctor(a.getDoctor());
		notif.setPatient(a.getPatient());
		notif.setTitle("Refused Appointment");
		notif.setObject("Mr/Mm " +a.getPatient().getFirstName()+"  Your  Appointment with Doctor  "+a.getDoctor().getFirstName()+"  at "+ a.getDate_appointment()+" has beed Refused");
		em.persist(notif);
	}

	@Override
	public List<Appointment> DailyProgram(int idDoc) {
		List<Appointment> appointments = new ArrayList<>();
		java.util.Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(date);
		TypedQuery<Appointment> query = em.createQuery(

				"select e from Appointment e where e.doctor=" + idDoc + " and e.date_appointment BETWEEN '" + format
						+ "' AND '" + format + " 23:59:59'ORDER BY e.date_appointment",
				Appointment.class);
		appointments = query.getResultList();
		System.out.println("rendez vous:" + appointments.size());
		return appointments;

	}

	@Override
	public List<Appointment> GetAppointmentRequest(int idDoc) {
		List<Appointment> appointments = new ArrayList<>();
		TypedQuery<Appointment> query = em.createQuery(
				"select e from Appointment e where e.doctor=" + idDoc + " and e.state=1 ORDER BY e.date_appointment",
				Appointment.class);
		appointments = query.getResultList();
		System.out.println("rendez vous:" + appointments.size());
		return appointments;
		
	}

	@Override
	public Appointment GetAppointmentyId(int id) {
		return em.find(Appointment.class, id);
	}

	@Override
	public List<Appointment> GetAllAppointment(int idDoc) {
		List<Appointment> appointments = new ArrayList<>();
		TypedQuery<Appointment> query = em.createQuery(
				"select e from Appointment e where e.doctor=" + idDoc + "  ORDER BY e.date_appointment",
				Appointment.class);
		appointments = query.getResultList();
		System.out.println("rendez vous:" + appointments.size());
		return appointments;
	}
	

	/*
	 * private static final String APPLICATION_NAME =
	 * "Google Calendar API Java Quickstart"; private static final JsonFactory
	 * JSON_FACTORY = JacksonFactory.getDefaultInstance(); private static final
	 * String TOKENS_DIRECTORY_PATH = "tokens";
	 */

	/**
	 * Global instance of the scopes required by this quickstart. If modifying these
	 * scopes, delete your previously saved tokens/ folder.
	 */
	/*
	 * private static final List<String> SCOPES =
	 * Collections.singletonList(CalendarScopes.CALENDAR); private static final
	 * String CREDENTIALS_FILE_PATH = "/credentials.json";
	 */

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException If the credentials.json file cannot be found.
	 */
	/*
	 * private static Credential getCredentials(final NetHttpTransport
	 * HTTP_TRANSPORT) throws IOException { // Load client secrets. InputStream in =
	 * AppointmentServices.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
	 * GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
	 * new InputStreamReader(in));
	 * 
	 * // Build flow and trigger user authorization request.
	 * GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	 * HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES) .setDataStoreFactory(new
	 * FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
	 * .setAccessType("offline") .build(); LocalServerReceiver receier = new
	 * LocalServerReceiver.Builder().setPort(8888).build(); return new
	 * AuthorizationCodeInstalledApp(flow, receier).authorize("user"); }
	 * 
	 * public void SycroCalander(Appointment a) throws IOException,
	 * GeneralSecurityException { // Build a new authorized API client service.
	 * final NetHttpTransport HTTP_TRANSPORT =
	 * GoogleNetHttpTransport.newTrustedTransport(); Calendar service = new
	 * Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,
	 * getCredentials(HTTP_TRANSPORT)) .setApplicationName(APPLICATION_NAME)
	 * .build();
	 * 
	 * // List the next 10 events from the primary calendar. Event event = new
	 * Event() .setSummary("Google I/O 2015")
	 * .setLocation("800 Howard St., San Francisco, CA 94103")
	 * .setDescription("A chance to hear more about Google's developer products.");
	 * 
	 * DateTime startDateTime = new DateTime(a.getDate_appointment()); EventDateTime
	 * start = new EventDateTime() .setDateTime(startDateTime)
	 * .setTimeZone("America/Los_Angeles"); event.setStart(start); Date
	 * d=a.getDate_appointment(); d.setMinutes(d.getMinutes()+15);
	 * 
	 * DateTime endDateTime = new DateTime(d); EventDateTime end = new
	 * EventDateTime() .setDateTime(endDateTime)
	 * .setTimeZone("America/Los_Angeles"); event.setEnd(end);
	 * 
	 * String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
	 * event.setRecurrence(Arrays.asList(recurrence));
	 * 
	 * EventAttendee[] attendees = new EventAttendee[] { new
	 * EventAttendee().setEmail("lpage@example.com"), new
	 * EventAttendee().setEmail("sbrin@example.com"), };
	 * event.setAttendees(Arrays.asList(attendees));
	 * 
	 * EventReminder[] reminderOverrides = new EventReminder[] { new
	 * EventReminder().setMethod("email").setMinutes(24 * 60), new
	 * EventReminder().setMethod("popup").setMinutes(10), }; Event.Reminders
	 * reminders = new Event.Reminders() .setUseDefault(false)
	 * .setOverrides(Arrays.asList(reminderOverrides));
	 * event.setReminders(reminders);
	 * 
	 * String calendarId = "primary"; event = service.events().insert(calendarId,
	 * event).execute(); System.out.printf("Event created: %s\n",
	 * event.getHtmlLink());
	 */
}
