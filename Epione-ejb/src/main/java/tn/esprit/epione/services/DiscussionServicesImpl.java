package tn.esprit.epione.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import tn.esprit.epione.interfaces.DiscussionIServicesLocal;
import tn.esprit.epione.interfaces.DiscussionIServicesRemote;
import tn.esprit.epione.persistance.Discussion;
import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Message;
import tn.esprit.epione.persistance.Patient;
import tn.esprit.epione.persistance.Role;
import tn.esprit.epione.persistance.User;
import tn.esprit.epione.util.Util;



@LocalBean
@Stateless
public class DiscussionServicesImpl implements DiscussionIServicesLocal {
	
	@PersistenceContext(name="Epione-ejb")
	EntityManager em ;

	@Override
	public int addDiscussion(Discussion c) {
		em.persist(c);
		em.flush();
		return c.getId();
	}

	@Override
	public int sendMsg(int idDoctor, int idPatient, Message msg) {

		TypedQuery<Discussion> q;
		Discussion c;
		try {
			q = em.createQuery(
					"select c from Discussion c where (c.doctor.id = :idDoctor and c.patient.id = :idPatient)",
					Discussion.class);
			q.setParameter("idDoctor", idDoctor).setParameter("idPatient", idPatient);
			List<Discussion> cl = q.getResultList();

			if (cl.isEmpty()) {
				System.out.println("***************** NULL ***********");
				c = new Discussion();
				c.setDoctor(new Doctor(idDoctor));
				c.setPatient(new Patient(idPatient));
				int idDiscussion = addDiscussion(c);

				c = em.find(Discussion.class, idDiscussion);

				msg.setDiscussion(c);
				msg.setSentTime(Util.getDateNowUTC());
				em.persist(msg);
				em.flush();
				return msg.getId();
			}
			System.out.println("***************** NOT NULL ***********");
			c = cl.get(0);

			c.setLastUpdated(Util.getDateNowUTC());
			c.getMessages().add(msg);
			msg.setSentTime(Util.getDateNowUTC());
			msg.setDiscussion(c);
			em.persist(msg);
			em.merge(c);
			return msg.getId();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}

	}

	@Override
	public Discussion getDiscussionById(int discussionId) {
		return em.find(Discussion.class, discussionId);
	}

	@Override
	public List<Message> getMessageLastDays(int discussionId, int days) {
		try {
			TypedQuery<Message> q = em.createQuery(
					"select m from Message m where (m.discussion.id = :cid) "
							+ " and ( (  TO_DAYS(:nowUTC) - TO_DAYS(m.sentTime) ) <= :days ) " + "order by m.sentTime",
					Message.class);
			q.setParameter("cid", discussionId).setParameter("days", new Long(days)).setParameter("nowUTC",
					Util.getDateNowUTC(), TemporalType.TIMESTAMP);
			List<Message> msgs = q.getResultList();
			return msgs;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public Discussion getDiscussion2Users(int idDoctor, int idPatient) {
		try {
			TypedQuery<Discussion> q = em.createQuery(
					"select c from Discussion c where (c.patient.id= :p and c.doctor.id= :d)", Discussion.class);
			q.setParameter("p", idPatient).setParameter("d", idDoctor);
			return (Discussion) q.getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public Discussion getDiscussion2UsersLastDays(int idDoctor, int idPatient, int days) {
		try {
			TypedQuery<Discussion> q = em.createQuery(
					"select c from Discussion c where (c.patient.id= :p and c.doctor.id= :d)", Discussion.class);
			q.setParameter("p", idPatient).setParameter("d", idDoctor);
			Discussion c = (Discussion) q.getSingleResult();
			c.setMessages(getMessageLastDays(c.getId(), days));
			return c;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	// My discussions order by last message sent
	@Override
	public List<Discussion> getDiscussionsByUser(int idUser) {

		try {
			User u = em.find(User.class, idUser);
			if (u == null)
				return null;

			TypedQuery<Discussion> q = em.createQuery(
					"select c from Discussion c where  ((c.doctor.id= :idUser) or (c.patient.id = :idUser)) order by lastUpdated desc",
					Discussion.class);

			q.setParameter("idUser", idUser);
			List<Discussion> discussions = q.getResultList();

			return discussions;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public List<Discussion> getDiscussionsLastDays(int idUser, int days) {

		try {
			User u = em.find(User.class, idUser);

			if (u == null)
				return null;

			TypedQuery<Discussion> q = em.createQuery(
					"select c from Discussion c where ((c.doctor.id= :idUser) or (c.patient.id = :idUser)) "
							+ " and ((TO_DAYS(NOW()) - TO_DAYS(c.lastUpdated)) <= " + days
							+ ") order by c.lastUpdated desc",
					Discussion.class);
			q.setParameter("idUser", idUser);

			return q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public boolean seenDiscussion(int discussionId) {
		try {
			Discussion c = em.find(Discussion.class, discussionId);
			c.getMessages().stream().filter(m -> m.getSeenTime() == null)
					.forEach(m -> m.setSeenTime(Util.getDateNowUTC()));

			em.merge(c);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}

	@Override
	public boolean deleteDiscussion(int discussionId, int idUser) {

		try {
			Discussion c = em.find(Discussion.class, discussionId);
			if (c.getDoctor().getId() == idUser) {
				if (c.isPatientDeleted()) {
					em.refresh(c);
					em.remove(c);
					return true;
				}
				c.setDoctorDeleted(true);
				em.merge(c);
				sendMsg(c.getDoctor().getId(), c.getPatient().getId(), new Message("The doctor have deleted the discussion",c.getDoctor().getId()));
				return true;
			}

			if (c.getPatient().getId() == idUser) {
				if (c.isDoctorDeleted()) {
					em.refresh(c);
					em.remove(c);
					return true;
				}
				c.setPatientDeleted(true);
				em.merge(c);
				sendMsg(c.getDoctor().getId(), c.getPatient().getId(), new Message("The patient have deleted the discussion", c.getPatient().getId()));
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return false;
	}
}
