package app.controlador;


import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pablo
 */
public interface IUsuario extends Serializable {
    

    public String getNick();

    public void setNick(String nick);    

    public boolean isConectado();

    public void setConectado(boolean conectado);
    
    public IComunicacionCliente getInterfaz();
    
    public void setInterfaz(IComunicacionCliente interfaz);
}
