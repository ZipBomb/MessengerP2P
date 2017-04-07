#!/bin/bash
shopt -s extglob # Activa las expresiones regulares

# Sin argumentos sólo para actualizar cambios
if [ -z $1 ]; then
     
    rm -rf !(test.sh)

    cp ../src/*.java .
    cp ../src/*.idl .

    idlj -fall IServidorCliente.idl
    javac *.java ClienteP2P/*.java 

# Hay que lanzar primero el ClienteP2P ya que está
# jugando el rol de servidor en este caso (al hacerlo
# ya se levanta el servicio ORBD). 
elif [ $1 == "cliente" ]; then

    orbd -ORBInitialPort 6666 &
    sleep 2 # A veces rechaza la conexión si intentas conectarte muy rápido
    java Cliente -ORBInitialPort 6666 -ORBInitialHost localhost 
    # Dejamos que bloquee el terminal para ver los cambios

# Lanzar en otro terminal
elif [ $1 == "servidor" ]; then
               
    java Servidor -ORBInitialPort 6666 -ORBInitialHost localhost

fi 
# Al final hay que matar el proceso "orbd" buscando con pgrep o netstat
