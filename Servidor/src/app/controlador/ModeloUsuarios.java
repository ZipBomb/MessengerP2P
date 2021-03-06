package app.controlador;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import app.controlador.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pablo
 */
public class ModeloUsuarios {
    
    private Connection conectar() throws SQLException{
        Connection connection = null;            
        connection = DriverManager.getConnection("jdbc:mysql://localhost/MessengerP2P","usuarioP2P","aplicacionP2P");
        
        return connection;
    }
        
    private ArrayList<Usuario> recuperarAmigosConectados(Connection connection, String nick) throws SQLException{
        ArrayList<Usuario> amigos = new ArrayList<>();
        PreparedStatement consulta = null;
        ResultSet resultado = null;
        ResultSet resultado2 = null;
        
        consulta = connection.prepareStatement("select * from amigos where usuario1 = ? or usuario2 = ?");
        consulta.setString(1, nick);
        consulta.setString(2, nick);
        resultado = consulta.executeQuery();
        
        while(resultado.next()){
            consulta = connection.prepareStatement("select * from usuarios where nick = ? and conectado = true");
            String user = null;
            if(resultado.getString(1).equals(nick))
                user = resultado.getString(2);
            else
                user = resultado.getString(1);
            consulta.setString(1, user);                
            resultado2 = consulta.executeQuery();
            if(resultado2.next()){
                Usuario amigo = new Usuario(resultado2.getString("nick"),resultado2.getBoolean("conectado"), null);
                amigos.add(amigo);
            }                                    
        }
        
        return amigos;
    }
    
    private ArrayList<Usuario> recuperarAmigos(Connection connection, String nick) throws SQLException{
        ArrayList<Usuario> amigos = new ArrayList<>();
        PreparedStatement consulta = null;
        ResultSet resultado = null;
        ResultSet resultado2 = null;
        
        consulta = connection.prepareStatement("select * from amigos where usuario1 = ? or usuario2 = ?");
        consulta.setString(1, nick);
        consulta.setString(2, nick);
        resultado = consulta.executeQuery();
        
        while(resultado.next()){
            consulta = connection.prepareStatement("select * from usuarios where nick = ?");
            String user = null;
            if(resultado.getString(1).equals(nick))
                user = resultado.getString(2);
            else
                user = resultado.getString(1);
            consulta.setString(1, user);                
            resultado2 = consulta.executeQuery();
            if(resultado2.next()){
                Usuario amigo = new Usuario(resultado2.getString("nick"), resultado2.getBoolean("conectado"), null);
                amigos.add(amigo);
            }                                    
        }
        
        return amigos;
    }
    
    private void connect(Connection connection, String nick) throws SQLException{
        PreparedStatement consulta = null;
        
        consulta = connection.prepareStatement("update usuarios set conectado = true where nick = ?");
        consulta.setString(1, nick);
        consulta.executeUpdate();
    }
    
    private boolean comprobarUsuario(Connection connection, String nick) throws SQLException{
        PreparedStatement consulta = null;
        ResultSet resultado = null;        
        
        consulta = connection.prepareStatement("select count(*) from usuarios where nick = ?");
        consulta.setString(1, nick);
        resultado = consulta.executeQuery();
        resultado.next();
        
        if(resultado.getInt(1) == 0)
            return true;
        else
            return false;
    }
    
    private boolean comprobarPassword(Connection connection, String nick, String password) throws SQLException{
        PreparedStatement consulta = null;
        ResultSet resultado = null;        
        
        consulta = connection.prepareStatement("select count(*) from usuarios where nick = ? and password = ?");
        consulta.setString(1, nick);
        consulta.setString(2, password);
        resultado = consulta.executeQuery();
        resultado.next();
        
        if(resultado.getInt(1) == 1)
            return true;
        else
            return false;
    }

