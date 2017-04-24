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

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 */
public class Mensaje {
    
    private final Amigo emisor;
    private final Date fecha;
    private final StringProperty contenido;

    public Mensaje(Amigo emisor, String mensaje) {
        this.emisor = emisor;
        this.fecha = new Date();
        String contenidoFecha = new SimpleDateFormat("[HH:mm:ss] ").format(this.fecha);   
        this.contenido = new SimpleStringProperty(contenidoFecha 
                + "@" + this.emisor.getNick().getValue()
                + " dijo: " + mensaje);
    }
    
    public Amigo getEmisor() {
        return emisor;
    }

    public Date getFecha() {
        return fecha;
    }

    public StringProperty getContenido() {
        return contenido;
    }
    
}
