<?php

	$con=mysqli_connect("localhost","usuarioP2P","aplicacionP2P","MessengerP2P");

	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	
	$nick=htmlspecialchars($_POST['nick']);
	$password=htmlspecialchars($_POST['password']);
	echo "asdsd";
	echo $nick;
	$query = "INSERT INTO usuarios(nick,password,conectado) values('$nick','$password',false)";
	if (mysqli_query($con, $query)) {
    		echo "Usuario creado con exito";
	} else {
    		echo "Error: " . $query . "<br>" . mysqli_error($con);
	}
	mysqli_close(con);
?> 
