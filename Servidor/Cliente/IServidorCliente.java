package app.controlador;


import app.controlador.Usuario;
import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pablo
 */
public interface IServidorCliente extends Remote {
    
    public void notificarSolicitudesPendientes (Usuario[] solicitudesPendientes) throws RemoteException;
    public void notificarConexion (Usuario usuario) throws RemoteException;
    public void notificarDesconexion (Usuario usuario) throws RemoteException;
    public void notificarNuevaAmistad (Usuario usuario) throws RemoteException;
    public void notificarNuevaSolicitud(Usuario usuario) throws RemoteException;
    public void actualizaIp(Usuario usuario) throws RemoteException;
   
}
