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
import app.modelo.ListaSolicitudesPendientes;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 * @version 1.0
 * @since 2017-04
 */
public class ControladorVistaSolicitudes {

    @FXML private TableView<Amigo> listaSolicitudesPendientes;
    @FXML private TableColumn<Amigo, String> columnaSolicitudes;
    
    @FXML
    private void initialize() {
        this.listaSolicitudesPendientes.setItems(ListaSolicitudesPendientes.getInstancia().getListaSolicitudesPendientes());
        this.columnaSolicitudes.setCellValueFactory(solicitud -> solicitud.getValue().getNick());
        this.listaSolicitudesPendientes.setPlaceholder(new Label("No tienes ninguna solicitud pendiente"));    
    }
    
    @FXML
    private void cerrar() {
        Stage stage = (Stage) this.listaSolicitudesPendientes.getScene().getWindow();
        stage.close();      
    }

    @FXML
    private void aceptar() {
        Stage stage = (Stage) this.listaSolicitudesPendientes.getScene().getWindow();
        stage.close();     
    }

    @FXML
    private void rechazar() {
        Stage stage = (Stage) this.listaSolicitudesPendientes.getScene().getWindow();
        stage.close();     
    }    
    
}
