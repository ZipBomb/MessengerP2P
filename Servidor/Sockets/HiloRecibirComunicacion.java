import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pablo
 */
public class HiloRecibirComunicacion extends Thread {
    
    private DataInputStream entrada;
    private String nick;
    
    public HiloRecibirComunicacion(Socket socketCliente, String nick) throws IOException {        
        this.entrada = new DataInputStream(socketCliente.getInputStream());
        this.nick = nick;
    }
    
    @Override
    public void run(){
        try {            
            while (true) {  
                String usuario = leerParametro();  
                if(usuario.equals(nick)){
                    String tipo = leerParametro();                    
                    if(tipo.equals("Text")){
                        String tamanho = leerParametro();
                        Integer size = Integer.parseInt(tamanho);
                        byte[] message = new byte[size];
                        entrada.read(message, 0, size);
                        System.out.println(new String(message));                        
                    }
                    else if(tipo.equals("Data")){
                        String nombre = leerParametro();
                        String extension = leerParametro();
                        String archivo = nombre + "." + extension;
                        String tamanho = leerParametro();
                        Integer size = Integer.parseInt(tamanho);  
                        escribirArchivo(archivo, size);
                    }
                }
                else{
                    
                }
            }            
        } catch (IOException ex) {
            Logger.getLogger(HiloRecibirComunicacion.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void escribirArchivo(String nombre, Integer size) throws FileNotFoundException, IOException{
        System.out.println(size);
        OutputStream out = new FileOutputStream(nombre);
        System.out.println("sas");
        byte[] message = new byte[size];
        entrada.read(message, 0, size);
        out.write(message);
    }      
    
    public void cerrarConversacion() throws IOException{
        entrada.close();        
    }
}
