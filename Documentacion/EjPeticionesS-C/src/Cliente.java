import ClienteP2P.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;


class IServidorClienteImpl extends IServidorClientePOA {
    private ORB orb;

    public void setORB(ORB orb_val) {
        orb = orb_val; 
    }
    
    @Override
    public void prueba(String str) {
        System.out.println(str);
    }
    
    @Override
    public void shutdown() {
        orb.shutdown(false);
    }

    @Override
    public void notificarSolicitudesPendientes(Usuario[] solicitudesPendientes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notificarConexion(Usuario usuario) {
        if(usuario.conectado) {
            System.out.println("Hemos triunfado");
        }
    }

    @Override
    public void notificarDesconexion(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notificarNuevaAmistad(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}


public class Cliente {

  public static void main(String args[]) {
    try{
      // create and initialize the ORB
      ORB orb = ORB.init(args, null);

      // get reference to rootpoa & activate the POAManager
      POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
      rootpoa.the_POAManager().activate();

      // create servant and register it with the ORB
      IServidorClienteImpl helloImpl = new IServidorClienteImpl();
      helloImpl.setORB(orb); 

      // get object reference from the servant
      org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloImpl);
      IServidorCliente href = IServidorClienteHelper.narrow(ref);
          
      // get the root naming context
      // NameService invokes the name service
      org.omg.CORBA.Object objRef =
          orb.resolve_initial_references("NameService");
      // Use NamingContextExt which is part of the Interoperable
      // Naming Service (INS) specification.
      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

      // bind the Object Reference in Naming
      String name = "Hello";
      NameComponent path[] = ncRef.to_name( name );
      ncRef.rebind(path, href);

      System.out.println("HelloServer ready and waiting ...");

      // wait for invocations from clients
      orb.run();
    } 
        
      catch (Exception e) {
        System.err.println("ERROR: " + e);
        e.printStackTrace(System.out);
      }
          
      System.out.println("HelloServer Exiting ...");
        
  }
}
