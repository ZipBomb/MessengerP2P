
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pablo
 */
public class Cliente2 {
    public static final int PORT = 4444;
    public static void main(String[] args) throws IOException {
        // Establece el puerto en el que escucha peticiones
        ServerSocket socketServidor = null;
        try {
            socketServidor = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("No puede escuchar en el puerto: " + PORT);
            System.exit(-1);
        }

        Socket socketCliente = null;
        String nick = "Pablo";
        while(true){
            socketCliente = socketServidor.accept();
            HiloRecibirComunicacion hilo = new HiloRecibirComunicacion(socketCliente,nick);
            hilo.start();
        }                                               
    }
}
