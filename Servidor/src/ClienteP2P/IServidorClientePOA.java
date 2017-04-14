package ClienteP2P;


/**
* ClienteP2P/IServidorClientePOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Interfaces.idl
* viernes 14 de abril de 2017 23H17' CEST
*/

public abstract class IServidorClientePOA extends org.omg.PortableServer.Servant
 implements ClienteP2P.IServidorClienteOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("hola", new java.lang.Integer (0));
    _methods.put ("notificarSolicitudesPendientes", new java.lang.Integer (1));
    _methods.put ("notificarConexion", new java.lang.Integer (2));
    _methods.put ("notificarDesconexion", new java.lang.Integer (3));
    _methods.put ("notificarNuevaAmistad", new java.lang.Integer (4));
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
       case 0:  // ClienteP2P/IServidorCliente/hola
       {
         String mes = in.read_string ();
         this.hola (mes);
         out = $rh.createReply();
         break;
       }

       case 1:  // ClienteP2P/IServidorCliente/notificarSolicitudesPendientes
       {
         ClienteP2P.Usuario solicitudesPendientes[] = ClienteP2P.listaUsuariosHelper.read (in);
         this.notificarSolicitudesPendientes (solicitudesPendientes);
         out = $rh.createReply();
         break;
       }

       case 2:  // ClienteP2P/IServidorCliente/notificarConexion
       {
         ClienteP2P.Usuario usuario = ClienteP2P.UsuarioHelper.read (in);
         this.notificarConexion (usuario);
         out = $rh.createReply();
         break;
       }

       case 3:  // ClienteP2P/IServidorCliente/notificarDesconexion
       {
         ClienteP2P.Usuario usuario = ClienteP2P.UsuarioHelper.read (in);
         this.notificarDesconexion (usuario);
         out = $rh.createReply();
         break;
       }

       case 4:  // ClienteP2P/IServidorCliente/notificarNuevaAmistad
       {
         ClienteP2P.Usuario usuario = ClienteP2P.UsuarioHelper.read (in);
         this.notificarNuevaAmistad (usuario);
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
    "IDL:ClienteP2P/IServidorCliente:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public IServidorCliente _this() 
  {
    return IServidorClienteHelper.narrow(
    super._this_object());
  }

  public IServidorCliente _this(org.omg.CORBA.ORB orb) 
  {
    return IServidorClienteHelper.narrow(
    super._this_object(orb));
  }


} // class IServidorClientePOA
