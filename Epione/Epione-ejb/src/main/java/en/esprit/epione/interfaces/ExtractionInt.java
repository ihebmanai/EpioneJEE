package en.esprit.epione.interfaces;

import javax.ejb.Remote;

@Remote
public interface ExtractionInt {
public void SearchBySpeciality(String link);
public void SearchByPlace(String place);
public void SearchBySpecialityandPlace(String spec,String place);


}
