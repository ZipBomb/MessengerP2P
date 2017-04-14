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
 */
public class ListaAmigosOff {
    
    private final ObservableList<Amigo> listaAmigosOff;
    private static final ListaAmigosOff INSTANCIA = new ListaAmigosOff();
    
    private ListaAmigosOff() {
        this.listaAmigosOff = FXCollections.observableArrayList();
    }
    
    public static ListaAmigosOff getInstancia() {
        return INSTANCIA;
    }
    
    public ObservableList<Amigo> getListaAmigosOff() {
        return listaAmigosOff;
    }

    public void anhadirAmigo(Amigo amigo) throws Exception {
        if(!yaExiste(amigo)) {
            this.listaAmigosOff.add(amigo);
        } else throw new Exception("El usuario no pertenecía a la lista de amigos conectados");
    }
    
    public void eliminarAmigo(Amigo amigo) throws Exception {
        if(yaExiste(amigo)) {
            this.listaAmigosOff.add(amigo);
        }
        else throw new Exception("El usuario no pertenecía a la lista de amigos desconectados");
    }
    
    private boolean yaExiste(Amigo amigo) {
        for(Amigo aux : this.listaAmigosOff) {
            if(aux.getNick().getValue().equals(amigo.getNick().getValue())) {
                return true;
            }
        }
        return false;
    }
    
}
