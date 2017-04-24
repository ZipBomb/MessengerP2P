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
import app.modelo.ListaResultadoBusqueda;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 */
public class HiloClienteServidor extends Thread {
    
    private IClienteServidor interfazServidor;
    private final int tipoOp;
    private final String[] args;
    
    public HiloClienteServidor(int tipoOp, String[] args) {
        try {
            String urlRegistro = "rmi://192.168.43.214:1099/Messenger";
            this.interfazServidor = (IClienteServidor) Naming.lookup(urlRegistro);
        } catch (MalformedURLException | NotBoundException | RemoteException ex) {
            System.out.println(ex.getMessage());
        }
        this.tipoOp = tipoOp;
        this.args = args;
    }
    
    @Override 
    public void run() {
        try {
            switch(this.tipoOp) {
                case 0: { // añadirAmigo(nick, nombreAmigo)
                    if(args.length == 2) {
                        this.anhadirAmigo(args[0], args[1]);
                    } 
                    break;
                }
                case 1: { // borrarAmigo(nick, nombreAmigo)
                    if(args.length == 2) {
                        this.borrarAmigo(args[0], args[1]);
                    } 
                    break;
                }
                case 2: { // desconectarse(nick)
                    if(args.length == 1) {
                        this.desconectarse(args[0]);
                    } 
                    break;
                }            
                case 3: { // notificarNuevaIp(nick, nuevaIP)
                    if(args.length == 2) {
                        this.notificarNuevaIp(args[0], args[1]);
                    } 
                    break;
                }
                case 4: { // buscarUsuarios(cadenaBusqueda)
                    if(args.length == 1) {
                        this.buscarUsuarios(args[0]);
                    } 
                    break;
                }     
                case 5: { // aceptarPeticion(nick, nombreAmigo)
                    if(args.length == 2) {
                        this.aceptarPeticion(args[0], args[1]);
                    } 
                    break;
                }     
                case 6: { // rechazarPeticion(nick, nombreAmigo)
                    if(args.length == 2) {
                        this.rechazarPeticion(args[0], args[1]);
                    } 
                    break;
                }                 
            }
        } catch(RemoteException e) {}
    }
    
    public void anhadirAmigo(String nick, String nombreAmigo) throws RemoteException {
        this.interfazServidor.anhadirAmigo(nick, nombreAmigo);
    }
    
    public void borrarAmigo(String nick, String nombreAmigo) throws RemoteException {
        this.interfazServidor.borrarAmigo(nick, nombreAmigo);
    }    
    
    public void desconectarse(String nick) throws RemoteException {
        this.interfazServidor.desconectarse(nick);
    }
    
    public void notificarNuevaIp(String nick, String nuevaIp) throws RemoteException {
        this.interfazServidor.cambiarIP(nick, nuevaIp);
    }
    
    public void buscarUsuarios(String cadenaBusqueda) throws RemoteException {
        Usuario[] resultados = this.interfazServidor.buscarUsuarios(cadenaBusqueda);
        for(Usuario aux : resultados) {
            try {
                ListaResultadoBusqueda.getInstancia().anhadirAmigo(
                    new Amigo(aux.getNick(), aux.isConectado(), aux.getIp(), aux.getPuerto())
                );
            } catch(Exception e) {}
        }
    }
    
    public void aceptarPeticion (String nick, String nombreAmigo) throws RemoteException {
        this.interfazServidor.aceptarPeticion(nick, nombreAmigo);
    }
    
    public void rechazarPeticion (String nick, String nombreAmigo) throws RemoteException {
        this.interfazServidor.rechazarPeticion(nick, nombreAmigo);
    }

}
