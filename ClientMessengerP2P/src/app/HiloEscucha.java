/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import ClienteP2P.*;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.*;

/**
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 */

// Implementación del archivo .idl
class IServidorClienteImpl extends IServidorClientePOA {
    private ORB orb;

    public void setORB(ORB orb_val) {
        orb = orb_val; 
    }

    @Override
    public void notificarSolicitudesPendientes(Usuario[] solicitudesPendientes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notificarConexion(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet.");
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

// Hilo que atiende a los mensajes que llegan desde el servidor
public class HiloEscucha extends Thread {

    private String[] parametrosConexion;
    
    public HiloEscucha(String[] parametrosConexion) {
        super();
        setParametrosConexion(parametrosConexion);
    }

    private void setParametrosConexion(String[] parametrosConexion) {
        if(parametrosConexion.length > 0) {
            this.parametrosConexion = parametrosConexion;
        }
    }

    @Override
    public void run() {
        try {
            // create and initialize the ORB
            ORB orb = ORB.init(parametrosConexion, null);

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
        } catch (Exception e) {
          System.err.println("ERROR: " + e);
          e.printStackTrace(System.out);
        }
        System.out.println("HelloServer Exiting ..."); 
    }
    
}
