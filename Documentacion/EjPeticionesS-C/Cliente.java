/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pablo
 */


import ClienteP2P.*;
import java.util.Properties;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
 

public class Cliente 
{
    public static void main(String args[])
    {
        try{
            Properties prop = new Properties();
            prop.put("org.omg.CORBA.ORBInitialHost", "localhost");
            prop.put("org.omg.CORBA.ORBInitialPort", "6666");
            ORB orb = ORB.init(args, prop);
            System.out.println("Initialized ORB");

            //Instantiate Servant and create reference
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            IServidorClienteImpl listener  = new IServidorClienteImpl();
            rootPOA.activate_object(listener);
            IServidorCliente ref = IServidorClienteHelper.narrow(rootPOA.servant_to_reference(listener));

            //Resolve MessageServer
            IClienteServidor msgServer = IClienteServidorHelper.narrow(orb.string_to_object("corbaname:iiop:1.2@localhost:6666#MessageServer"));

            //Register listener reference (callback object) with MessageServer            
            System.out.println("Listener registered with MessageServer");

            //Activate rootpoa
            rootPOA.the_POAManager().activate();

            //Wait for messages
            System.out.println("Wait for incoming messages");
            
            msgServer.conectarse("", "", "", "", ref);
            
            orb.run();
             
        } catch (Exception e) {
            System.out.println("ERROR : " + e) ;
            e.printStackTrace(System.out);
        }
    }
}