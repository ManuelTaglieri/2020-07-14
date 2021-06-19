/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnClassifica"
    private Button btnClassifica; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSquadra"
    private ComboBox<Team> cmbSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doClassifica(ActionEvent event) {
    	
    	txtResult.clear();
    	if (this.cmbSquadra.getItems().isEmpty()) {
    		txtResult.setText("Creare prima il grafo!");
    		return;
    	}
    	if (this.cmbSquadra.getValue()==null) {
    		txtResult.setText("Selezionare una squadra!");
    		return;
    	}
    	
    	Map<Team, Integer> classifica = this.model.getClassifica();
    	txtResult.appendText("Squadre migliori:\n");
    	for (Team t : this.model.getMigliori(this.cmbSquadra.getValue())) {
    		txtResult.appendText(t.toString() + "(" +(classifica.get(t)-this.cmbSquadra.getValue().getPunti())+ ")\n");
    	}
    	txtResult.appendText("\n");
    	txtResult.appendText("Squadre peggiori:\n");
    	for (Team t : this.model.getPeggiori(this.cmbSquadra.getValue())) {
    		txtResult.appendText(t.toString() + "(" +(classifica.get(t)-this.cmbSquadra.getValue().getPunti())+ ")\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	txtResult.clear();
    	model.creaGrafo();
    	txtResult.appendText("Grafo Creato!\n");
    	txtResult.appendText("# VERTICI: "+model.getVertici()+"\n");
    	txtResult.appendText("# ARCHI: "+model.getArchi()+"\n");
    	for (Team t : this.model.getSquadre()) {
    		this.cmbSquadra.getItems().add(t);
    	}

    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	txtResult.clear();
    	if (this.cmbSquadra.getItems().isEmpty()) {
    		txtResult.setText("Creare prima il grafo!");
    		return;
    	}
    	try {
    		
    		int n = Integer.parseInt(txtN.getText());
    		int x = Integer.parseInt(txtX.getText());
    		
    		this.model.simula(n, x);
    		
    		txtResult.appendText("Simulazione completata!\n");
    		txtResult.appendText("Reporter medi per partita: "+this.model.getRepMedi()+"\n");
    		txtResult.appendText("Partite con un numero di reporter critico: "+this.model.getPartiteCritiche()+"\n");
    		
    	} catch (NumberFormatException e) {
    		txtResult.setText("Inserire dei valori interi naturali validi per N e x");
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClassifica != null : "fx:id=\"btnClassifica\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbSquadra != null : "fx:id=\"cmbSquadra\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
