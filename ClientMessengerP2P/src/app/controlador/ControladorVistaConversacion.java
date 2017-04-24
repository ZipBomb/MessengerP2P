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
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 */
public class ControladorVistaConversacion {
    
    private Conversacion conversacionActual;
    private DataOutputStream salida;
    private Socket socketSalida;
    
    @FXML private TableView<Mensaje> listaMensajes;
    @FXML private TableColumn<Mensaje, String> columnaMensajes;  
    @FXML private TextArea cajaTexto;
    
    @FXML
    public void initialize() {
        this.listaMensajes.setPlaceholder(new Label("Todavía no habéis enviado ningún mensaje"));
        
    }    

    @FXML
    private void enviarMensaje() throws IOException {
        this.enviarTexto(this.cajaTexto.getText());
        this.conversacionActual.anhadirMensaje(
            new Mensaje(UsuarioActual.getInstancia().getUsuarioActual(), this.cajaTexto.getText())
        );
        this.cajaTexto.setText("");
    }
    
    @FXML
    private void adjuntarArchivo() {
        
    }    
    
    public void initData(Conversacion conversacion) throws IOException {
        this.conversacionActual = conversacion;
        Amigo destinatario = conversacion.getDestinatario();
        this.socketSalida = new Socket(destinatario.getIp(), Integer.parseInt(destinatario.getPuerto()));
        this.salida = new DataOutputStream(socketSalida.getOutputStream()); 
        this.listaMensajes.setItems(conversacion.getConversacion());
        this.columnaMensajes.setCellValueFactory(mensaje -> mensaje.getValue().getContenido());
    }
    
    public void enviarTexto(String texto) throws IOException {
        String cabecera = UsuarioActual.getInstancia().getUsuarioActual().getNick().getValue() 
                            + ".Text." + texto.length() + "." + texto;
        byte[] paquete = cabecera.getBytes();
        this.salida.write(paquete);
    }
    
    public DataOutputStream getSalida() {
        return this.salida;
    }
    
}
