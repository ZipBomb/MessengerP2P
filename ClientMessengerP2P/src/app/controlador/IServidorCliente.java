package app.controlador;
/**
 *
 * @author pablo
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServidorCliente extends Remote {
    public void notificarSolicitudesPendientes (Usuario[] solicitudesPendientes) throws RemoteException;
    public void notificarConexion (Usuario usuario) throws RemoteException;
    public void notificarDesconexion (Usuario usuario) throws RemoteException;
    public void notificarNuevaAmistad (Usuario usuario) throws RemoteException;
    public void notificarNuevaSolicitud (Usuario usuario) throws RemoteException;
    public void actualizaIp (Usuario usuario) throws RemoteException;
}
