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

import app.controlador.ControladorVistaGeneral;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 * @version 1.0
 * @since 2017-04
 */
public class ListaSolicitudesPendientes {
    
    private final ObservableList<Amigo> listaSolicitudesPendientes;
    private static final ListaSolicitudesPendientes INSTANCIA = new ListaSolicitudesPendientes();
    public ObjectProperty<ControladorVistaGeneral> mainControllerProperty = new SimpleObjectProperty();    
    
    private ListaSolicitudesPendientes() {
        this.listaSolicitudesPendientes = FXCollections.observableArrayList();
    }
    
    public static ListaSolicitudesPendientes getInstancia() {
        return INSTANCIA;
    }

    public ObservableList<Amigo> getListaSolicitudesPendientes() {
        return listaSolicitudesPendientes;
    }
    
    public void anhadirAmigo(Amigo amigo) throws Exception {
        if(!yaExiste(amigo)) {
            this.listaSolicitudesPendientes.add(amigo);
        } else throw new Exception("El usuario ya estaba en la lista");
    }
    
    public void eliminarAmigo(Amigo amigo) throws Exception {
        if(yaExiste(amigo)) {
            for(Amigo aux : this.listaSolicitudesPendientes) {
                if(aux.getNick().getValue().equals(amigo.getNick().getValue())) {
                    this.listaSolicitudesPendientes.remove(aux);
                    break;
                }
            }
        }
        else throw new Exception("El usuario no estaba en la lista");
    }
    
    private boolean yaExiste(Amigo amigo) {
        for(Amigo aux : this.listaSolicitudesPendientes) {
            if(aux.getNick().getValue().equals(amigo.getNick().getValue())) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return this.listaSolicitudesPendientes.isEmpty();
    }
    
    public void notificaPeticion(Amigo amigo) throws IOException {
        Platform.runLater(new Runnable(){
             @Override public void run() {            
                 try {
                     mainControllerProperty.getValue().notificaNuevaSolicitud(amigo);
                 } catch (IOException ex) {
                     System.out.println(ex.getMessage());
                 }
             }
        });        
    }
    
}
