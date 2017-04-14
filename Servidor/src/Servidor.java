import ClienteP2P.*;
import java.util.Properties;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;


public class Servidor {

    public static void main(String args[]) {
        try{
            // create and initialize the ORB
            Properties prop = new Properties();
            prop.put("org.omg.CORBA.ORBInitialHost", "localhost");
            prop.put("org.omg.CORBA.ORBInitialPort", "6666");
           // prop.put("org.omg.CORBA.ORBDefaultInitRef", "corbaloc::localhost:2809");
            
            ORB orb = ORB.init(args, prop);
            System.out.println("Initialized ORB");

            //Instantiate Servant and create reference
            POA rootPOA = POAHelper.narrow(
                orb.resolve_initial_references("RootPOA"));
            IClienteServidorImpl servidor = new IClienteServidorImpl();
            rootPOA.activate_object(servidor);
            IClienteServidor interfaz = IClienteServidorHelper.narrow(rootPOA.servant_to_reference(servidor));

            //Bind reference with NameService
            NamingContext namingContext = NamingContextHelper.narrow(
                orb.resolve_initial_references("NameService"));
            System.out.println("Resolved NameService");
            NameComponent[] nc = { new NameComponent("MessageServer", "") };
            namingContext.rebind(nc, interfaz);

            //Activate rootpoa
            rootPOA.the_POAManager().activate();

            //Start readthread and wait for incoming requests            
            System.out.println("Server ready and running ....");
            
            orb.run();
        } 

          catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
          }

          System.out.println("HelloServer Exiting ...");          
    }
}
