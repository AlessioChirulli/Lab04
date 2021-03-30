package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.DAO.ConnectDB;
import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
public String getStudente(int matricola){
	
	String sql=" select nome,cognome,matricola,CDS "
			+" from studente "
			+" where matricola = ? ";
	
	String s=null;
	try {
		Connection conn=ConnectDB.getConnection();
		PreparedStatement st=conn.prepareStatement(sql);
		st.setInt(1, matricola);
		ResultSet rs=st.executeQuery();
		
		while(rs.next()) {
			String nome=rs.getString("nome");
			String cognome=rs.getString("cognome");
			s=nome+" "+cognome;
		}
		conn.close();
	}catch(SQLException e) {
		throw new RuntimeException(e);
	}
	return s;		
}

public List<Corso> getCorsiIscrittoLoStudente(int matricola) {
	
	List<Corso>corsi=new LinkedList<Corso>();
	
	String sql="select  c.codins,crediti,nome,pd"
			    +" from iscrizione as i,corso as c "
			    +" where i.codins = c.codins "
	            +" and i.matricola = ? ";
	try {
		Connection conn = ConnectDB.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, matricola);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			String codins = rs.getString("codins");
			int numeroCrediti = rs.getInt("crediti");
			String nome = rs.getString("nome");
			int periodoDidattico = rs.getInt("pd");
            
			Corso c=new Corso(codins,numeroCrediti,nome,periodoDidattico);
			corsi.add(c);
			
		}
		

		conn.close();
	
		return corsi;
	}catch (SQLException e) {
		// e.printStackTrace();
		throw new RuntimeException("Errore Db", e);
	}
	
			    // TODO
}
}
