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
import app.modelo.UsuarioActual;
import app.vista.VistaUtils;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Pattern;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
public class ControladorVistaLogin {
    
    private HostServices hostServices;
    private IClienteServidor interfazServidor;
    private Pattern regexp;    
    
    public HostServices getHostServices() {
        return hostServices;
    }

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }    
    
    @FXML private TextField campoUsuario;
    @FXML private PasswordField campoPassword;
    
    @FXML
    private void initialize() {
        try {
            this.regexp = Pattern.compile("^(192\\.168\\.).*");
            String ipLocal = this.getLocalIp();
            System.setProperty("java.rmi.server.hostname", ipLocal);
            String urlRegistro = "rmi://192.168.43.214:1099/Messenger";
            this.interfazServidor = (IClienteServidor) Naming.lookup(urlRegistro);
        } catch (MalformedURLException | NotBoundException | RemoteException | SocketException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void iniciarSesion() throws IOException, InterruptedException {
        String nick = campoUsuario.getText();
        String password = campoPassword.getText();
        try {        
            UsuarioActual.getInstancia().setUsuarioActual(nick);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        // Llamada a método remoto con comprobacion de login
        Usuario[] misAmigos = interfazServidor.conectarse(
                        nick, password, 
                        new IServidorClienteImpl(),
                        new IComunicacionClienteImpl()
        );
        if(misAmigos != null) {
            // Si el servidor acepta la autenticación cargamos las amistades
            try {
                ArrayList<Amigo> listaAmigos = new ArrayList<>();
                for(Usuario aux : misAmigos) {
                    listaAmigos.add(new Amigo(aux.getNick(), aux.isConectado(), aux.getInterfaz()));
                }
                for(Amigo aux : listaAmigos) {
                    if(aux.estaConectado()) {
                        ListaAmigosOn.getInstancia().anhadirAmigo(aux);
                    }
                    else {
                        ListaAmigosOff.getInstancia().anhadirAmigo(aux);
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            // Definición de la ventana principal
            FXMLLoader loader = VistaUtils.cargarVista("app/vista/VistaGeneral.fxml");            
            Parent vista = loader.load();            
            Stage stage = (Stage)this.campoPassword.getScene().getWindow();
            Scene scene = new Scene(vista);
            stage.setScene(scene);
            // Desconectarse al cerrar ventana            
            stage.setOnCloseRequest((WindowEvent event) -> {
                String[] args = { UsuarioActual.getInstancia().getUsuarioActual().getNick().getValue() };
                HiloClienteServidor hiloLlamada = new HiloClienteServidor(2, args);
                hiloLlamada.start();
                Platform.exit();
            });              
            stage.show();   
            // Imprimir aviso de solicitudes pendientes en caso de que se hayan notificado
            if(!ListaSolicitudesPendientes.getInstancia().isEmpty()) {
                loader = VistaUtils.cargarVista("app/vista/VentanaAviso.fxml");
                vista = loader.load();
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
            // En caso de identificación incorrecta impresión del mensaje de error
            this.campoUsuario.setText("");
            this.campoPassword.setText("");
            FXMLLoader loader = VistaUtils.cargarVista("app/vista/VentanaAviso.fxml");
            Parent vista = loader.load();
            ControladorVentanaAviso controlador = loader.getController();
            controlador.setMensaje("Credenciales incorrectas o sesión ya iniciada.");
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
        hostServices.showDocument("192.168.43.96:80");
    }
    
    private String getLocalIp() throws SocketException {
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        while(e.hasMoreElements()) {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while(ee.hasMoreElements()) {
                InetAddress i = (InetAddress) ee.nextElement();
                if(this.regexp.matcher(i.getHostAddress()).matches()) {
                    return i.getHostAddress();
                }
            }
        }
        return null;
    }        
}
