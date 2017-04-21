package app.controlador;


import app.controlador.IServidorCliente;
import app.controlador.Usuario;
import app.modelo.Amigo;
import app.modelo.ListaAmigosOff;
import app.modelo.ListaAmigosOn;
import app.modelo.ListaSolicitudesPendientes;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pablo
 */
public class ServidorCliente extends UnicastRemoteObject implements IServidorCliente {
    
    public ServidorCliente() throws RemoteException {
        
    }

    @Override
    public void notificarSolicitudesPendientes(Usuario[] solicitudesPendientes) {
        Amigo amigo;
        for(Usuario usuario : solicitudesPendientes) {
            amigo = new Amigo(usuario.getNick(), usuario.isConectado(), usuario.getIp(), usuario.getPuerto());
            try {
                ListaSolicitudesPendientes.getInstancia().anhadirAmigo(amigo);
            } catch(Exception ex) {
                System.out.println("ERROR: " + ex.getMessage());
            } 
        }
    }

    @Override
    public void notificarConexion(Usuario usuario) {
        Amigo amigo = new Amigo(usuario.getNick(), usuario.isConectado(), usuario.getIp(), usuario.getPuerto());
        try {
            ListaAmigosOff.getInstancia().eliminarAmigo(amigo);
            ListaAmigosOn.getInstancia().anhadirAmigo(amigo);
        } catch(Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    @Override
    public void notificarDesconexion(Usuario usuario) {
        Amigo amigo = new Amigo(usuario.getNick(), usuario.isConectado(), usuario.getIp(), usuario.getPuerto());
        try {
            ListaAmigosOn.getInstancia().eliminarAmigo(amigo);
            ListaAmigosOff.getInstancia().anhadirAmigo(amigo);
        } catch(Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    @Override
    public void notificarNuevaAmistad(Usuario usuario) {
        Amigo amigo = new Amigo(usuario.getNick(), usuario.isConectado(), usuario.getIp(), usuario.getPuerto());
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
        Amigo amigo = new Amigo(usuario.getNick(), usuario.isConectado(), usuario.getIp(), usuario.getPuerto());
        try {
            ListaSolicitudesPendientes.getInstancia().anhadirAmigo(amigo);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void actualizaIp(Usuario usuario) {
        try {
            ListaAmigosOn.getInstancia().modificaIp(usuario.getNick(), usuario.getIp());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }        
    }
    
}
