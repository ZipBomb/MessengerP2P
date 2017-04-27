package app.controlador;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClienteServidor extends Remote {
    public Usuario[] conectarse (String nick, String password, IServidorCliente i, IComunicacionCliente c) throws RemoteException;    public Usuario[] buscarUsuarios (String cadenaBusqueda) throws RemoteException;
    public boolean registrarUsuario (String nick, String password, String ip, String puerto) throws RemoteException;
    public void modificarPassword (String nick, String passwordNueva, String passwordVieja) throws RemoteException;
    public void anhadirAmigo (String nick, String amigo) throws RemoteException;
    public void borrarAmigo (String nick, String amigo) throws RemoteException;
    public void aceptarPeticion (String nick, String amigo) throws RemoteException;
    public void rechazarPeticion (String nick, String amigo) throws RemoteException;
    public void desconectarse (String nick) throws RemoteException;
    public void cambiarIP (String nick, String ipNueva) throws RemoteException;
}
