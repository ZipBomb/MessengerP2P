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

import app.modelo.Amigo;
import app.modelo.ListaAmigosOff;
import app.modelo.ListaAmigosOn;
import app.modelo.UsuarioActual;
import app.vista.VistaUtils;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 * @version 1.0
 * @since 2017-04
 */
public class ControladorVistaEliminarAmigo {

    @FXML private TextField campoNick;      
    
    @FXML
    private void initialize() {}
    
    @FXML
    private void cancelar() {
        Stage stage = (Stage) this.campoNick.getScene().getWindow();
        stage.close();    
    }
    
    @FXML
    private void confirmar() throws IOException, Exception {
        // Comprobamos si el nombre introducido se corresponde con algún amigo
        Amigo usuarioSeleccionado = ListaAmigosOn.getInstancia().recuperaAmigo(this.campoNick.getText());
        if(usuarioSeleccionado == null) {
            usuarioSeleccionado = ListaAmigosOff.getInstancia().recuperaAmigo(this.campoNick.getText());
        }
        FXMLLoader loader = VistaUtils.cargarVista("app/vista/VentanaAviso.fxml");
        Parent vista = loader.load();
        ControladorVentanaAviso controlador = loader.getController();  
        
        if(usuarioSeleccionado != null) {
            // Notificación al servidor
            String[] args = {
                UsuarioActual.getInstancia().getUsuarioActual().getNick().getValue(),
                usuarioSeleccionado.getNick().getValue()
            };
            HiloClienteServidor hiloLlamada = new HiloClienteServidor(1, args);
            hiloLlamada.start();
            
            // Actualizamos la vista
            if(usuarioSeleccionado.estaConectado()) {
                ListaAmigosOn.getInstancia().eliminarAmigo(usuarioSeleccionado);
            } else {
                ListaAmigosOff.getInstancia().eliminarAmigo(usuarioSeleccionado);
            }
            controlador.setMensaje("Amigo eliminado con éxito.");
            
            Stage dialogo = new Stage();
            dialogo.initModality(Modality.WINDOW_MODAL);
            dialogo.initOwner(this.campoNick.getScene().getWindow());
            dialogo.setScene(new Scene(vista));
            dialogo.setTitle("Éxito");
            dialogo.showAndWait();

            Stage stage = (Stage) this.campoNick.getScene().getWindow();
            stage.close();    
        }
        else {
            controlador.setMensaje("El usuario no pertenece a tu lista de amigos.");
            
            Stage dialogo = new Stage();
            dialogo.initModality(Modality.WINDOW_MODAL);
            dialogo.initOwner(this.campoNick.getScene().getWindow());
            dialogo.setScene(new Scene(vista));
            dialogo.setTitle("Error");
            dialogo.showAndWait();

            Stage stage = (Stage) this.campoNick.getScene().getWindow();
            stage.close();                
        }
    }    
    
}

