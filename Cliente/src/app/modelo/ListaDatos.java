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

package app.modelo;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

/**
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 * @version 1.0
 * @since 2017-03
 */
public class ListaDatos {
    
    private final ObservableList<Dato> listaDatos;
    private final XYChart.Series<String, Float> series;
    private final StringProperty tiempoRestante;
    private static final ListaDatos INSTANCIA = new ListaDatos();
    
    private ListaDatos() {
        this.listaDatos = FXCollections.observableArrayList();
        this.series = new XYChart.Series<>();
        this.series.setName("Intervalo RR");
        this.tiempoRestante = new SimpleStringProperty(Integer.toString(0));
    }
    
    public static ListaDatos getInstancia() {
        return INSTANCIA;
    }
    
    public ObservableList<Dato> getListaDatos() {
        return listaDatos;
    }

    public Series<String, Float> getSeries() {
        return series;
    }
  
    public StringProperty getTiempoRestante() {
        return tiempoRestante;
    }
    
    public void refrescaTiempoRestante() {
        this.tiempoRestante.setValue("0");
    }

    public void anhadirDato(Float dato, int tiempoRestante) {
        // Añadir dato al observableList correspondiente a la tabla
        this.listaDatos.add(new Dato(String.valueOf(listaDatos.size()), dato));
        // Añadir datos al contenido de la gráfica
        Platform.runLater(() -> {
            this.tiempoRestante.setValue(Integer.toString(tiempoRestante));
            Dato aux = this.listaDatos.get(this.listaDatos.size() - 1);
            series.getData().add(                            
                new XYChart.Data(aux.getPaso().getValue(), 
                aux.getDato().getValue()));
            if(this.listaDatos.size() > 60) {
                series.getData().remove(0);
            }
        });
    }
    
    public void limpiaDatos() {
        this.listaDatos.clear();
        this.series.getData().clear();
    }
    
}
