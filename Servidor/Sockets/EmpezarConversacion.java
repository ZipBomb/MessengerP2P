import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class EmpezarConversacion {
    
    private String nick;
    private DataOutputStream salida;
    
    public void EmpezarConversacion(String nick, Usuario usuario){
        this.nick = nick;
        Socket socketCliente = null;        

        try {
            socketCliente = new Socket(usuario.getIp(), Integer.parseInt(usuario.getPuerto()));                        
            salida = new DataOutputStream(socketCliente.getOutputStream());
        } catch (IOException e) {
            System.err.println("No puede establer canales de E/S para la conexión");
            System.exit(-1);
        }
    }
    
    public void iniciarConversacion() throws IOException{        
        try {            
            String mensaje = null;
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            while (true) {                  
                mensaje = stdIn.readLine();
                enviarTexto(mensaje);    
                //enviarArchivo("a.txt");
            }            
        } catch (IOException ex) {
            Logger.getLogger(HiloRecibirComunicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void enviarTexto(String texto) throws IOException{
        String cabecera = nick + "." + "Text." + texto.length() +"." + texto;
        byte[] paquete = cabecera.getBytes();
        salida.write(paquete);
    }
    
    private void enviarArchivo(String ruta) throws FileNotFoundException, IOException{
        File myFile = new File (ruta);
        String nombre = myFile.getName();
        byte [] arrayArchivo  = new byte [(int)myFile.length()];
        FileInputStream fis = new FileInputStream(myFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(arrayArchivo,0,arrayArchivo.length);
        String cabecera = nick + "." + "Data." + nombre + "." + myFile.length() +".";

        byte [] paquete = new byte[arrayArchivo.length + cabecera.length()];

        for(int i = 0; i < cabecera.length(); i++){
            paquete[i] = (byte)cabecera.charAt(i);                    
        }                
        int j = 0;
        for(int i = cabecera.length() - 1; i < arrayArchivo.length + cabecera.length() - 1; i++){                    
            paquete[i] = arrayArchivo[j];                    
            j++;
        }
        salida.write(paquete);
    }
}
