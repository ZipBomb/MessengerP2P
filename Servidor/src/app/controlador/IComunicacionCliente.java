/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controlador;

import java.rmi.Remote;

/**
 *
 * @author pablo
 */
public interface IComunicacionCliente extends Remote {
    
    public void enviarArchivo(byte[] array, String nick);
    public void enviarTexto(String mensaje, String nick);
    
}
