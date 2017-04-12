/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 */
public class ListaAmigosOff {
    
    private final ObservableList<Amigo> listaAmigos;
    private static final ListaAmigosOff INSTANCIA = new ListaAmigosOff();
    
    private ListaAmigosOff() {
        this.listaAmigos = FXCollections.observableArrayList();
    }
    
    public static ListaAmigosOff getInstancia() {
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
