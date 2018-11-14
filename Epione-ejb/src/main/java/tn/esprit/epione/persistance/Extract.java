package tn.esprit.epione.persistance;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
public class Extract implements Serializable{

private String nom;
private String prenom;

private String adresse;
private String specialite;
private String profile ;
private String photo;
private String telephone;
private String password;
public String getTelephone() {
	return telephone;
}





public void setTelephone(String telephone) {
	this.telephone = telephone;
}





public String getPassword() {
	return password;
}





public void setPassword(String password) {
	this.password = password;
}





public Extract(String nom, String adresse, String specialite,String password) {
	super();
	this.nom = nom;
	this.adresse = adresse;
	this.specialite = specialite;
	this.password = password;
}





public Extract(String nom, String prenom, String adresse, String specialite, String profile, String photo) {
	super();
	this.nom = nom;
	this.prenom = prenom;
	this.adresse = adresse;
	this.specialite = specialite;
	this.profile = profile;
	this.photo = photo;
}





public Extract() {
	super();
}


public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}
@XmlAttribute
public String getAdresse() {
	return adresse;
}
public void setAdresse(String adresse) {
	this.adresse = adresse;
}
public String getSpecialite() {
	return specialite;
}
public void setSpecialite(String specialite) {
	this.specialite = specialite;
}
public String getPrenom() {
	return prenom;
}





public void setPrenom(String prenom) {
	this.prenom = prenom;
}





public String getProfile() {
	return profile;
}





public void setProfile(String profile) {
	this.profile = profile;
}





public String getPhoto() {
	return photo;
}





public void setPhoto(String photo) {
	this.photo = photo;
}





@Override
public String toString() {
	return "nom=" + nom + ", prenom=" + prenom + ", adresse=" + adresse + ", specialite=" + specialite
			+ ", profile=" + profile + ", photo=" + photo +", telephone="+telephone;
}

}
