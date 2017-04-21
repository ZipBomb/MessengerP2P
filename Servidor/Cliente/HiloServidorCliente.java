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
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 */

// Hilo que atiende a los mensajes que llegan desde el servidor
public class HiloServidorCliente {
    
    private IClienteServidor interfazServidor;
    private IServidorCliente interfazCliente;
    
    public HiloServidorCliente() throws RemoteException {
        try {
            //System.setProperty("java.rmi.server.hostname","192.168.43.214");
            String registroURL = "rmi://localhost:1099/Messenger";
            this.interfazServidor = (IClienteServidor)Naming.lookup(registroURL);
            System.out.println("Cliente Listo\n");  
        } catch (MalformedURLException | NotBoundException | RemoteException ex) {
            System.out.println(ex.getMessage());
        }  
        this.interfazCliente = new ServidorCliente();
    }

    public ArrayList<Amigo> conectarse(String nick, String password) throws RemoteException {
        Usuario[] misAmigos = interfazServidor.conectarse(nick, password, "192.168.43.146", "7777", this.interfazCliente);
        if(misAmigos.length == 1 && misAmigos[0].getNick() == null) {
            return null;
        }
        ArrayList<Amigo> listaAmigos = new ArrayList<>();
        for(Usuario aux : misAmigos) {
            listaAmigos.add(new Amigo(aux.getNick(), aux.isConectado(), aux.getIp(), aux.getPuerto()));            
        }
        return listaAmigos;
    }
    
}