package app.controlador;


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
    
    public void notificarSolicitudesPendientes (Usuario[] solicitudesPendientes);
    public void notificarConexion (Usuario usuario);
    public void notificarDesconexion (Usuario usuario);
    public void notificarNuevaAmistad (Usuario usuario);
    public void notificarNuevaSolicitud (Usuario usuario);
    public void actualizaIp (Usuario usuario);
}
