package app.controlador;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
/**
 *
 * @author pablo
 */
public class IClienteServidorImpl extends UnicastRemoteObject implements IClienteServidor {
    private ModeloUsuarios modeloUsuarios;  
    private HashMap<String, IServidorCliente> clientes;
    
    public IClienteServidorImpl() throws RemoteException {
        this.modeloUsuarios = new ModeloUsuarios(); 
        this.clientes = new HashMap<>();
    }
        
    @Override
    public synchronized Usuario[] conectarse(String nick, String password, String ip, String puerto, IServidorCliente interfaz) throws RemoteException {
        Usuario[] amigos = modeloUsuarios.conectarse(nick, password, ip, puerto);               
        if(amigos == null)
            return null;        
        else{                
            clientes.put(nick, interfaz);
            Usuario usuario = new Usuario(nick, ip, puerto, true);
            for(int i = 0; i < amigos.length; i++){
                if(amigos[i].isConectado()){
                    IServidorCliente aux = clientes.get(amigos[i].getNick());
                    aux.notificarConexion(usuario);
                }
            }
            Usuario[] peticiones = modeloUsuarios.recpuerarSolicitudesAmistad(nick); 
            interfaz.notificarSolicitudesPendientes(peticiones);            
            return amigos;
        }         
    }

    @Override
    public synchronized Usuario[] buscarUsuarios(String cadenaBusqueda) throws RemoteException {
        return modeloUsuarios.buscarUsuarios(cadenaBusqueda);
    }        

    @Override
    public synchronized boolean registrarUsuario(String nick, String password, String ip, String puerto) throws RemoteException {
        return modeloUsuarios.registrarUsuario(nick, password, ip, puerto);
    }

    @Override
    public synchronized void modificarPassword(String nick, String passwordNueva, String passwordVieja) throws RemoteException {
        modeloUsuarios.modificarPassword(nick, passwordNueva, passwordVieja);               
    }
   
    @Override
    public synchronized void anhadirAmigo(String nick, String amigo) throws RemoteException {
        modeloUsuarios.guardarPeticionAmistad(nick, amigo);
        if(modeloUsuarios.estaOnline(amigo)){
            Usuario[] peticiones = modeloUsuarios.recpuerarSolicitudesAmistad(amigo);
            clientes.get(amigo).notificarSolicitudesPendientes(peticiones);
        }                    
    }

    
    @Override
    public synchronized void borrarAmigo(String nick, String amigo) throws RemoteException {
        modeloUsuarios.borrarAmigo(nick, amigo);
        if(modeloUsuarios.estaOnline(amigo)){
            Usuario usuario = modeloUsuarios.recuperarUsuario(nick);
            clientes.get(amigo).notificarEliminacion(usuario);
        }
    }

    @Override
    public void aceptarPeticion(String nick, String amigo) throws RemoteException {
        modeloUsuarios.eliminarPeticion(nick, amigo);
        modeloUsuarios.anhadirAmigo(nick, amigo);
        if(clientes.get(amigo) != null){
            Usuario usuario = modeloUsuarios.recuperarUsuario(nick);
            clientes.get(amigo).notificarNuevaAmistad(usuario);
        }
    }

    @Override
    public void rechazarPeticion(String nick, String amigo) throws RemoteException {
        modeloUsuarios.eliminarPeticion(nick, amigo);
    }        

    @Override
    public synchronized void desconectarse(String nick) throws RemoteException {
        Usuario[] amigos = modeloUsuarios.desconectarse(nick);
        Usuario usuario = new Usuario(nick, null, null, false);
        if(amigos != null){
            for(int i = 0; i < amigos.length; i++)
                clientes.get(amigos[i].getNick()).notificarDesconexion(usuario);
        }
        clientes.remove(nick);
    }

    @Override
    public synchronized void cambiarIP(String nick, String ipNueva) throws RemoteException {
        modeloUsuarios.cambiarIP(nick, ipNueva);
        Usuario usuario = modeloUsuarios.recuperarUsuario(nick);
        Usuario[] amigos = modeloUsuarios.recuperarAmigos(nick);
        for(int i = 0; i < amigos.length; i++)
            clientes.get(amigos[i].getNick()).notificarConexion(usuario);
    }
   
}