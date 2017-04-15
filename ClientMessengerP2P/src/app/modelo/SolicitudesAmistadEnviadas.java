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

import java.util.ArrayList;

/**
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 */
public class SolicitudesAmistadEnviadas {
    
    private final ArrayList<Amigo> solicitudesEnviadas;
    private static final SolicitudesAmistadEnviadas INSTANCIA = new SolicitudesAmistadEnviadas();

    public SolicitudesAmistadEnviadas() {
        this.solicitudesEnviadas = new ArrayList();
    }
    
    public static SolicitudesAmistadEnviadas getInstancia() {
        return INSTANCIA;
    }
    
    public void anhadirSolicitud(Amigo usuario) {
        if(!yaSeEnvio(usuario)) {
            this.solicitudesEnviadas.add(usuario);
        }
    }
    
    public boolean yaSeEnvio(Amigo usuario) {
        for (Amigo aux : this.solicitudesEnviadas) {
            if(usuario.getNick().getValue().equals(aux.getNick().getValue())) {
                return true;
            }
        }
        return false;
    }    
    
}
