package tn.esprit.epione.interfaces;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.Remote;

import tn.esprit.epione.persistance.Doctor;
import tn.esprit.epione.persistance.Extract;

@Remote
public interface ExtractionInt {
public ArrayList<Extract>  SearchBySpeciality(String speciality,int pagenumber) throws IOException;
public ArrayList<Extract> SearchByPlace(String place,int pagenumber) throws IOException;
public ArrayList<Extract>  SearchBySpecialityandPlace(String spec,String place,int pagenumber) throws IOException;
public Extract searchexistingdoctor(String nom,String prenom,String specialie) throws IOException ;
public Doctor AddDoctor(String nom,String prenom ,String specialie) throws IOException;
public void test(String e,int b) throws IOException;

}
