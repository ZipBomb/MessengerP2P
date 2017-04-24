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
package app.controlador;

import app.modelo.Amigo;
import app.modelo.ListaAmigosOn;
import app.modelo.ListaConversaciones;
import app.modelo.Mensaje;
import app.vista.VistaUtils;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 */
public class HiloAtiendeCliente extends Thread {
        
    private final DataInputStream entrada;
    
    public HiloAtiendeCliente(Socket socketCliente) throws IOException {
        super();
        this.entrada = new DataInputStream(socketCliente.getInputStream());
    }
    
    @Override
    public void run() {
        Amigo emisor;
        FXMLLoader loader = VistaUtils.cargarVista("app/vista/VistaGeneral.fxml");
        ControladorVistaGeneral controlador = loader.getController();        
        while(!Thread.interrupted()) {
            try {
                String nickEmisor = this.leerParametro();  
                String tipo = leerParametro();
                emisor = ListaAmigosOn.getInstancia().recuperaAmigo(nickEmisor);                
                if(tipo.equals("Text")){
                    String tamanho = leerParametro();
                    Integer size = Integer.parseInt(tamanho);
                    byte[] message = new byte[size];
                    this.entrada.read(message, 0, size);
                    if(ListaConversaciones.getInstancia().existeConversacion(emisor)) {
                        ListaConversaciones.getInstancia().getConversacion(emisor)
                            .anhadirMensaje(new Mensaje(emisor, new String(message)));
                    }
                    else {
                        ListaConversaciones.getInstancia().iniciarConversacion(emisor);
                        ListaConversaciones.getInstancia().getConversacion(emisor)
                            .anhadirMensaje(new Mensaje(emisor, new String(message)));
                    }
                    controlador.reabreVentanaConversacion(emisor);
                }
                else if(tipo.equals("Data")){
                    String nombre = leerParametro();
                    String extension = leerParametro();
                    String tamanho = leerParametro();                    
                    String archivo = nombre + "." + extension;
                    Integer size = Integer.parseInt(tamanho);  
                    this.escribirArchivo(archivo, size);
                    if(ListaConversaciones.getInstancia().existeConversacion(emisor)) {
                        ListaConversaciones.getInstancia().getConversacion(emisor)
                            .anhadirMensaje(new Mensaje(emisor, "Te he enviado " + archivo));
                    }
                    else {
                        ListaConversaciones.getInstancia().iniciarConversacion(emisor);
                        ListaConversaciones.getInstancia().getConversacion(emisor)
                            .anhadirMensaje(new Mensaje(emisor, "Te he enviado " + archivo));
                    }
                    controlador.reabreVentanaConversacion(emisor);
                }
            } catch(IOException | NumberFormatException e) {}
        }
    }
    
    private String leerParametro() throws IOException{
        String parametro = new String();
        String caracter = new String();
        while(!caracter.equals(".")){
            parametro = parametro + caracter;
            byte[] message = new byte[1];
            entrada.read(message, 0, 1);
            caracter = new String(message);                          
        }   
        return parametro;
    }        
    
    private void escribirArchivo(String nombre, Integer size) throws FileNotFoundException, IOException {
        OutputStream out = new FileOutputStream("/tmp/" + nombre);
        byte[] message = new byte[size];
        entrada.read(message, 0, size);
        out.write(message);
    }      
    
    public void cerrarConversacion() throws IOException {
        entrada.close();        
    }    
    
}
