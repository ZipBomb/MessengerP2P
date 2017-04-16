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
import app.modelo.ListaResultadoBusqueda;
import app.modelo.SolicitudesAmistadEnviadas;
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
public class ControladorVistaResultadosBusqueda {
    
    @FXML private TableView<Amigo> listaResultados;
    @FXML private TableColumn<Amigo, String> columnaResultados;
    
    @FXML
    private void initialize() {
        this.listaResultados.setItems(ListaResultadoBusqueda.getInstancia().getListaResultadoBusqueda());
        this.columnaResultados.setCellValueFactory(usuario -> usuario.getValue().getNick());
        this.listaResultados.setPlaceholder(new Label("No hay resultados para tu búsqueda"));
        this.listaResultados.getSelectionModel().selectFirst();
    }
    
    @FXML
    private void cerrar() {
        Stage stage = (Stage) this.listaResultados.getScene().getWindow();
        stage.close();       
    }
    
    @FXML
    private void enviarSolicitud() throws IOException, Exception {
        Amigo usuarioSeleccionado = listaResultados.getSelectionModel().getSelectedItem();        
        FXMLLoader loader = VistaUtils.cargarVista("app/vista/VentanaAviso.fxml");
        Parent vista = loader.load();
        ControladorVentanaAviso controlador = loader.getController();   
        if(!ListaAmigosOn.getInstancia().yaExiste(usuarioSeleccionado) && !ListaAmigosOff.getInstancia().yaExiste(usuarioSeleccionado)) {
            if(!SolicitudesAmistadEnviadas.getInstancia().yaSeEnvio(usuarioSeleccionado)) {
                controlador.setMensaje("Solicitud enviada a " + 
                    usuarioSeleccionado.getNick().getValue() +
                    " con éxito.");
                SolicitudesAmistadEnviadas.getInstancia().anhadirSolicitud(usuarioSeleccionado);

                Stage dialogo = new Stage();
                dialogo.initModality(Modality.WINDOW_MODAL);
                dialogo.initOwner(this.listaResultados.getScene().getWindow());
                dialogo.setScene(new Scene(vista));
                dialogo.setTitle("Éxito");
                dialogo.showAndWait();
            }
            else {
                controlador.setMensaje("Ya has enviado una solicitud a este usuario.");
                Stage dialogo = new Stage();
                dialogo.initModality(Modality.WINDOW_MODAL);
                dialogo.initOwner(this.listaResultados.getScene().getWindow());
                dialogo.setScene(new Scene(vista));
                dialogo.setTitle("Error");
                dialogo.showAndWait();  
            }
        }
        else {
            controlador.setMensaje("El usuario ya pertenece a tu lista de amigos.");
            Stage dialogo = new Stage();
            dialogo.initModality(Modality.WINDOW_MODAL);
            dialogo.initOwner(this.listaResultados.getScene().getWindow());
            dialogo.setScene(new Scene(vista));
            dialogo.setTitle("Error");
            dialogo.showAndWait();              
        }        
    }
    
}