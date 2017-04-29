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
import app.modelo.ListaAmigosOff;
import app.modelo.ListaAmigosOn;
import app.modelo.ListaSolicitudesPendientes;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 */
public class IServidorClienteImpl extends UnicastRemoteObject implements IServidorCliente {

    public IServidorClienteImpl() throws RemoteException {}
    
    @Override
    public void notificarSolicitudesPendientes(Usuario[] solicitudesPendientes) {
        Amigo amigo;
        for(Usuario usuario : solicitudesPendientes) {
            amigo = new Amigo(usuario.getNick(), usuario.isConectado(), usuario.getInterfaz());
            try {
                ListaSolicitudesPendientes.getInstancia().anhadirAmigo(amigo);
            } catch(Exception ex) {
                System.out.println("ERROR: " + ex.getMessage());
            } 
        }
    }

    @Override
    public void notificarConexion(Usuario usuario) {
        Amigo amigo = new Amigo(usuario.getNick(), usuario.isConectado(), usuario.getInterfaz());
        try {
            ListaAmigosOff.getInstancia().eliminarAmigo(amigo);
            ListaAmigosOn.getInstancia().anhadirAmigo(amigo);
            ListaAmigosOff.getInstancia().desbloqueaVentanaConexion(amigo);
        } catch(Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    @Override
    public void notificarDesconexion(Usuario usuario) {
        Amigo amigo = new Amigo(usuario.getNick(), usuario.isConectado(), usuario.getInterfaz());
        try {
            ListaAmigosOn.getInstancia().eliminarAmigo(amigo);
            ListaAmigosOff.getInstancia().anhadirAmigo(amigo);
            ListaAmigosOff.getInstancia().bloqueaVentanaDesconexion(amigo);       
        } catch(Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    @Override
    public void notificarNuevaAmistad(Usuario usuario) {
        Amigo amigo = new Amigo(usuario.getNick(), usuario.isConectado(), usuario.getInterfaz());
        try {
            if(amigo.estaConectado()) {
                ListaAmigosOn.getInstancia().anhadirAmigo(amigo);
            }
            else {
                ListaAmigosOff.getInstancia().anhadirAmigo(amigo);
            }
        } catch(Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());            
        }
    }

    @Override
    public void notificarNuevaSolicitud(Usuario usuario) {
        Amigo amigo = new Amigo(usuario.getNick(), usuario.isConectado(), usuario.getInterfaz());
        try {
            ListaSolicitudesPendientes.getInstancia().anhadirAmigo(amigo);
            ListaSolicitudesPendientes.getInstancia().notificaPeticion(amigo);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void notificarEliminacion(Usuario usuario) throws RemoteException {
        Amigo amigo = new Amigo(usuario.getNick(), usuario.isConectado(), usuario.getInterfaz());
        if(ListaAmigosOn.getInstancia().yaExiste(amigo)) {
            try {
                ListaAmigosOff.getInstancia().bloqueaVentanaDesconexion(amigo);
                ListaAmigosOn.getInstancia().eliminarAmigo(amigo);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        else if(ListaAmigosOff.getInstancia().yaExiste(amigo)) {
            try {
                ListaAmigosOff.getInstancia().eliminarAmigo(amigo);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }            
        }
    }
    
}
