package app.controlador;


import java.io.Serializable;
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
