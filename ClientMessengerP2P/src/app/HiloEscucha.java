/*
 * Copyright (C) 2017 Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package app;

import ClienteP2P.*;
import app.modelo.Amigo;
import app.modelo.ListaAmigosOff;
import app.modelo.ListaAmigosOn;
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
        Amigo amigo = new Amigo(usuario.nick, usuario.conectado, usuario.ip, usuario.puerto);
        try {
            ListaAmigosOff.getInstancia().eliminarAmigo(amigo);
            ListaAmigosOn.getInstancia().anhadirAmigo(amigo);
        } catch(Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    @Override
    public void notificarDesconexion(Usuario usuario) {
        Amigo amigo = new Amigo(usuario.nick, usuario.conectado, usuario.ip, usuario.puerto);
        try {
            ListaAmigosOn.getInstancia().eliminarAmigo(amigo);
            ListaAmigosOff.getInstancia().anhadirAmigo(amigo);
        } catch(Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    @Override
    public void notificarNuevaAmistad(Usuario usuario) {
        Amigo amigo = new Amigo(usuario.nick, usuario.conectado, usuario.ip, usuario.puerto);
        try {
            if(amigo.estaConectado()) {
                ListaAmigosOn.getInstancia().anhadirAmigo(amigo);
            }
            else {
                ListaAmigosOff.getInstancia().anhadirAmigo(amigo);
            }
        } catch(Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());            
        }
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