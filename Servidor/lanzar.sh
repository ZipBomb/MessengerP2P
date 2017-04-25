#!/bin/bash
rm -rd Servidor/
mkdir Servidor
cp src/java.policy Servidor/
cp src/mysql-connector-java-5.1.23-bin.jar Servidor
cd src/
javac Servidor.java -d ../Servidor/
cd app/controlador/
javac * -d ../../../Servidor/
cd ../../../Servidor/
java -Djava.security.policy=java.policy -cp .:mysql-connector-java-5.1.23-bin.jar Servidor
