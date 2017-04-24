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
import app.modelo.ListaSolicitudesPendientes;
import app.modelo.UsuarioActual;
import app.vista.VistaUtils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javafx.application.HostServices;
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
public class ControladorVistaLogin {
    
    private HostServices hostServices;
    private IClienteServidor interfazServidor;
    
    public HostServices getHostServices() {
        return hostServices;
    }

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }    
    
    @FXML private TextField campoUsuario;
    @FXML private TextField campoPassword;
    
    @FXML
    private void initialize() {
        //DESCOMENTAR
        /*try {
            System.setProperty("java.rmi.server.hostname","192.168.43.214");
            String registroURL = "rmi://localhost:1099/Messenger";
            this.interfazServidor = (IClienteServidor) Naming.lookup(urlRegistro);
        } catch (MalformedURLException | NotBoundException | RemoteException ex) {
            System.out.println(ex.getMessage());
        }
        */ 
    }
    
    @FXML
    private void iniciarSesion() throws IOException, InterruptedException {
        String nick = campoUsuario.getText();
        String password = campoPassword.getText();
        UsuarioActual.getInstancia().setUsuarioActual(nick);        
        // Llamada a método remoto con comprobacion de login
        /*Usuario[] misAmigos = interfazServidor.conectarse(
                        nick, password, 
                        UsuarioActual.getInstancia().getUsuarioActual().getIp(), 
                        UsuarioActual.getInstancia().getUsuarioActual().getPuerto(), 
                        new IServidorClienteImpl()
        );
        if(misAmigos.length == 1 && misAmigos[0].getNick() == null) {
            misAmigos = null;
        }*/        
        // CAMBIAR
        Usuario[] misAmigos = null;
        if(misAmigos == null) {
            // Si el servidor acepta la autenticación cargamos las amistades
            try {
                /*ArrayList<Amigo> listaAmigos = new ArrayList<>();
                for(Usuario aux : misAmigos) {
                    listaAmigos.add(new Amigo(aux.getNick(), aux.isConectado(), aux.getIp(), aux.getPuerto()));
                }               
                // DESCOMENTAR CUANDO FUNCIONE
                for(Amigo aux : listaAmigos) {
                    if(aux.estaConectado()) {
                        ListaAmigosOn.getInstancia().anhadirAmigo(aux);
                    }
                    else {
                        ListaAmigosOff.getInstancia().anhadirAmigo(aux);
                    }
                }*/          
                // COMENTAR CUANDO FUNCIONE
                ArrayList<Amigo> datosPrueba = new ArrayList();
                datosPrueba.add(new Amigo("roquefort21", true, "192.168.0.56", "6666"));
                datosPrueba.add(new Amigo("zipbomb3", false, null, null));
                datosPrueba.add(new Amigo("mdbrimberry", false, null, null));
                datosPrueba.add(new Amigo("MDO13", true, "192.168.0.56", "6667"));                
                datosPrueba.add(new Amigo("mdutcher", true, "192.168.0.15", "6666"));             
                for(Amigo amigo : datosPrueba) {
                    if(amigo.estaConectado()) {
                        ListaAmigosOn.getInstancia().anhadirAmigo(amigo);
                    }
                    else {
                        ListaAmigosOff.getInstancia().anhadirAmigo(amigo);
                    }
                }
                ListaResultadoBusqueda.getInstancia().anhadirAmigo(new Amigo("lopoe21", false, null, null));
                ListaResultadoBusqueda.getInstancia().anhadirAmigo(new Amigo("mdutcher", false, null, null));                
                ListaSolicitudesPendientes.getInstancia().anhadirAmigo(new Amigo("pepinho", false, null, null));
                ListaSolicitudesPendientes.getInstancia().anhadirAmigo(new Amigo("josito", false, null, null));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            
            Stage stage = (Stage)this.campoPassword.getScene().getWindow();
            FXMLLoader loader = VistaUtils.cargarVista("app/vista/VistaGeneral.fxml");
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            // Imprimir aviso de solicitudes pendientes
            if(!ListaSolicitudesPendientes.getInstancia().isEmpty()) {
                loader = VistaUtils.cargarVista("app/vista/VentanaAviso.fxml");
                Parent vista = loader.load();
                ControladorVentanaAviso controlador = loader.getController();     

                controlador.setMensaje("Tienes nuevas solicitudes de amistad pendientes \n\t(Opciones -> Solicitudes pendientes)");
                Stage dialogo = new Stage();
                dialogo.initModality(Modality.WINDOW_MODAL);
                dialogo.initOwner(this.campoUsuario.getScene().getWindow());
                dialogo.setScene(new Scene(vista));
                dialogo.setTitle("Solicitudes pendientes");
                dialogo.showAndWait();
            }          
        }
        else {
            this.campoUsuario.setText("");
            this.campoPassword.setText("");
            
            FXMLLoader loader = VistaUtils.cargarVista("app/vista/VentanaAviso.fxml");
            Parent vista = loader.load();
            ControladorVentanaAviso controlador = loader.getController();
            controlador.setMensaje("Credenciales incorrectas.");
            
            Stage dialogo = new Stage();
            dialogo.initModality(Modality.WINDOW_MODAL);
            dialogo.initOwner(this.campoUsuario.getScene().getWindow());
            dialogo.setScene(new Scene(vista));
            dialogo.setTitle("Error");
            dialogo.showAndWait();         
        }
    }
    
    @FXML
    private void registrarse() {
        hostServices.showDocument("file:///" 
            + System.getProperty("user.dir") 
            + "/src/app/webRegistro.html");
    }
    
}
