/*
 * Copyright (C) 2017 Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package app.controlador;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 */
public class ControladorVentanaAviso {

    private StringProperty mensaje;
        
    public void setMensaje(String mensaje) {
        if(mensaje.length() > 0) {
            this.mensaje.setValue(mensaje);
        }
    } 
    
    @FXML private Label etiquetaMensaje;
    
    @FXML
    public void initialize() {
        this.mensaje = new SimpleStringProperty("");
        etiquetaMensaje.textProperty().bind(mensaje);
    }  
    
    @FXML
    private void cerrar() {
        Stage stage = (Stage) this.etiquetaMensaje.getScene().getWindow();
        stage.close();        
    }
    
}
