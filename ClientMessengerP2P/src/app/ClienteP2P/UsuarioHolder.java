package ClienteP2P;

/**
* ClienteP2P/UsuarioHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from IServidorCliente.idl
* viernes 7 de abril de 2017 16H19' CEST
*/

public final class UsuarioHolder implements org.omg.CORBA.portable.Streamable
{
  public ClienteP2P.Usuario value = null;

  public UsuarioHolder ()
  {
  }

  public UsuarioHolder (ClienteP2P.Usuario initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ClienteP2P.UsuarioHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ClienteP2P.UsuarioHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ClienteP2P.UsuarioHelper.type ();
  }

}