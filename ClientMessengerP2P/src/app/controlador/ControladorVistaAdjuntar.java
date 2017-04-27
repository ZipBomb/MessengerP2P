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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 */
public class ControladorVistaAdjuntar {
    
    ObjectProperty<ControladorVistaConversacion> mainControllerProperty = new SimpleObjectProperty();

    @FXML private TextField campoRuta;      
    
    @FXML
    private void initialize() {}
    
    @FXML
    private void cancelar() {
        Stage stage = (Stage) this.campoRuta.getScene().getWindow();
        stage.close();    
    }
    
    @FXML
    private void adjuntar() throws FileNotFoundException, IOException {
        String ruta = this.campoRuta.getText();
        File myFile = new File(ruta);    
        if(myFile.isFile()) {
            String nombre = myFile.getName();
            byte [] arrayArchivo  = new byte [(int)myFile.length()];
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(arrayArchivo,0,arrayArchivo.length);
            String cabecera = nombre + ":";

            byte [] paquete = new byte[arrayArchivo.length + cabecera.length()];

            for(int i = 0; i < cabecera.length(); i++){
                paquete[i] = (byte)cabecera.charAt(i);                    
            }                
            int j = 0;
            for(int i = cabecera.length() - 1; i < arrayArchivo.length + cabecera.length() - 1; i++){                    
                paquete[i] = arrayArchivo[j];                    
                j++;
            }
            // Llamada al controlador para enviar el paquete por el canal TCP y cierre de ventana
            this.mainControllerProperty.getValue().enviarData(
                    paquete, 
                    this.mainControllerProperty.getValue().getConversacionActual().getDestinatario()
            );
            Stage stage = (Stage) this.campoRuta.getScene().getWindow();
            stage.close();     
        }      
    }
    
}
