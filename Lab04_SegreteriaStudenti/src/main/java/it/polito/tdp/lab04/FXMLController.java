package it.polito.tdp.lab04;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
private Model model;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> boxCorsi;

    @FXML
    private Button btnCercaIscritti;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button btnCompilazione;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCercaCorsi;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;
    
    @FXML
    void doCercaCorsi(ActionEvent event) {
    	txtResult.clear();
    	String s=new String();
    	this.boxCorsi.getSelectionModel().clearSelection();
    	try {
    	List<Corso>corsi=model.getCorsoStudente(Integer.parseInt(txtMatricola.getText()));
    	if(corsi.size()==0)
    		txtResult.setText("Spiacente,la matricola inserita non segue corsi");
    	for(Corso c:corsi) {
    		s+=String.format("%-8s", c.getCodins())+" "+String.format("%-4d", c.getCrediti())+" "+String.format("%-50s", c.getNome())+" "+String.format("%-4d\n",c.getPd());
    		//txtResult.appendText(c.getCodins()+"\t"+c.getCrediti()+"\t"+c.getNome()+"\t"+c.getPd()+"\n");
    	    //txtResult.appendText(s);
    	}
    	txtResult.setText(s);
    	}catch(NumberFormatException nbe) {
    		txtResult.setText("Inserisci una matricola valida");
    	}
          this.btnCercaCorsi.setDisable(true);
    }
    

    @FXML
    void doCercaIscritti(ActionEvent event) {
    	String ss=new String();
    	txtResult.clear();
    	if(this.boxCorsi.getValue() == null || this.boxCorsi.getValue().getCodins().equals("")) {
    	txtResult.setText("Inserisci un corso valido");	
    	}else {
    		List<Studente>studenti=model.getStudentiCorso(boxCorsi.getValue());
    		if(studenti.size()!=0) {
    		for(Studente s:studenti) {
    			ss+=String.format("%-8d", s.getMatricola())+" "+String.format("%-25s", s.getNome())+" "+String.format("%-25s", s.getCognome())+" "+String.format("%-8s\n",s.getCDS());
    			//txtResult.appendText(s.getMatricola()+"\t"+s.getNome()+"\t"+s.getCognome()+"\t"+s.getCDS()+"\n");
    		    //txtResult.appendText(ss);
    		}
    		txtResult.setText(ss.substring(0,ss.length()-1));
    		}else {
    			if(this.model.esisteCorso(boxCorsi.getValue()))
    			txtResult.setText("Corso senza iscritti");
    			else
    				txtResult.setText("Il corso non esiste");
    		}
    	}

    }

    @FXML
    void doCompilazione(ActionEvent event) {
    	this.txtMatricola.setDisable(true);
    	this.boxCorsi.getSelectionModel().clearSelection();
    	this.txtResult.clear();
    	try{
       int matricola=Integer.parseInt(txtMatricola.getText());
       String nc=this.model.getStudente(matricola);
       if(nc!=null) {
       String[] nomeCompleto=nc.split(" ");
       this.txtNome.setText(nomeCompleto[0]);
       this.txtCognome.setText(nomeCompleto[1]);
       this.btnIscrivi.setDisable(false);
       this.btnCercaCorsi.setDisable(false);
       }else {
    	   txtResult.setText("Spiacente,la matricola inserita non esiste");
       }   
    	}catch(NumberFormatException nbe) {
    		txtResult.setText("Inserisci una matricola valida");
    	    return ;
    	}
    	
    	
    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	/*
    	txtResult.clear();
    	if(this.boxCorsi.getValue() == null || this.boxCorsi.getValue().getCodins().equals("")) {
        	txtResult.setText("Inserisci un corso valido");	
        	}else {
        		if(this.model.isStudenteIscritto(boxCorsi.getValue(),Integer.parseInt(txtMatricola.getText()))) {
        			txtResult.setText("Lo studente è iscritto al corso");
        		}else {
        			txtResult.setText("Lo studente non è iscritto al corso");
        		}
        	}
    	this.btnIscritti.setDisable(true);
    	*/
    	txtResult.clear();
    	if(this.boxCorsi.getValue() == null || this.boxCorsi.getValue().getCodins().equals("")) {
        	txtResult.setText("Inserisci un corso valido");	
        	}else {
        		try {
        		if(this.model.iscriviStudente(boxCorsi.getValue(),Integer.parseInt(txtMatricola.getText()))) {
        			txtResult.setText(" Studente iscritto al corso");
        		}else {
        			txtResult.setText("Errore: lo studente è già stato iscritto al corso");
        		}
        		}catch(NumberFormatException nbe) {
        			txtResult.setText("Inserisci una matricola valida");
        		}
        	}
    	this.btnIscrivi.setDisable(true);
    	
    }

    @FXML
    void doReset(ActionEvent event) {
    this.boxCorsi.getSelectionModel().clearSelection();
    this.txtMatricola.clear();
    this.txtNome.clear();
    this.txtCognome.clear();
    this.txtResult.clear();
    this.btnIscrivi.setDisable(true);
    this.btnCercaCorsi.setDisable(true);
    this.txtMatricola.setDisable(false);
    }

    public void setModel(Model m) {
    	this.model = m ;
    	this.boxCorsi.getItems().addAll(model.getCorsi());
    }
    
    @FXML
    void initialize() {
        assert boxCorsi != null : "fx:id=\"boxCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscritti != null : "fx:id=\"btnCercaIscritti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCompilazione != null : "fx:id=\"btnCompilazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCecaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"brnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";
        txtResult.setStyle("-fx-font-family:monospace");
    }
}
