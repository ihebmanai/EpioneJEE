package tn.esprit.epione.interfaces;

import java.util.List;

import javax.ejb.Remote;

import tn.esprit.epione.persistance.Discussion;
import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Message;
import tn.esprit.epione.persistance.Patient;

@Remote
public interface DiscussionIServicesRemote {
	
	
	public int addDiscussion(Discussion c);
	public int sendMsg(int idDiscussion, Message msg) ;
	public boolean seenDiscussion(int discussionId);
	
	public Discussion getDiscussionById(int discussionId);
	public List<Message> getMessageLastDays(int discussionId, int days);
	public Discussion getDiscussion2Users(int idDoctor, int idPatient);
	public Discussion getDiscussion2UsersLastDays(int idDoctor, int idPatient,int days);
	public List<Discussion> getDiscussions(int idUser);
	public List<Discussion> getDiscussionsLastDays(int idUser, int days);
	
	public boolean deleteDiscussion(int discussionId, int idUser);
	
	
}
