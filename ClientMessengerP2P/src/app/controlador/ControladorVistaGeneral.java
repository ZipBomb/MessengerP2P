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
import app.modelo.ListaConversaciones;
import app.modelo.Mensaje;
import app.modelo.UsuarioActual;
import app.vista.VistaUtils;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
        this.listaAmigosOn.setPlaceholder(new Label("No tienes ningún amigo conectado."));
        this.listaAmigosOff.setPlaceholder(new Label("No tienes ningún amigo desconectado."));  
        
        HiloCambiosIP hiloCambiosIp = new HiloCambiosIP();
        hiloCambiosIp.start();
    }
    
    @FXML
    private void iniciarConversacion(MouseEvent event) throws IOException {
        if(event.getClickCount() == 2) {
            FXMLLoader loader = VistaUtils.cargarVista("app/vista/VistaConversacion.fxml");
            Parent vista = loader.load();
            ControladorVistaConversacion controlador = loader.getController();

            Amigo destinatario = this.listaAmigosOn.getSelectionModel().getSelectedItem();
            if(!ListaConversaciones.getInstancia().existeConversacion(destinatario)) {
                ListaConversaciones.getInstancia().iniciarConversacion(destinatario);
            }
            Mensaje aux = new Mensaje(destinatario, "Que no estamos tan mal, hombre!");
            ListaConversaciones.getInstancia().getConversacion(destinatario).anhadirMensaje(aux);

            controlador.initData(ListaConversaciones.getInstancia().getConversacion(destinatario));            
            
            Stage dialogo = new Stage();
            dialogo.setScene(new Scene(vista));
            dialogo.setTitle("Conversación con " 
                    + destinatario.getNick().getValue());
            dialogo.show();
        }
    }

    @FXML
    private void buscarUsuario() throws IOException {
        //Peticion servidor
        String[] args = { this.campoBusqueda.getText() };
        HiloClienteServidor hiloLlamada = new HiloClienteServidor(4, args);
        hiloLlamada.start();
        
        // Cargar vista            
        this.campoBusqueda.setText("");
        FXMLLoader loader = VistaUtils.cargarVista("app/vista/VistaResultadosBusqueda.fxml");
        Parent vista = loader.load();
        ControladorVistaResultadosBusqueda controlador = loader.getController();
        
        Stage dialogo = new Stage();
        dialogo.initModality(Modality.WINDOW_MODAL);
        dialogo.initOwner(this.listaAmigosOn.getScene().getWindow());
        dialogo.setScene(new Scene(vista));
        dialogo.setTitle("Usuarios encontrados");
        dialogo.showAndWait();
    }
    
    @FXML
    private void verSolicitudesPendientes() throws IOException {
        // Cargar vista        
        FXMLLoader loader = VistaUtils.cargarVista("app/vista/VistaSolicitudes.fxml");
        Parent vista = loader.load();
        ControladorVistaSolicitudes controlador = loader.getController();
        
        Stage dialogo = new Stage();
        dialogo.initModality(Modality.WINDOW_MODAL);
        dialogo.initOwner(this.listaAmigosOn.getScene().getWindow());
        dialogo.setScene(new Scene(vista));
        dialogo.setTitle("Solicitudes pendientes");
        dialogo.showAndWait();   
    }

    @FXML
    private void anhadirAmistad() throws IOException {
        // Cargar vista
        FXMLLoader loader = VistaUtils.cargarVista("app/vista/VistaAnhadirAmigo.fxml");
        Parent vista = loader.load();
        ControladorVistaAnhadirAmigo controlador = loader.getController();
        
        Stage dialogo = new Stage();
        dialogo.initModality(Modality.WINDOW_MODAL);
        dialogo.initOwner(this.listaAmigosOn.getScene().getWindow());
        dialogo.setScene(new Scene(vista));
        dialogo.setTitle("Añadir amistad");
        dialogo.showAndWait();
    }

    @FXML
    private void eliminarAmistad() throws IOException {
        // Cargar vista        
        FXMLLoader loader = VistaUtils.cargarVista("app/vista/VistaEliminarAmigo.fxml");
        Parent vista = loader.load();
        ControladorVistaEliminarAmigo controlador = loader.getController();
        
        Stage dialogo = new Stage();
        dialogo.initModality(Modality.WINDOW_MODAL);
        dialogo.initOwner(this.listaAmigosOn.getScene().getWindow());
        dialogo.setScene(new Scene(vista));
        dialogo.setTitle("Eliminar amistad");
        dialogo.showAndWait();    
    }

    @FXML
    private void cerrarSesion() {
        //Peticion servidor
        String[] args = { UsuarioActual.getInstancia().getUsuarioActual().getNick().getValue() };
        HiloClienteServidor hiloLlamada = new HiloClienteServidor(2, args);
        hiloLlamada.start();            
        
        Platform.exit();
    }    
    
}
