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

package app.vista;

import app.ImplSuscriptorRR;
import app.InterfazServidorRR;
import app.modelo.Dato;
import app.modelo.ListaDatos;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 * @version 1.0
 * @since 2017-03
 */
public class ControladorVistaGeneral {
    
    private InterfazServidorRR interfazServidor;
    private ImplSuscriptorRR instanciaObjetoRemoto;
    private Pattern regexp;  
    
    @FXML private LineChart graficaRR; 
    @FXML private TableView<Dato> tablaDatos;
    @FXML private TableColumn<Dato, String> columnaPaso;
    @FXML private TableColumn<Dato, Float> columnaDato;
    @FXML private TextField campoTiempo;
    @FXML private Label etiquetaTiempoRestante;
    
    @FXML 
    private void initialize() {          
        try {
            // Obtención de una instancia de la interfaz remota y definición de la regexp
            String portNum = "1099";
            String registryURL = "rmi://localhost:" + portNum + "/ServidorRR";
            this.interfazServidor = (InterfazServidorRR)Naming.lookup(registryURL);
            regexp = Pattern.compile("^[1-9][0-9]*$");            
            // Asociación entre modelo y vista para tabla
            this.tablaDatos.setItems(ListaDatos.getInstancia().getListaDatos());
            this.columnaPaso.setCellValueFactory(dato -> dato.getValue().getPaso());
            this.columnaDato.setCellValueFactory(dato -> dato.getValue().getDato().asObject());
            // Asociación entre modelo y vista para la gráfica + ajustes
            this.graficaRR.getData().add(ListaDatos.getInstancia().getSeries());
            this.graficaRR.setCreateSymbols(false);
            NumberAxis ejeY = (NumberAxis)this.graficaRR.getYAxis();
            ejeY.setAutoRanging(false);
            ejeY.setLowerBound(0.5);
            ejeY.setUpperBound(0.75);
            ejeY.setTickUnit(0.05);
            // Asociación entre el modelo y el tiempo restante
            this.etiquetaTiempoRestante.textProperty().bind(ListaDatos.getInstancia().getTiempoRestante());
        }
        catch (NotBoundException | MalformedURLException | RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @FXML
    private void suscribirse() throws RemoteException {
        if(regexp.matcher(campoTiempo.getText()).matches()) {
            if(this.instanciaObjetoRemoto == null) {
                this.instanciaObjetoRemoto = new ImplSuscriptorRR();
            }
            this.interfazServidor.suscribirse(this.instanciaObjetoRemoto, 
                    Integer.parseInt(campoTiempo.getText()));
            this.campoTiempo.setText("");
        }
        else {
            campoTiempo.setText("");
        }
    }
    
    @FXML
    private void reiniciar() throws RemoteException {
        try {
            // El cliente limpia los datos en medio de una suscripción
            this.interfazServidor.cancelarSuscripcion(this.instanciaObjetoRemoto);
            ListaDatos.getInstancia().limpiaDatos();
            campoTiempo.setText("");            
            ListaDatos.getInstancia().refrescaTiempoRestante();
        } catch(RemoteException e) {
            // El cliente limpia los datos una vez que la suscripción ha finalizado
            ListaDatos.getInstancia().limpiaDatos();
            campoTiempo.setText("");            
            ListaDatos.getInstancia().refrescaTiempoRestante();
        }
    }    
    
    @FXML
    private void salir() throws RemoteException {
        try {
            // El cliente sale en medio de una suscripción
            this.interfazServidor.cancelarSuscripcion(this.instanciaObjetoRemoto);
            Platform.exit();
        } catch(RemoteException e) {
            // El cliente sale sin suscripción activa
            Platform.exit();
        }
    }    
    
}