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
import app.modelo.ListaSolicitudesPendientes;
import app.vista.VistaUtils;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 * @version 1.0
 * @since 2017-04
 */
public class ControladorVistaSolicitudes {

    @FXML private TableView<Amigo> listaSolicitudesPendientes;
    @FXML private TableColumn<Amigo, String> columnaSolicitudes;
    
    @FXML
    private void initialize() {
        this.listaSolicitudesPendientes.setItems(ListaSolicitudesPendientes.getInstancia().getListaSolicitudesPendientes());
        this.columnaSolicitudes.setCellValueFactory(solicitud -> solicitud.getValue().getNick());
        this.listaSolicitudesPendientes.setPlaceholder(new Label("No tienes ninguna solicitud pendiente"));    
        this.listaSolicitudesPendientes.getSelectionModel().selectFirst();    
    }
    
    @FXML
    private void cerrar() {
        Stage stage = (Stage) this.listaSolicitudesPendientes.getScene().getWindow();
        stage.close();      
    }

    @FXML
    private void aceptar() throws IOException {
        Amigo usuarioSeleccionado = this.listaSolicitudesPendientes.getSelectionModel().getSelectedItem();
        FXMLLoader loader = VistaUtils.cargarVista("app/vista/VentanaAviso.fxml");
        Parent vista = loader.load();
        ControladorVentanaAviso controlador = loader.getController();           
        
//        TO DO: Notificar al servidor
        try {
            if(usuarioSeleccionado.estaConectado()) {
                ListaAmigosOn.getInstancia().anhadirAmigo(usuarioSeleccionado);
                ListaSolicitudesPendientes.getInstancia().eliminarAmigo(usuarioSeleccionado);
            }
            else {
                ListaAmigosOff.getInstancia().anhadirAmigo(usuarioSeleccionado);
                ListaSolicitudesPendientes.getInstancia().eliminarAmigo(usuarioSeleccionado);                
            }           
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        controlador.setMensaje("Amistad añadida con éxito.");
        Stage dialogo = new Stage();
        dialogo.initModality(Modality.WINDOW_MODAL);
        dialogo.initOwner(this.listaSolicitudesPendientes.getScene().getWindow());
        dialogo.setScene(new Scene(vista));
        dialogo.setTitle("Éxito");
        dialogo.showAndWait();        
    }

    @FXML
    private void rechazar() throws IOException {
        Amigo usuarioSeleccionado = this.listaSolicitudesPendientes.getSelectionModel().getSelectedItem();
        FXMLLoader loader = VistaUtils.cargarVista("app/vista/VentanaAviso.fxml");
        Parent vista = loader.load();
        ControladorVentanaAviso controlador = loader.getController();           
        
//        TO DO: Notificar al servidor
        try {
            ListaSolicitudesPendientes.getInstancia().eliminarAmigo(usuarioSeleccionado);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        controlador.setMensaje("Petición rechazada con éxito.");
        Stage dialogo = new Stage();
        dialogo.initModality(Modality.WINDOW_MODAL);
        dialogo.initOwner(this.listaSolicitudesPendientes.getScene().getWindow());
        dialogo.setScene(new Scene(vista));
        dialogo.setTitle("Éxito");
        dialogo.showAndWait();        
    }    
    
}
