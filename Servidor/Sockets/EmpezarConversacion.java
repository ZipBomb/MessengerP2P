
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class EmpezarConversacion {
    
    private String nick;
    private Usuario usuario;
    
    public void EmpezarConversacion(String nick, Usuario usuario){
        this.nick = nick;
        this.usuario = usuario;
    }
    
    public void iniciarConversacion() throws IOException{
        Socket socketCliente = null;
        BufferedReader entrada = null;
        PrintWriter salida = null;

        try {
            socketCliente = new Socket(usuario.getIp(), Integer.parseInt(usuario.getPuerto()));            
            entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())),true);
        } catch (IOException e) {
            System.err.println("No puede establer canales de E/S para la conexión");
            System.exit(-1);
        }
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        try {            
            String enviado = null;
            while (true) {  
                String recibido;            
                recibido = entrada.readLine();
                String[] mensaje = recibido.split("\\.");
                String usuario = mensaje[0];
                recibido = mensaje[1];
                System.out.println("Cliente: " + recibido);
                enviado = stdIn.readLine();
                enviado = nick + "\\." + enviado;
                salida.println(enviado);
                if (recibido.equals("Adios")) break;
            }
            salida.close();
            entrada.close();
            socketCliente.close();
        } catch (IOException ex) {
            Logger.getLogger(HiloRecibirComunicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
