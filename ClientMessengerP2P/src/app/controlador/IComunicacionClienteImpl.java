/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controlador;

import app.modelo.Amigo;
import app.modelo.ListaAmigosOn;
import app.modelo.ListaConversaciones;
import app.modelo.Mensaje;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author pablo
 */
public class IComunicacionClienteImpl extends UnicastRemoteObject implements IComunicacionCliente {

    public IComunicacionClienteImpl() throws RemoteException {
    }

    @Override
    public void enviarArchivo(byte[] array, String nick) throws RemoteException {
        int i = 0;
        String nombre = leerParametro(array, i);
        OutputStream out = null;
        try {
            out = new FileOutputStream("/tmp/" + nombre);             
            out.write(array, i, array.length);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }        
    }

    @Override
    public void enviarTexto(String mensaje, String nick) throws RemoteException {
        Amigo emisor = ListaAmigosOn.getInstancia().recuperaAmigo(nick);
        if(ListaConversaciones.getInstancia().existeConversacion(emisor)) {
            ListaConversaciones.getInstancia().getConversacion(emisor)
                .anhadirMensaje(new Mensaje(emisor, mensaje));
        }
        else {
            ListaConversaciones.getInstancia().iniciarConversacion(emisor);
            ListaConversaciones.getInstancia().getConversacion(emisor)
                .anhadirMensaje(new Mensaje(emisor, mensaje));
        }
    }            
    
    private String leerParametro(byte[] array, int i) {
        String parametro = new String();
        String caracter = new String();
        while(!caracter.equals(":")){
            parametro = parametro + caracter;
            byte[] message = new byte[1];  
            message[0] = array[i];
            caracter = new String(message);  
            i++;
        }   
        return parametro;
    }   
    
}
