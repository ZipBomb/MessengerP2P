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
    
    private String nick;
    private boolean conectado;
    private IComunicacionCliente interfaz;

    public Usuario (String nick, boolean conectado, IComunicacionCliente interfaz) {
        this.nick = nick;
        this.conectado = conectado;
        this.interfaz = interfaz;
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
    public boolean isConectado() {
        return conectado;
    }

    @Override
    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }
    
    @Override
    public IComunicacionCliente getInterfaz() {
        return interfaz;
    }
    
    @Override
    public void setInterfaz(IComunicacionCliente interfaz) {
        this.interfaz = interfaz;
    }            
}
