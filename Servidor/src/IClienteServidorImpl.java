/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import AccesoDatos.ModeloUsuarios;
import ClienteP2P.IClienteServidorPOA;
import ClienteP2P.IServidorCliente;
import ClienteP2P.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import org.omg.CORBA.ORB;
/**
 *
 * @author pablo
 */
public class IClienteServidorImpl extends IClienteServidorPOA {
    private ORB orb;
    private ModeloUsuarios modeloUsuarios;  
    private HashMap<String, IServidorCliente> clientes;
    private HiloCliente hilo;
    
    public IClienteServidorImpl() {
        this.modeloUsuarios = new ModeloUsuarios(); 
        this.clientes = new HashMap<>();        
        this.hilo = new HiloCliente();        
        hilo.start();
    }
        
    public void setORB(ORB orb_val) {
        orb = orb_val; 
    }

    @Override
    public Usuario[] conectarse(String nick, String password, String ip, String puerto, IServidorCliente interfaz) {
        Usuario[] usuarios = modeloUsuarios.conectarse(nick, password, ip, puerto);
        Usuario[] peticiones = modeloUsuarios.recpuerarSolicitudesAmistad(nick);        
        if(usuarios == null){
            Usuario[] aux = new Usuario[1];
            aux[0] = new Usuario(null, null, null, false); 
            return aux;
        }
        else{                        
            clientes.put(nick, interfaz);
            Usuario[] usuario = new Usuario[1];
            usuario[0] = new Usuario(nick, ip, puerto, true);    
            ArrayList<IServidorCliente> interfaces = new ArrayList<>();
            for(int i = 0; i < usuarios.length; i++){             
                interfaces.add(clientes.get(usuarios[i].nick));
            }
            hilo.introducirOrden(2, interfaces, usuario);
            interfaces.clear();
            interfaces.add(interfaz);
            hilo.introducirOrden(1, interfaces, peticiones);
            
            return usuarios;
        }        
    }

    @Override
    public Usuario[] buscarUsuarios(String cadenaBusqueda) {
        return modeloUsuarios.buscarUsuarios(cadenaBusqueda);
    }        

    @Override
    public boolean registrarUsuario(String nick, String password, String ip, String puerto) {
        return modeloUsuarios.registrarUsuario(nick, password, ip, puerto);
    }

    @Override
    public void modificarPassword(String nick, String passwordNueva, String passwordVieja) {
        modeloUsuarios.modificarPassword(nick, passwordNueva, passwordVieja);        
    }

    @Override
    public void anhadirAmigo(String nick, String amigo) {
        if(modeloUsuarios.estaOnline(amigo)){
            Usuario[] peticiones = modeloUsuarios.recpuerarSolicitudesAmistad(amigo);
            ArrayList<IServidorCliente> interfaces = new ArrayList<>();
            interfaces.add(clientes.get(amigo));
            hilo.introducirOrden(4, interfaces, peticiones);
        }
        else
            modeloUsuarios.guardarPeticionAmistad(nick, amigo);
    }

    @Override
    public void borrarAmigo(String nick, String amigo) {
        modeloUsuarios.borrarAmigo(nick, amigo);
    }

    @Override
    public void desconectarse(String nick) {
        Usuario[] amigos = modeloUsuarios.desconectarse(nick);
        Usuario[] usuario = new Usuario[1];
        usuario[0] = new Usuario(nick, null, null, false);
        ArrayList<IServidorCliente> interfaces = new ArrayList<>();
        for(int i = 0; i < amigos.length; i++)
            interfaces.add(clientes.get(amigos[i].nick));
        hilo.introducirOrden(3, interfaces, usuario);
        clientes.remove(nick);
    }

    @Override
    public void cambiarIP(String nick, String ipNueva) {
        modeloUsuarios.cambiarIP(nick, ipNueva);
    }

    @Override
    public void shutdown() {
        orb.shutdown(true);
    }

}