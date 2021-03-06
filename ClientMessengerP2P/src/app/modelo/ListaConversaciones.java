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
package app.modelo;

import app.controlador.ControladorVistaGeneral;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 */
public class ListaConversaciones {
    
    private final ConcurrentHashMap<String, Conversacion> listaConversaciones;
    private static final ListaConversaciones INSTANCIA = new ListaConversaciones();
    public ObjectProperty<ControladorVistaGeneral> mainControllerProperty = new SimpleObjectProperty();    

    private ListaConversaciones() {
        this.listaConversaciones = new ConcurrentHashMap<>();
    }
    
    public static ListaConversaciones getInstancia() {
        return INSTANCIA;
    }    
    
    public void iniciarConversacion(Amigo destinatario) {
        String nickAmigo = destinatario.getNick().getValue();
        if(ListaAmigosOn.getInstancia().yaExiste(destinatario) 
                && !this.listaConversaciones.containsKey(nickAmigo)) {
            this.listaConversaciones.put(nickAmigo, new Conversacion(destinatario));
        }
    }
    
    public Conversacion getConversacion(Amigo destinatario) {
        String nickAmigo = destinatario.getNick().getValue();        
        if(ListaAmigosOn.getInstancia().yaExiste(destinatario) 
                && this.listaConversaciones.containsKey(nickAmigo)) {
            return this.listaConversaciones.get(nickAmigo);
        }
        return null;
    }
    
    public boolean existeConversacion(Amigo destinatario) {
        String nickAmigo = destinatario.getNick().getValue();        
        return this.listaConversaciones.containsKey(nickAmigo);
    }
    
    public void reabreVentana(Amigo amigo) throws IOException {
        Platform.runLater(() -> {
            try {
                this.mainControllerProperty.getValue().reabreVentanaConversacion(amigo);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });        
    }    
    
}

