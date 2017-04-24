package app.controlador;


import java.io.Serializable;
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
