/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.vista;

import javafx.fxml.FXMLLoader;

/**
 *
 * @author Pablo Rey <pablo.rey.fernandez@rai.usc.es>
 */
public class VistaUtils {
    public static FXMLLoader cargarVista(String vista) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(VistaUtils.class.getClassLoader().getResource(vista));
        return loader;
    }    
}
