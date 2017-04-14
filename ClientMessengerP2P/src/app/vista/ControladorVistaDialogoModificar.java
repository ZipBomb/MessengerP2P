/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.vista;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 */
public class ControladorVistaDialogoModificar {

    @FXML private TextField campoNick;      
    
    @FXML
    private void initialize() {
        
    }
    
    @FXML
    private void cancelar() {
        Stage stage = (Stage) this.campoNick.getScene().getWindow();
        stage.close();    
    }
    
    @FXML
    private void confirmar() {
        Stage stage = (Stage) this.campoNick.getScene().getWindow();
        stage.close();    
    }    
    
}
