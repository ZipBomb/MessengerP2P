

import app.controlador.IClienteServidorImpl;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;
import java.util.regex.Pattern;



public class Servidor {

    public static void main(String args[]) throws RemoteException, SocketException {
        String registroURL;
        System.setProperty("java.rmi.server.hostname",getLocalIp());
        try{                         
            iniciarRegistro(1099);            
            registroURL = "rmi://localhost:1099/Messenger";
            IClienteServidorImpl objetoExportado = new IClienteServidorImpl();
            Naming.rebind(registroURL, objetoExportado);
            System.out.println("Servidor Listo\n");            
        }
        catch (Exception re) {
            System.out.println("Excepcion en el Servidor: " + re);
        }        
    }
   
    private static void iniciarRegistro(int RMIPuerto) throws RemoteException{
        try {
            Registry registro = LocateRegistry.getRegistry(RMIPuerto);
            registro.list();                                
        }
        catch (RemoteException e) {                         
            Registry registro = LocateRegistry.createRegistry(RMIPuerto);            
        }
    }
    
    private static String getLocalIp() throws SocketException {
        Pattern regexp = Pattern.compile("^(192\\.168\\.).*");
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        while(e.hasMoreElements()) {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while(ee.hasMoreElements()) {
                InetAddress i = (InetAddress) ee.nextElement();
                if(regexp.matcher(i.getHostAddress()).matches()) {
                    return i.getHostAddress();
                }
            }
        }
        return null;
    }
}
