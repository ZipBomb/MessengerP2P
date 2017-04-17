import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;



public class Servidor {

    public static void main(String args[]) {
        String registroURL;
        try{                         
            int RMIPuerto = 1099;
            iniciarRegistro(RMIPuerto);            
            registroURL = "rmi://192.168.0.17:1099/Messenger";
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
