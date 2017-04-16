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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 * @version 1.0
 * @since 2017-03
 */
public class ListaAmigosOn {

    private final ObservableList<Amigo> listaAmigosOn;
    private static final ListaAmigosOn INSTANCIA = new ListaAmigosOn();
    
    private ListaAmigosOn() {
        this.listaAmigosOn = FXCollections.observableArrayList();
    }
    
    public static ListaAmigosOn getInstancia() {
        return INSTANCIA;
    }
    
    public ObservableList<Amigo> getListaAmigosOn() {
        return listaAmigosOn;
    }

    public void anhadirAmigo(Amigo amigo) throws Exception {
        if(!yaExiste(amigo)) {
            this.listaAmigosOn.add(amigo);
        } else throw new Exception("El usuario no pertenecía a la lista de amigos conectados");
    }
    
    public void eliminarAmigo(Amigo amigo) throws Exception {
        if(yaExiste(amigo)) {
            for(Amigo aux : this.listaAmigosOn) {
                if(aux.getNick().getValue().equals(amigo.getNick().getValue())) {
                    this.listaAmigosOn.remove(aux);
                    break;
                }
            }
        }
        else throw new Exception("El usuario no pertenecía a la lista de amigos desconectados");
    }
    
    public boolean yaExiste(Amigo amigo) {
        for(Amigo aux : this.listaAmigosOn) {
            if(aux.getNick().getValue().equals(amigo.getNick().getValue())) {
                return true;
            }
        }
        return false;
    }
    
    public Amigo recuperaAmigo(String nick) {
        Amigo aux = new Amigo(nick, false, null, null);
        if(yaExiste(aux)) {
            for(Amigo i : this.listaAmigosOn) {
                if(i.getNick().getValue().equals(nick)) {
                    return i;
                }
            }
        }
        return null;
    }
    
}
