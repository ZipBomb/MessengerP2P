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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 */
public class Conversacion {
    
    private final ObservableList<Mensaje> conversacion;
    private final Amigo destinatario;
    
    public Conversacion(Amigo destinatario) {
        this.destinatario = destinatario;
        this.conversacion = FXCollections.observableArrayList();
    }
    
    public ObservableList<Mensaje> getConversacion() {
        return this.conversacion;
    }
    
    public void anhadirMensaje(Mensaje mensaje) {
        this.conversacion.add(mensaje);
    }
    
    public Amigo getDestinatario() {
        return this.destinatario;
    }
    
}
