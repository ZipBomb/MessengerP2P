package ClienteP2P;


/**
* ClienteP2P/Usuario.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Interfaces.idl
* sábado, 15 de abril de 2017, 13:20:18 (hora de verano de Europa central)
*/

public final class Usuario implements org.omg.CORBA.portable.IDLEntity
{
  public String nick = null;
  public String ip = null;
  public String puerto = null;
  public boolean conectado = false;

  public Usuario ()
  {
  } // ctor

  public Usuario (String _nick, String _ip, String _puerto, boolean _conectado)
  {
    nick = _nick;
    ip = _ip;
    puerto = _puerto;
    conectado = _conectado;
  } // ctor

} // class Usuario
