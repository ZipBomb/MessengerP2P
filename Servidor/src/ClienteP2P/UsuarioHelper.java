package ClienteP2P;


/**
* ClienteP2P/UsuarioHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Interfaces.idl
* sábado, 15 de abril de 2017, 13:20:17 (hora de verano de Europa central)
*/

abstract public class UsuarioHelper
{
  private static String  _id = "IDL:ClienteP2P/Usuario:1.0";

  public static void insert (org.omg.CORBA.Any a, ClienteP2P.Usuario that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static ClienteP2P.Usuario extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [4];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "nick",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "ip",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[2] = new org.omg.CORBA.StructMember (
            "puerto",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_boolean);
          _members0[3] = new org.omg.CORBA.StructMember (
            "conectado",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (ClienteP2P.UsuarioHelper.id (), "Usuario", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static ClienteP2P.Usuario read (org.omg.CORBA.portable.InputStream istream)
  {
    ClienteP2P.Usuario value = new ClienteP2P.Usuario ();
    value.nick = istream.read_string ();
    value.ip = istream.read_string ();
    value.puerto = istream.read_string ();
    value.conectado = istream.read_boolean ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, ClienteP2P.Usuario value)
  {
    ostream.write_string (value.nick);
    ostream.write_string (value.ip);
    ostream.write_string (value.puerto);
    ostream.write_boolean (value.conectado);
  }

}
