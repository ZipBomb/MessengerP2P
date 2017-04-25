

import app.controlador.IClienteServidorImpl;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;



public class Servidor {

    public static void main(String args[]) throws RemoteException {
        String registroURL;
        System.setProperty("java.rmi.server.hostname","34.251.225.12");
        try{                         
            iniciarRegistro(1099);            
            registroURL = "rmi://34.251.225.12:1099/Messenger";
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
}
