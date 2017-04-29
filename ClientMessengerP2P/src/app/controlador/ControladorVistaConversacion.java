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
import app.modelo.Conversacion;
import app.modelo.Mensaje;
import app.modelo.UsuarioActual;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 */
public class ControladorVistaConversacion {
    
    private Conversacion conversacionActual;
    
    @FXML private TableView<Mensaje> listaMensajes;
    @FXML private TableColumn<Mensaje, String> columnaMensajes;  
    @FXML private TextArea cajaTexto;
    @FXML private Button botonEnviar;
    
    @FXML
    public void initialize() {
        this.listaMensajes.setPlaceholder(new Label("Todavía no habéis enviado ningún mensaje"));    
        this.columnaMensajes.setText("Tu amigo está conectado");
        this.listaMensajes.setSelectionModel(null);
        this.listaMensajes.setStyle("-fx-border-color: green");      
    }

    @FXML
    private void enviarMensaje() throws IOException {
        this.enviarTexto(this.cajaTexto.getText(), this.conversacionActual.getDestinatario());
        this.conversacionActual.anhadirMensaje(
            new Mensaje(UsuarioActual.getInstancia().getUsuarioActual(), this.cajaTexto.getText())
        );
        this.cajaTexto.setText("");
    }
    
    @FXML
    private void adjuntarArchivo() throws IOException {
        FileChooser fileChooser = new FileChooser();        
        File myFile = fileChooser.showOpenDialog(null);    
        if(myFile != null) {
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
            for(int i = cabecera.length(); i < arrayArchivo.length + cabecera.length() - 1; i++){                    
                paquete[i] = arrayArchivo[j];                    
                j++;
            }
            // Actualización de la vista
            this.conversacionActual.anhadirMensaje(
                new Mensaje(UsuarioActual.getInstancia().getUsuarioActual(), "Acabo de enviarte " + nombre)
            );
            // Llamada al controlador para enviar el paquete y cierre de ventana
            this.enviarData(
                    paquete, 
                    this.conversacionActual.getDestinatario()
            ); 
        }       
    }    
    
    public void initData(Conversacion conversacion) throws IOException {
        this.conversacionActual = conversacion;
        this.listaMensajes.setItems(conversacion.getConversacion());
        this.columnaMensajes.setCellValueFactory(mensaje -> mensaje.getValue().getContenido());
    }
    
    public void enviarTexto(String texto, Amigo destinatario) throws IOException {
        String nickUsuarioActual = UsuarioActual.getInstancia().getUsuarioActual().getNick().getValue();
        destinatario.getInterfaz().enviarTexto(texto, nickUsuarioActual);
    }
    
    public void enviarData(byte[] paquete, Amigo destinatario) throws IOException {
        String nickUsuarioActual = UsuarioActual.getInstancia().getUsuarioActual().getNick().getValue();        
        destinatario.getInterfaz().enviarArchivo(paquete, nickUsuarioActual);
    }
    
    public void bloqueaConversacion() {
        this.botonEnviar.setDisable(true);
        this.cajaTexto.setDisable(true);
        this.listaMensajes.setStyle("-fx-border-color: red");
        this.columnaMensajes.setText("Tu amigo se ha desconectado");
    }
}
