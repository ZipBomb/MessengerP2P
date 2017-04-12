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
    
    private final ObservableList<Amigo> listaAmigos;
    private static final ListaAmigosOn INSTANCIA = new ListaAmigosOn();
    
    private ListaAmigosOn() {
        this.listaAmigos = FXCollections.observableArrayList();
    }
    
    public static ListaAmigosOn getInstancia() {
        return INSTANCIA;
    }
    
    public ObservableList<Amigo> getListaAmigos() {
        return listaAmigos;
    }

    public void anhadirAmigo() {

    }
    
    public void eliminarAmigo() {
    
    }
    
}