    public Usuario[] conectarse(String nick, String password) {
        Connection connection = null;
        try {
            connection = conectar();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreparedStatement consulta = null;
        ResultSet resultado = null;
        ArrayList<Usuario> amigos = null;        
        try {
            connection.setAutoCommit(false);
                                    
            consulta = connection.prepareStatement("select count(*) from usuarios where nick = ? and password = ? and conectado = false");
            consulta.setString(1, nick);
            consulta.setString(2, password);
            resultado = consulta.executeQuery();
            resultado.next();
            
            if(resultado.getInt(1) == 1){
                amigos = new ArrayList<>();               
                connect(connection,nick);
                amigos = recuperarAmigos(connection, nick);                  
            }            
                        
            connection.commit();
        }catch (SQLException e){
            System.out.println(e.getMessage());         
            try{
		if(connection!=null)
                    connection.rollback();
            }catch(SQLException se2){
               se2.getMessage();
            }
        }finally{
            try {connection.close();} catch (SQLException e){
                System.out.println("Imposible cerrar cursores");              
            }
        }                    
                
        if(amigos != null){
            Usuario[] usuarios = new Usuario[amigos.size()];
            for(int i=0; i< amigos.size(); i++)
                usuarios[i] = amigos.get(i);    
            return usuarios;
        }
        else
            return null;        
    }
    
    public Usuario[] buscarUsuarios(String nick, String cadenaBusqueda) {
        Connection connection = null;
        try {
            connection = conectar();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreparedStatement consulta = null;
        ResultSet resultado = null;
        ArrayList<Usuario> users = new ArrayList<>();        
        try {
            connection.setAutoCommit(false);
                                    
            consulta = connection.prepareStatement("select * from usuarios where nick like ?");
            consulta.setString(1, "%" + cadenaBusqueda + "%");           
            resultado = consulta.executeQuery();
            
            while(resultado.next()){
                if(!resultado.getString("nick").equals(nick)){
                    Usuario usuario = new Usuario(resultado.getString("nick"), resultado.getBoolean("conectado"), null);
                    users.add(usuario);
                }
            }
                                                         
            connection.commit();
        }catch (SQLException e){
            System.out.println(e.getMessage());         
            try{
		if(connection!=null)
                    connection.rollback();
            }catch(SQLException se2){
               se2.getMessage();
            }
        }finally{
            try {connection.close();} catch (SQLException e){
                System.out.println("Imposible cerrar cursores");              
            }
        }                    
        
        Usuario[] usuarios = new Usuario[users.size()];
        for(int i=0; i< users.size(); i++)
            usuarios[i] = users.get(i);                
        
        return usuarios;
    }
    
    public boolean registrarUsuario(String nick, String password) {
        Connection connection = null;
        try {
            connection = conectar();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreparedStatement consulta = null;
        boolean test = false;  
        try {
            connection.setAutoCommit(false);
            
            if(comprobarUsuario(connection, nick)){
                test = true;
                consulta = connection.prepareStatement("insert into usuarios values(?,?,?)");
                consulta.setString(1, nick);
                consulta.setString(2, password);
                consulta.setBoolean(3, true);
                consulta.executeUpdate();
            }
                                                 
            connection.commit();
        }catch (SQLException e){
            System.out.println(e.getMessage());         
            try{
		if(connection!=null)
                    connection.rollback();
            }catch(SQLException se2){
               se2.getMessage();
            }
        }finally{
            try {connection.close();} catch (SQLException e){
                System.out.println("Imposible cerrar cursores");              
            }
        }            
        return test;
    }
    
    public void modificarPassword(String nick, String passwordNueva, String passwordVieja) {
        Connection connection = null;
        try {
            connection = conectar();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreparedStatement consulta = null;
        try {
            connection.setAutoCommit(false);
            
            if(comprobarPassword(connection, nick, passwordVieja)){
                consulta = connection.prepareStatement("update usuarios set password = ? where nick = ?");
                consulta.setString(1, passwordNueva);
                consulta.setString(2, nick);
                consulta.executeUpdate();
            }
                                                 
            connection.commit();
        }catch (SQLException e){
            System.out.println(e.getMessage());         
            try{
		if(connection!=null)
                    connection.rollback();
            }catch(SQLException se2){
               se2.getMessage();
            }
        }finally{
            try {connection.close();} catch (SQLException e){
                System.out.println("Imposible cerrar cursores");              
            }
        }            
    }
    
    public void anhadirAmigo(String nick, String amigo) {
        Connection connection = null;
        try {
            connection = conectar();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreparedStatement consulta = null;
        try {
            connection.setAutoCommit(false);
            
            consulta = connection.prepareStatement("insert into amigos values(?,?)");
            consulta.setString(1, amigo);
            consulta.setString(2, nick);
            consulta.executeUpdate();            
                                                 
            connection.commit();
        }catch (SQLException e){
            System.out.println(e.getMessage());         
            try{
		if(connection!=null)
                    connection.rollback();
            }catch(SQLException se2){
               se2.getMessage();
            }
        }finally{
            try {connection.close();} catch (SQLException e){
                System.out.println("Imposible cerrar cursores");              
            }
        }        
    }
    
    public void borrarAmigo(String nick, String amigo) {
        Connection connection = null;
        try {
            connection = conectar();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreparedStatement consulta = null;
        try {
            connection.setAutoCommit(false);
            
            consulta = connection.prepareStatement("delete from amigos where (usuario1 = ? and usuario2 = ?) or (usuario1 = ? and usuario2 = ?)");
            consulta.setString(1, amigo);
            consulta.setString(2, nick);
            consulta.setString(3, nick);
            consulta.setString(4, amigo);
            consulta.executeUpdate();            
                                                 
            connection.commit();
        }catch (SQLException e){
            System.out.println(e.getMessage());         
            try{
		if(connection!=null)
                    connection.rollback();
            }catch(SQLException se2){
               se2.getMessage();
            }
        }finally{
            try {connection.close();} catch (SQLException e){
                System.out.println("Imposible cerrar cursores");              
            }
        } 
    }
    
    public Usuario[] desconectarse(String nick) {
        Connection connection = null;
        try {
            connection = conectar();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreparedStatement consulta = null;
        ArrayList<Usuario> amigos = null;
        try {
            connection.setAutoCommit(false);
            
            consulta = connection.prepareStatement("update usuarios set conectado = ? where nick = ?");
            consulta.setBoolean(1, false);
            consulta.setString(2, nick);
            consulta.executeUpdate();
            
            amigos = recuperarAmigosConectados(connection, nick);      
                                                 
            connection.commit();
        }catch (SQLException e){
            System.out.println(e.getMessage());         
            try{
		if(connection!=null)
                    connection.rollback();
            }catch(SQLException se2){
               se2.getMessage();
            }
        }finally{
            try {connection.close();} catch (SQLException e){
                System.out.println("Imposible cerrar cursores");              
            }
        }        
        if(amigos.size() != 0){
            Usuario[] usuarios = new Usuario[amigos.size()];
            for(int i=0; i< amigos.size(); i++)
                usuarios[i] = amigos.get(i);
            return usuarios;
        }
        else
            return null;
    }
    
    private boolean estaPeticion(Connection conn, String peticionario, String receptor) throws SQLException{
        PreparedStatement consulta = null;
        ResultSet resultado = null;
        
        consulta = conn.prepareStatement("select count(*) from peticionesPendientes where (peticionario = ? and receptor = ?) or (peticionario = ? and receptor = ?)");
        consulta.setString(1,peticionario);
        consulta.setString(2, receptor);
        consulta.setString(3, receptor);
        consulta.setString(4, peticionario);
        resultado = consulta.executeQuery();
        resultado.next();
        if(resultado.getInt(1) == 0)
            return true;
        else
            return false;
    }
    
    public void guardarPeticionAmistad(String peticionario, String receptor) {
        Connection connection = null;
        try {
            connection = conectar();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreparedStatement consulta = null;
        try {
            connection.setAutoCommit(false);
            
            if(estaPeticion(connection,peticionario,receptor)){
                consulta = connection.prepareStatement("insert into peticionesPendientes(peticionario,receptor) values(?,?)");
                consulta.setString(1, peticionario);
                consulta.setString(2, receptor);
                consulta.executeUpdate();
            }
                                                 
            connection.commit();
        }catch (SQLException e){
            System.out.println(e.getMessage());         
            try{
		if(connection!=null)
                    connection.rollback();
            }catch(SQLException se2){
               se2.getMessage();
            }
        }finally{
            try {connection.close();} catch (SQLException e){
                System.out.println("Imposible cerrar cursores");              
            }
        }        
    }
    
    public void eliminarPeticion(String receptor, String peticionario) {
        Connection connection = null;
        try {
            connection = conectar();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreparedStatement consulta = null;
        try {
            connection.setAutoCommit(false);
            
            consulta = connection.prepareStatement("delete from peticionesPendientes where peticionario = ? and receptor = ?");
            consulta.setString(1, peticionario);
            consulta.setString(2, receptor);
            consulta.executeUpdate();
                                                 
            connection.commit();
        }catch (SQLException e){
            System.out.println(e.getMessage());         
            try{
		if(connection!=null)
                    connection.rollback();
            }catch(SQLException se2){
               se2.getMessage();
            }
        }finally{
            try {connection.close();} catch (SQLException e){
                System.out.println("Imposible cerrar cursores");              
            }
        }        
    }        
    
    public Usuario[] recpuerarSolicitudesAmistad(String receptor) {
        Connection connection = null;
        try {
            connection = conectar();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreparedStatement consulta = null;
        ResultSet resultado = null;       
        ResultSet resultado2 = null;
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            
            consulta = connection.prepareStatement("select peticionario from peticionesPendientes where receptor = ?");
            consulta.setString(1, receptor);            
            resultado = consulta.executeQuery();
            
            while(resultado.next()){
                consulta = connection.prepareStatement("select * from usuarios where nick = ?");
                consulta.setString(1, resultado.getString("peticionario"));
                resultado2 = consulta.executeQuery();
                if(resultado2.next()){
                    Usuario user = new Usuario(resultado2.getString("nick"), resultado2.getBoolean("conectado"), null);
                    usuarios.add(user);
                }                
            }
                                                 
            connection.commit();
        }catch (SQLException e){
            System.out.println(e.getMessage());         
            try{
		if(connection!=null)
                    connection.rollback();
            }catch(SQLException se2){
               se2.getMessage();
            }
        }finally{
            try {connection.close();} catch (SQLException e){
                System.out.println("Imposible cerrar cursores");              
            }
        }
        
        Usuario[] peticionarios = new Usuario[usuarios.size()];
        for(int i = 0; i < usuarios.size(); i++)
            peticionarios[i] = usuarios.get(i);
        return peticionarios;
    }
    
    public boolean estaOnline(String nick) {
        Connection connection = null;
        try {
            connection = conectar();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreparedStatement consulta = null;
        ResultSet resultado = null;       
        boolean test = false;
        try {
            connection.setAutoCommit(false);
            
            consulta = connection.prepareStatement("select count(*) from usuarios where nick = ? and conectado = true");
            consulta.setString(1, nick);            
            resultado = consulta.executeQuery();
            if(resultado.next()){
                if(resultado.getInt(1) == 1)
                    test = true;
            }
                                                 
            connection.commit();
        }catch (SQLException e){
            System.out.println(e.getMessage());         
            try{
		if(connection!=null)
                    connection.rollback();
            }catch(SQLException se2){
               se2.getMessage();
            }
        }finally{
            try {connection.close();} catch (SQLException e){
                System.out.println("Imposible cerrar cursores");              
            }
        }
        
        return test;
    }
    
    public Usuario recuperarUsuario(String nick) {
        Connection connection = null;
        try {
            connection = conectar();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreparedStatement consulta = null;
        ResultSet resultado = null;       
        Usuario usuario = null;
        try {
            connection.setAutoCommit(false);
            
            consulta = connection.prepareStatement("select * from usuarios where nick = ?");
            consulta.setString(1, nick);            
            resultado = consulta.executeQuery();
            if(resultado.next()){
                usuario = new Usuario(resultado.getString("nick"), resultado.getBoolean("conectado"), null);
            }
                                                 
            connection.commit();
        }catch (SQLException e){
            System.out.println(e.getMessage());         
            try{
		if(connection!=null)
                    connection.rollback();
            }catch(SQLException se2){
               se2.getMessage();
            }
        }finally{
            try {connection.close();} catch (SQLException e){
                System.out.println("Imposible cerrar cursores");              
            }
        }
        return usuario;
    }
    
    public boolean estaPendiente(String nick, String amigo) {
        Connection connection = null;
        try {
            connection = conectar();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreparedStatement consulta = null;
        ResultSet resultado = null;       
        boolean test = false;
        try {
            connection.setAutoCommit(false);
            
            consulta = connection.prepareStatement("select count(*) from peticionesPendientes where peticionario = ? and receptor = ?");
            consulta.setString(1, amigo);            
            consulta.setString(2, nick);
            resultado = consulta.executeQuery();
            if(resultado.next()){
                if(resultado.getInt(1) == 1)
                    test = true;
            }
                                                 
            connection.commit();
        }catch (SQLException e){
            System.out.println(e.getMessage());         
            try{
		if(connection!=null)
                    connection.rollback();
            }catch(SQLException se2){
               se2.getMessage();
            }
        }finally{
            try {connection.close();} catch (SQLException e){
                System.out.println("Imposible cerrar cursores");              
            }
        }
        
        return test;
    }
    
    public Usuario[] recuperarAmigos(String nick) {
        Connection connection = null;      
        try {
            connection = conectar();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Usuario> amigos = null;
        try {
            connection.setAutoCommit(false);
                        
            amigos = recuperarAmigosConectados(connection, nick);
                                                 
            connection.commit();
        }catch (SQLException e){
            System.out.println(e.getMessage());         
            try{
		if(connection!=null)
                    connection.rollback();
            }catch(SQLException se2){
               se2.getMessage();
            }
        }finally{
            try {connection.close();} catch (SQLException e){
                System.out.println("Imposible cerrar cursores");              
            }
        }
        
        if(amigos != null){
            Usuario[] usuarios = new Usuario[amigos.size()];
            for(int i = 0; i < amigos.size(); i++)
                usuarios[i] = amigos.get(i);
            return usuarios;
        }
        else
            return null;
    }
    
}
