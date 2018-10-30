package tn.esprit.epione.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.epione.interfaces.EvaluationServiceLocal;
import tn.esprit.epione.persistance.Appointment;
import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Evaluation;
import tn.esprit.epione.persistance.User;



@LocalBean
@Stateless
public class EvaluationServicesImpl implements EvaluationServiceLocal {
	
	@PersistenceContext(name="Epione-ejb")
	EntityManager em ;

	@Override
	public int addEvalutation(Evaluation evaluation) {
		try {
			em.persist(evaluation);
			em.flush();
			return evaluation.getId();
		}catch(Exception e)
		{
			return -1;
		}
		
	}

	@Override
	public int editEvalutation(Evaluation evaluation) {
		try {
			em.merge(evaluation);
			return 0;
			
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			return -1;
		}
		
	}

	@Override
	public int deleteEvalutation(Evaluation evaluation) {
		try {
			Evaluation delete = em.find(Evaluation.class, evaluation.getId());
			em.remove(delete);
			return 0;
			
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			return -1;
		}
		
	}

	@Override
	public double AverageRating(int idDoctor) {
		UserService us = new UserService();
		List<Appointment> appointments = new ArrayList<>();
		TypedQuery <Appointment> query= em.createQuery("select e from Appointment e where e.doctor = :idUser",Appointment.class);
		query.setParameter("idUser", em.find(User.class, idDoctor));
		appointments = query.getResultList();
        double average = appointments.stream().mapToDouble(a -> a.getEvaluation().getNote()).average().getAsDouble();
		return average;
	}

}
