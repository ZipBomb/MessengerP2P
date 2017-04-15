package ClienteP2P;


/**
* ClienteP2P/_IClienteServidorStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Interfaces.idl
* sábado, 15 de abril de 2017, 13:20:18 (hora de verano de Europa central)
*/

public class _IClienteServidorStub extends org.omg.CORBA.portable.ObjectImpl implements ClienteP2P.IClienteServidor
{

  public ClienteP2P.Usuario[] conectarse (String nick, String password, String ip, String puerto, ClienteP2P.IServidorCliente i)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("conectarse", true);
                $out.write_string (nick);
                $out.write_string (password);
                $out.write_string (ip);
                $out.write_string (puerto);
                ClienteP2P.IServidorClienteHelper.write ($out, i);
                $in = _invoke ($out);
                ClienteP2P.Usuario $result[] = ClienteP2P.listaUsuariosHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return conectarse (nick, password, ip, puerto, i        );
            } finally {
                _releaseReply ($in);
            }
  } // conectarse

  public ClienteP2P.Usuario[] buscarUsuarios (String cadenaBusqueda)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("buscarUsuarios", true);
                $out.write_string (cadenaBusqueda);
                $in = _invoke ($out);
                ClienteP2P.Usuario $result[] = ClienteP2P.listaUsuariosHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return buscarUsuarios (cadenaBusqueda        );
            } finally {
                _releaseReply ($in);
            }
  } // buscarUsuarios

  public boolean registrarUsuario (String nick, String password, String ip, String puerto)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("registrarUsuario", true);
                $out.write_string (nick);
                $out.write_string (password);
                $out.write_string (ip);
                $out.write_string (puerto);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return registrarUsuario (nick, password, ip, puerto        );
            } finally {
                _releaseReply ($in);
            }
  } // registrarUsuario

  public void modificarPassword (String nick, String passwordNueva, String passwordVieja)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("modificarPassword", false);
                $out.write_string (nick);
                $out.write_string (passwordNueva);
                $out.write_string (passwordVieja);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                modificarPassword (nick, passwordNueva, passwordVieja        );
            } finally {
                _releaseReply ($in);
            }
  } // modificarPassword

  public void anhadirAmigo (String nick, String amigo)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("anhadirAmigo", false);
                $out.write_string (nick);
                $out.write_string (amigo);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                anhadirAmigo (nick, amigo        );
            } finally {
                _releaseReply ($in);
            }
  } // anhadirAmigo

  public void borrarAmigo (String nick, String amigo)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("borrarAmigo", false);
                $out.write_string (nick);
                $out.write_string (amigo);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                borrarAmigo (nick, amigo        );
            } finally {
                _releaseReply ($in);
            }
  } // borrarAmigo

  public void desconectarse (String nick)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("desconectarse", false);
                $out.write_string (nick);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                desconectarse (nick        );
            } finally {
                _releaseReply ($in);
            }
  } // desconectarse

  public void cambiarIP (String nick, String ipNueva)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("cambiarIP", false);
                $out.write_string (nick);
                $out.write_string (ipNueva);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                cambiarIP (nick, ipNueva        );
            } finally {
                _releaseReply ($in);
            }
  } // cambiarIP

  public void shutdown ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("shutdown", false);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                shutdown (        );
            } finally {
                _releaseReply ($in);
            }
  } // shutdown

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:ClienteP2P/IClienteServidor:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _IClienteServidorStub
