import ClienteP2P.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;

public class Servidor
{
  static IServidorCliente helloImpl;
  
  public static void main(String args[])
    {
      try{
        // create and initialize the ORB
        ORB orb = ORB.init(args, null);

        // get the root naming context
        org.omg.CORBA.Object objRef = 
            orb.resolve_initial_references("NameService");
        // Use NamingContextExt instead of NamingContext. This is 
        // part of the Interoperable naming Service.  
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
 
        // resolve the Object Reference in Naming
        String name = "Hello";
        helloImpl = IServidorClienteHelper.narrow(ncRef.resolve_str(name));
    
        Usuario pepe = new Usuario("pepe", "localhost", "nose", true);
        helloImpl.prueba("HOLA");
        helloImpl.notificarConexion(pepe);
        //helloImpl.shutdown();

        } catch (Exception e) {
          System.out.println("ERROR : " + e) ;
          e.printStackTrace(System.out);
        }
    }

}