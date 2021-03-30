package it.polito.tdp.lab04.model;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
private CorsoDAO corso;
private StudenteDAO studente;

public Model() {
	corso=new CorsoDAO();
	studente=new StudenteDAO();
}

public List<Corso> getCorsi(){
	List<Corso> c=new LinkedList<Corso>();
	c.add(new Corso("",null,null,null));
	for(Corso cc: corso.getTuttiICorsi()) {
		c.add(cc);
	}
	return c;
}
public String getStudente(int matricola) {
	return studente.getStudente(matricola);
}

public List<Studente> getStudentiCorso(Corso corso){
	return this.corso.getStudentiIscrittiAlCorso(corso);
}

public List<Corso> getCorsoStudente(int matricola){
	return this.studente.getCorsiIscrittoLoStudente(matricola);
}
public boolean isStudenteIscritto(Corso corso,int matricola) {
	return this.corso.isIscrittoAlCorso(corso, matricola);
}

public boolean iscriviStudente(Corso corso,int matricola) {
	return this.corso.inscriviStudenteACorso(matricola, corso);
}
}
