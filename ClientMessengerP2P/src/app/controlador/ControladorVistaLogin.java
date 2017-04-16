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
import app.vista.VistaUtils;
import java.io.IOException;
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
    
    }
    
    @FXML
    private void iniciarSesion() throws IOException {
        // Llamada a método remoto con comprobacion de login
        if(true) {
            try {
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
            
//            Lanzamiento del hilo de escucha
//            String[] args = {"", ""};
//            HiloEscucha hiloEscucha = new HiloEscucha(args);
//            hiloEscucha.start();
            
            Stage stage = (Stage)this.campoPassword.getScene().getWindow();
            FXMLLoader loader = VistaUtils.cargarVista("app/vista/VistaGeneral.fxml");
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
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
