#!/usr/bin/python
import MySQLdb


def conexion(conn, cursor):
    conn = MySQLdb.connect("localhost","usuarioP2P","password","P2P")
    cursor = conn.cursor()

def identificar(nick, password, conn, cursor):
    try:         
        cursor.execute("SELECT * FROM usuarios WHERE nick = %s and password = %s", (nick, password))
        if cursor.rowcount == 1:
            test = True;
        else:
            test = False;

        print test
    except MySQLdb.Error, e:
        print "Error %d: %s" % (e.args[0],e.args[1])
        sys.exit(1)
                    
    finally:                    
        if conn:    
            conn.close()

def main():
    conn = MySQLdb.connect("localhost","usuarioP2P","password","P2P")
    cursor = conn.cursor()
    identificar("Pepe", "dsdsds", conn, cursor)

if __name__ == "__main__":
        main()
