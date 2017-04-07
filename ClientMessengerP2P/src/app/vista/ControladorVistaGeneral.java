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

import ClienteP2P.IServidorClientePOA;
import ClienteP2P.Usuario;
import javafx.fxml.FXML;
import org.omg.CORBA.ORB;

/**
 * FXML Controller class
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 * @version 1.0
 * @since 2017-04
 */

class IServidorClienteImpl extends IServidorClientePOA {
    private ORB orb;

    public void setORB(ORB orb_val) {
        orb = orb_val; 
    }

    @Override
    public void notificarSolicitudesPendientes(Usuario[] solicitudesPendientes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notificarConexion(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notificarDesconexion(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notificarNuevaAmistad(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

public class ControladorVistaGeneral {
    
    @FXML
    private void remove() {
    
    }
    
}
