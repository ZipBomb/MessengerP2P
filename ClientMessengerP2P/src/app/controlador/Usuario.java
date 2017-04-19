package app.controlador;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author pablo
 */
public class Usuario implements IUsuario {
    
    private String nick = null;
    private String ip = null;
    private String puerto = null;
    private boolean conectado = false;

    public Usuario (String _nick, String _ip, String _puerto, boolean _conectado) {
      nick = _nick;
      ip = _ip;
      puerto = _puerto;
      conectado = _conectado;
    } 

    @Override
    public String getNick() {
        return nick;
    }

    @Override
    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public String getIp() {
        return ip;
    }

    @Override
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String getPuerto() {
        return puerto;
    }

    @Override
    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    @Override
    public boolean isConectado() {
        return conectado;
    }

    @Override
    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }
    
    
    
}
