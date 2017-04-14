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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 */
public class Amigo {
    private final StringProperty nick;
    private final boolean conectado;    
    private final String ip;
    private final String puerto;

    public Amigo(String nick, boolean conectado, String ip, String puerto) {
        this.nick = new SimpleStringProperty(nick);
        this.conectado = conectado;
        this.ip = ip;
        this.puerto = puerto;
    }

    public StringProperty getNick() {
        return nick;
    }

    public boolean estaConectado() {
        return conectado;
    }

    public String getIp() {
        return ip;
    }

    public String getPuerto() {
        return puerto;
    }
 
}
