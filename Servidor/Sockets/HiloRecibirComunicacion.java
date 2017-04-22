
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
    
    private Socket socketCliente;
    private DataOutputStream salida; //Flujo de datos de salida
    private DataInputStream entrada;
    private String nick;
    
    public HiloRecibirComunicacion(Socket socketCliente, String nick) throws IOException {        
        super();
        this.socketCliente = socketCliente;
        this.salida = new DataOutputStream(socketCliente.getOutputStream());
        this.entrada = new DataInputStream(socketCliente.getInputStream());
        this.nick = nick;
    }
    
    @Override
    public void run(){
        try {            
            String enviado;
            while (true) {  
                String recibido;            
                recibido = entrada.readLine();
                String[] mensaje = recibido.split("\\.");
                String usuario = mensaje[0];
                recibido = mensaje[1];
                System.out.println("Cliente: " + recibido);
                enviado = entrada.readLine();
                enviado = nick + "\\." + enviado;
                salida.writeBytes(enviado);
                               
                if (recibido.equals("Adios")) break;
            }
            salida.close();
            entrada.close();
            socketCliente.close();
        } catch (IOException ex) {
            Logger.getLogger(HiloRecibirComunicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }            
    
    public void enviarArchivo(String ruta) throws FileNotFoundException, IOException{
        File myFile = new File (ruta);
        byte [] mybytearray  = new byte [(int)myFile.length()];
        FileInputStream fis = new FileInputStream(myFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(mybytearray,0,mybytearray.length);

        salida.write(mybytearray, 0, mybytearray.length);
    }
}
