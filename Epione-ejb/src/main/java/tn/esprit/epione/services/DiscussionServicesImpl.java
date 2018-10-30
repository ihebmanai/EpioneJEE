package tn.esprit.epione.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
	public int sendMsg(int idDiscussion, Message msg) {

		try {
//			msg.setStatus(MessageStatus.sent);
			Discussion c = em.find(Discussion.class, idDiscussion);
			c.getMessages().add(msg);
			msg.setDiscussion(c);

			em.persist(msg);
			em.flush();
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
			TypedQuery<Message> q = em.createQuery("select m from Message m where (m.discussion = :cid) "
					+ " and ( (  TO_DAYS(NOW()) - TO_DAYS(m.timeSent) ) <= " + days + " ) "
					+ "order by m.timeSent", Message.class);
			q.setParameter("cid", em.find(Discussion.class, discussionId));
			return q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public Discussion getDiscussion2Users(int idDoctor, int idPatient) {
		try {
			TypedQuery<Discussion> q = em.createQuery(
					"select c from Discussion c where (c.patient= :p and c.doctor= :d)", Discussion.class);
			q.setParameter("p", new Patient(idPatient)).setParameter("d", new Doctor(idDoctor));
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
					"select c from Discussion c where (c.patient= :p and c.doctor= :d)", Discussion.class);
			q.setParameter("p", new Patient(idPatient)).setParameter("d", new Doctor(idDoctor));
			Discussion c = (Discussion) q.getSingleResult();
			c.setMessages(getMessageLastDays(c.getId(), days));
			return c;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public List<Discussion> getDiscussions(int idUser) {

		try {
			User u = em.find(User.class, idUser);

			TypedQuery<Discussion> q = em.createQuery(
					"select c from Discussion c where  c.doctor= :idUser order by c.creationTime",
					Discussion.class);
			q.setParameter("idUser", new Doctor(idUser));

			if (u.getRole() == Role.patient) {
				q = em.createQuery("select c from Discussion c where c.patient= :idUser order by c.creationTime",
						Discussion.class);
				q.setParameter("idUser", new Patient(idUser));
			}

			return q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public List<Discussion> getDiscussionsLastDays(int idUser, int days) {

		try {
			User u = em.find(User.class, idUser);

			TypedQuery<Discussion> q = em.createQuery("select c from Discussion c where (c.doctor= :idUser) "
					+ " and ((TO_DAYS(NOW()) - TO_DAYS(c.creationTime)) <= " + days + ") order by c.creationTime",
					Discussion.class);
			q.setParameter("idUser", new Doctor(idUser));

			if (u.getRole() == Role.patient) {
				q = em.createQuery("select c from Discussion c where (c.patient= :idUser) "
						+ " and ((TO_DAYS(NOW()) - TO_DAYS(c.creationTime)) <= " + days + ") order by c.creationTime",
						Discussion.class);
				q.setParameter("idUser", new Patient(idUser));
			}
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
			c.setTimeSeen(Util.getDateNowUTC());
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
			if (c.getDoctor() != null && c.getDoctor().getId() == idUser) {
				if (c.getPatient() == null) {
					em.refresh(c);
					em.remove(c);
				} else {
					c.setDoctor(null);
					em.merge(c);
					em.persist(c);
				}
			} else if (c.getPatient() != null && c.getPatient().getId() == idUser) {
				if (c.getDoctor() == null) {
					em.refresh(c);
					em.remove(c);
				} else {
					c.setPatient(null);
					em.merge(c);
					em.persist(c);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

}
