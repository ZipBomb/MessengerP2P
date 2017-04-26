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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 */
public class HiloEscucha extends Thread {
    
    ServerSocket socketEscucha;
    ArrayList<HiloAtiendeCliente> conexiones;
    int puerto = 7777;
    
    public HiloEscucha() throws IOException {
        super();
        this.socketEscucha = new ServerSocket(this.puerto);
        this.conexiones = new ArrayList<>();
    }
    
    @Override public void run() {
        Socket socketCliente = null;
        HiloAtiendeCliente hiloAtiendeCliente = null;
        while(!Thread.interrupted()) {
            try {
                socketCliente = socketEscucha.accept();
                hiloAtiendeCliente = new HiloAtiendeCliente(socketCliente);                
                hiloAtiendeCliente.start();
                this.conexiones.add(hiloAtiendeCliente);                
            } catch (IOException ex) {}
        }
        for(HiloAtiendeCliente conexion : this.conexiones) {
            try {
                conexion.cerrarConversacion();
                conexion.interrupt();                
            } catch (IOException ex) {}
        }
    }
    
}
