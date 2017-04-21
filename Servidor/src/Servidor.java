

import app.controlador.ClienteServidor;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;



public class Servidor {

    public static void main(String args[]) throws RemoteException {
        String registroURL;
        //System.setProperty("java.rmi.server.hostname","192.168.43.214");
        try{                         
            iniciarRegistro(1099);            
            registroURL = "rmi://localhost:1099/Messenger";
            ClienteServidor objetoExportado = new ClienteServidor();
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
