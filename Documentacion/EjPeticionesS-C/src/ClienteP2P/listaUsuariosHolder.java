package ClienteP2P;


/**
* ClienteP2P/listaUsuariosHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from IServidorCliente.idl
* jueves 6 de abril de 2017 22H29' CEST
*/

public final class listaUsuariosHolder implements org.omg.CORBA.portable.Streamable
{
  public ClienteP2P.Usuario value[] = null;

  public listaUsuariosHolder ()
  {
  }

  public listaUsuariosHolder (ClienteP2P.Usuario[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ClienteP2P.listaUsuariosHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ClienteP2P.listaUsuariosHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ClienteP2P.listaUsuariosHelper.type ();
  }

}
