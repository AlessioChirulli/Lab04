package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
                
				Corso c=new Corso(codins,numeroCrediti,nome,periodoDidattico);
				corsi.add(c);

				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
			}
			

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(Corso corso) {
		String sql=" select codins,nome,crediti,pd "
				   +" from iscrizione "
				   +" where codins = ? ";
		
		Corso c=null;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
                
				 c=new Corso(codins,numeroCrediti,nome,periodoDidattico);
			}
			

			conn.close();
			
			return c;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		
		// TODO
	}
public boolean isIscrittoAlCorso(Corso corso,int matricola) {
	
	boolean controllo=false;
	
	String sql="select matricola,codins "
		    +" from iscrizione as i "
		    +" where matricola = ? "
            +" and codins = ? ";
	try {
		Connection conn = ConnectDB.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, matricola);
		st.setString(2, corso.getCodins());
		ResultSet rs = st.executeQuery();

		if (rs.next()) {
			
		controllo=true;	
		}
		

		conn.close();
	 
		return controllo;
		
	}catch (SQLException e) {
		// e.printStackTrace();
		throw new RuntimeException("Errore Db", e);
	}
}
	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		
		List<Studente>studenti=new LinkedList<Studente>();
		
		String sql="select nome,cognome,i.matricola,CDS "
				    +" from iscrizione as i,studente as s "
				    +" where i.matricola = s.matricola "
		            +" and i.codins = ? ";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String nome=rs.getString("nome");
				String cognome=rs.getString("cognome");
				String CDS=rs.getString("CDS");
				int matricola=rs.getInt("matricola");
				
				Studente s=new Studente(matricola,nome,cognome,CDS);
				studenti.add(s);
				
			}
			

			conn.close();
		
			return studenti;
		}catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		
				    // TODO
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(int matricola,Corso corso) {
		// TODO
		// ritorna true se l'iscrizione e' avvenuta con successo
		if(!this.isIscrittoAlCorso(corso, matricola)) {
			
			String sql=" insert into iscrizione ( matricola,codins ) "
					   +" values(?,?) ";
			try {
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setInt(1, matricola);
				st.setString(2, corso.getCodins());
				int rs = st.executeUpdate();
				

				conn.close();
			    
				if(rs==0)
				return false;
				else
				return true;
				
			}catch (SQLException e) {
				// e.printStackTrace();
				throw new RuntimeException("Errore Db", e);
			}
			
		}
		return false;
	}
	

}
