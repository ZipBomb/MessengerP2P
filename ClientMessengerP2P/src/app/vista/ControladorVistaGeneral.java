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
package app.vista;

import app.modelo.Amigo;
import app.modelo.ListaAmigosOff;
import app.modelo.ListaAmigosOn;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
public class ControladorVistaGeneral {
    
    @FXML private TableView<Amigo> listaAmigosOn;    
    @FXML private TableColumn<Amigo, String> columnaOn;
    @FXML private TableView<Amigo> listaAmigosOff;   
    @FXML private TableColumn<Amigo, String> columnaOff;
    @FXML private TextField campoBusqueda;
        
    @FXML
    private void initialize() {  
        this.listaAmigosOn.setItems(ListaAmigosOn.getInstancia().getListaAmigosOn());
        this.columnaOn.setCellValueFactory(amigo -> amigo.getValue().getNick());
        this.listaAmigosOff.setItems(ListaAmigosOff.getInstancia().getListaAmigosOff());
        this.columnaOff.setCellValueFactory(amigo -> amigo.getValue().getNick());        
    }

    @FXML
    private void buscarUsuario() {
        this.campoBusqueda.setText("");
//        TODO: Añadir llamadas a servidor y presentar resultados
    }
    
    @FXML
    private void verSolicitudesPendientes() {
//        TODO: Presentar ventana modal con resultados   
    }

    @FXML
    private void anhadirAmistad() throws IOException {
        FXMLLoader loader = VistaUtils.cargarVista("app/vista/VistaDialogoModificar.fxml");
        Parent vista = loader.load();
        ControladorVistaDialogoModificar controlador = loader.getController();
        
        Stage dialogo = new Stage();
        dialogo.initModality(Modality.WINDOW_MODAL);
        dialogo.initOwner(this.listaAmigosOn.getScene().getWindow());
        dialogo.setScene(new Scene(vista));
        dialogo.setTitle("Añadir amistad");
        dialogo.showAndWait();
        
//      TODO: Enviar solicitud al servidor
    }

    @FXML
    private void eliminarAmistad() throws IOException {
        FXMLLoader loader = VistaUtils.cargarVista("app/vista/VistaDialogoModificar.fxml");
        Parent vista = loader.load();
        ControladorVistaDialogoModificar controlador = loader.getController();
        
        Stage dialogo = new Stage();
        dialogo.initModality(Modality.WINDOW_MODAL);
        dialogo.initOwner(this.listaAmigosOn.getScene().getWindow());
        dialogo.setScene(new Scene(vista));
        dialogo.setTitle("Eliminar amistad");
        dialogo.showAndWait();
        
//      TODO: Enviar solicitud al servidor y actualizar tablas locales       
    }

    @FXML
    private void cerrarSesion() {
        Platform.exit();
        
//        TODO: Notificar al servidor del cierre de sesión
    }    
    
}
