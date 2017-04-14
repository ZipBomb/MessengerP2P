
import ClienteP2P.IServidorCliente;
import ClienteP2P.Usuario;
import java.util.ArrayList;
import java.util.HashMap;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pablo
 */
public class HiloCliente extends Thread{
    
    private ArrayList<Integer> colaOrdenes;
    private ArrayList<IServidorCliente> colaInterfaces;
    private ArrayList<Usuario[]> colaArgumentos;

    public HiloCliente() {
        this.colaOrdenes = new ArrayList<>();
        this.colaInterfaces = new ArrayList<>();
        this.colaArgumentos = new ArrayList<>();
    }
    
    public void introducirOrden(Integer codigo, IServidorCliente interfaz, Usuario[] argumentos){
        colaOrdenes.add(codigo);
        colaInterfaces.add(interfaz);
        colaArgumentos.add(argumentos);
    }
    
    private void push(){
        colaOrdenes.remove(0);
        colaInterfaces.remove(0);
        colaArgumentos.remove(0);
    }
         
    
    @Override
    public void run(){
        while(true){    
            System.out.print("");
            if(!colaOrdenes.isEmpty()){                
                switch(colaOrdenes.get(0)){
                    //NotificarSolicitudes
                    case 1:
                        colaInterfaces.get(0).hola("asasa");
                        push();
                        break;
                    //NotificarConexion
                    case 2:
                        push();
                        break;
                    //NotificarDesconexion
                    case 3:
                        push();
                        break;
                    //NotificarNuevaAmistad
                    case 4:
                        push();
                        break;
                }
            }
        }
    }
    
}
