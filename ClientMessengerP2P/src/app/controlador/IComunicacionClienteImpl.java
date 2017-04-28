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
import java.io.IOException;
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
        Integer i = 0;        
        String nombre = leerParametro(array, i);
        int h = nombre.length() + 1;
        OutputStream out = null;
        byte[] aux = new byte[array.length - nombre.length() -1];
        for(int j = 0; j < aux.length; j++){
            aux[j] = array[h];
            h++;
        }
        try {
            out = new FileOutputStream("/tmp/" + nombre);         
            out.write(aux, 0, aux.length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        // Actualización de la vista
        Amigo emisor = ListaAmigosOn.getInstancia().recuperaAmigo(nick);
        if(ListaConversaciones.getInstancia().existeConversacion(emisor)) {
            ListaConversaciones.getInstancia().getConversacion(emisor)
                .anhadirMensaje(new Mensaje(emisor, "Acabo de enviarte '" + nombre + "' (/tmp)"));
        }
        else {
            ListaConversaciones.getInstancia().iniciarConversacion(emisor);
            ListaConversaciones.getInstancia().getConversacion(emisor)
                .anhadirMensaje(new Mensaje(emisor, "Acabo de enviarte '" + nombre + "' (/tmp)"));
        }
        try {
            ListaConversaciones.getInstancia().reabreVentana(emisor);
        } catch (IOException ex) {}
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
        try {
            ListaConversaciones.getInstancia().reabreVentana(emisor);
        } catch (IOException ex) {}        
    }            
    
    private String leerParametro(byte[] array, Integer i) {
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
