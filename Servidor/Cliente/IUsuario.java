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

    public String getIp();

    public void setIp(String ip);

    public String getPuerto();

    public void setPuerto(String puerto);

    public boolean isConectado();

    public void setConectado(boolean conectado);
}
