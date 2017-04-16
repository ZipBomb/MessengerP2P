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

import app.modelo.Conversacion;
import app.modelo.Mensaje;
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
    
    @FXML private TableView<Mensaje> listaMensajes;
    @FXML private TableColumn<Mensaje, String> columnaMensajes;  
    @FXML private TextArea cajaTexto;
    
    @FXML
    public void initialize() {
        this.listaMensajes.setPlaceholder(new Label("Todavía no habéis enviado ningún mensaje"));
    }    

    @FXML
    private void enviarMensaje() {
        this.conversacionActual.anhadirMensaje(
                new Mensaje(this.conversacionActual.getDestinatario(), this.cajaTexto.getText())
        );
        this.cajaTexto.setText("");
    }
    
    public void initData(Conversacion conversacion) {
        this.conversacionActual = conversacion;
        this.listaMensajes.setItems(conversacion.getConversacion());
        this.columnaMensajes.setCellValueFactory(mensaje -> mensaje.getValue().getContenido());    
    }
    
}
