package ClienteP2P;


/**
* ClienteP2P/IClienteServidorPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Interfaces.idl
* sábado, 15 de abril de 2017, 13:20:18 (hora de verano de Europa central)
*/

public abstract class IClienteServidorPOA extends org.omg.PortableServer.Servant
 implements ClienteP2P.IClienteServidorOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("conectarse", new java.lang.Integer (0));
    _methods.put ("buscarUsuarios", new java.lang.Integer (1));
    _methods.put ("registrarUsuario", new java.lang.Integer (2));
    _methods.put ("modificarPassword", new java.lang.Integer (3));
    _methods.put ("anhadirAmigo", new java.lang.Integer (4));
    _methods.put ("borrarAmigo", new java.lang.Integer (5));
    _methods.put ("desconectarse", new java.lang.Integer (6));
    _methods.put ("cambiarIP", new java.lang.Integer (7));
    _methods.put ("shutdown", new java.lang.Integer (8));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // ClienteP2P/IClienteServidor/conectarse
       {
         String nick = in.read_string ();
         String password = in.read_string ();
         String ip = in.read_string ();
         String puerto = in.read_string ();
         ClienteP2P.IServidorCliente i = ClienteP2P.IServidorClienteHelper.read (in);
         ClienteP2P.Usuario $result[] = null;
         $result = this.conectarse (nick, password, ip, puerto, i);
         out = $rh.createReply();
         ClienteP2P.listaUsuariosHelper.write (out, $result);
         break;
       }

       case 1:  // ClienteP2P/IClienteServidor/buscarUsuarios
       {
         String cadenaBusqueda = in.read_string ();
         ClienteP2P.Usuario $result[] = null;
         $result = this.buscarUsuarios (cadenaBusqueda);
         out = $rh.createReply();
         ClienteP2P.listaUsuariosHelper.write (out, $result);
         break;
       }

       case 2:  // ClienteP2P/IClienteServidor/registrarUsuario
       {
         String nick = in.read_string ();
         String password = in.read_string ();
         String ip = in.read_string ();
         String puerto = in.read_string ();
         boolean $result = false;
         $result = this.registrarUsuario (nick, password, ip, puerto);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 3:  // ClienteP2P/IClienteServidor/modificarPassword
       {
         String nick = in.read_string ();
         String passwordNueva = in.read_string ();
         String passwordVieja = in.read_string ();
         this.modificarPassword (nick, passwordNueva, passwordVieja);
         out = $rh.createReply();
         break;
       }

       case 4:  // ClienteP2P/IClienteServidor/anhadirAmigo
       {
         String nick = in.read_string ();
         String amigo = in.read_string ();
         this.anhadirAmigo (nick, amigo);
         out = $rh.createReply();
         break;
       }

       case 5:  // ClienteP2P/IClienteServidor/borrarAmigo
       {
         String nick = in.read_string ();
         String amigo = in.read_string ();
         this.borrarAmigo (nick, amigo);
         out = $rh.createReply();
         break;
       }

       case 6:  // ClienteP2P/IClienteServidor/desconectarse
       {
         String nick = in.read_string ();
         this.desconectarse (nick);
         out = $rh.createReply();
         break;
       }

       case 7:  // ClienteP2P/IClienteServidor/cambiarIP
       {
         String nick = in.read_string ();
         String ipNueva = in.read_string ();
         this.cambiarIP (nick, ipNueva);
         out = $rh.createReply();
         break;
       }

       case 8:  // ClienteP2P/IClienteServidor/shutdown
       {
         this.shutdown ();
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:ClienteP2P/IClienteServidor:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public IClienteServidor _this() 
  {
    return IClienteServidorHelper.narrow(
    super._this_object());
  }

  public IClienteServidor _this(org.omg.CORBA.ORB orb) 
  {
    return IClienteServidorHelper.narrow(
    super._this_object(orb));
  }


} // class IClienteServidorPOA