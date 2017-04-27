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
import app.modelo.ListaSolicitudesPendientes;
import app.modelo.UsuarioActual;
import app.vista.VistaUtils;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
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
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 * @version 1.0
 * @since 2017-04
 */
public class ControladorVistaGeneral {
    
    private ConcurrentHashMap<Amigo, Boolean> conversacionesAbiertas;
    
    @FXML private TableView<Amigo> listaAmigosOn;    
    @FXML private TableColumn<Amigo, String> columnaOn;
    @FXML private TableView<Amigo> listaAmigosOff;   
    @FXML private TableColumn<Amigo, String> columnaOff;
    @FXML private TextField campoBusqueda;
        
    @FXML
    private void initialize() throws IOException {
        this.listaAmigosOn.setItems(ListaAmigosOn.getInstancia().getListaAmigosOn());
        this.columnaOn.setCellValueFactory(amigo -> amigo.getValue().getNick());
        this.listaAmigosOff.setItems(ListaAmigosOff.getInstancia().getListaAmigosOff());
        this.columnaOff.setCellValueFactory(amigo -> amigo.getValue().getNick());  
        this.listaAmigosOn.setPlaceholder(new Label("No tienes ningún amigo conectado."));
        this.listaAmigosOff.setPlaceholder(new Label("No tienes ningún amigo desconectado."));  
        this.listaAmigosOn.getSelectionModel().selectFirst();
        
        this.conversacionesAbiertas = new ConcurrentHashMap<>();
        
        ListaSolicitudesPendientes.getInstancia().mainControllerProperty.set(this);
    }
    
    @FXML
    private void iniciarConversacion(MouseEvent event) throws IOException {
        if(event.getClickCount() == 2 && !this.conversacionesAbiertas.containsKey(this.listaAmigosOn.getSelectionModel().getSelectedItem())) {
            FXMLLoader loader = VistaUtils.cargarVista("app/vista/VistaConversacion.fxml");
            Parent vista = loader.load();
            ControladorVistaConversacion controlador = loader.getController();

            Amigo destinatario = this.listaAmigosOn.getSelectionModel().getSelectedItem();
            if(!ListaConversaciones.getInstancia().existeConversacion(destinatario)) {
                ListaConversaciones.getInstancia().iniciarConversacion(destinatario);
            }
            controlador.initData(ListaConversaciones.getInstancia().getConversacion(destinatario));            
            this.conversacionesAbiertas.put(destinatario, Boolean.TRUE);
            
            Stage dialogo = new Stage();
            dialogo.setScene(new Scene(vista));
            dialogo.setOnCloseRequest((WindowEvent event1) -> {
                this.conversacionesAbiertas.put(destinatario, Boolean.FALSE);
            });            
            dialogo.setTitle("Conversación con " + destinatario.getNick().getValue());
            dialogo.show();
        }
    }

    @FXML
    private void buscarUsuario() throws IOException {
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
        String[] args = { UsuarioActual.getInstancia().getUsuarioActual().getNick().getValue() };
        HiloClienteServidor hiloLlamada = new HiloClienteServidor(2, args);
        hiloLlamada.start();

        Platform.exit();
    }
    
    public void reabreVentanaConversacion(Amigo amigo) throws IOException {
        System.out.println("Contiene: " + this.conversacionesAbiertas.containsKey(amigo));
        System.out.println("Estado conversación: " + this.conversacionesAbiertas.get(amigo));
        if(!this.conversacionesAbiertas.containsKey(amigo) 
            || Objects.equals(this.conversacionesAbiertas.get(amigo), Boolean.FALSE)) {
            FXMLLoader loader = VistaUtils.cargarVista("app/vista/VistaConversacion.fxml");
            Parent vista = loader.load();

            System.out.println("Amigo: " + this.conversacionesAbiertas.get(amigo));
            if(!ListaConversaciones.getInstancia().existeConversacion(amigo)) {
                ListaConversaciones.getInstancia().iniciarConversacion(amigo);
            }
            // controlador.initData(ListaConversaciones.getInstancia().getConversacion(amigo));            
            this.conversacionesAbiertas.put(amigo, Boolean.TRUE);
            System.out.println("YEAH");
            Stage dialogo = new Stage();
            dialogo.setScene(new Scene(vista));
            dialogo.setTitle("Conversación con " + amigo.getNick().getValue());
            dialogo.setOnCloseRequest((WindowEvent event1) -> {
                this.conversacionesAbiertas.put(amigo, Boolean.FALSE);
            });
            dialogo.show();            
        }
    }
    
    public void notificaNuevaSolicitud(Amigo amigo) throws IOException {
        FXMLLoader loader = VistaUtils.cargarVista("app/vista/VentanaAviso.fxml");
        Parent vista = loader.load();
        ControladorVentanaAviso controlador = loader.getController();          
        controlador.setMensaje(amigo.getNick().getValue() + 
                "te ha enviado una solicitud de amistad.");

        Stage dialogo = new Stage();
        dialogo.initModality(Modality.WINDOW_MODAL);
        dialogo.initOwner(this.campoBusqueda.getScene().getWindow());
        dialogo.setScene(new Scene(vista));
        dialogo.setTitle("Nueva solicitud");
        dialogo.showAndWait();

        Stage stage = (Stage) this.campoBusqueda.getScene().getWindow();
        stage.close();           
    }

}
