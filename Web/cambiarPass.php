<?php

	$con=mysqli_connect("localhost","usuarioP2P","aplicacionP2P","MessengerP2P");

	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	
	$nick=htmlspecialchars($_POST['nick']);
	$password=htmlspecialchars($_POST['password']);
	echo "$nick";
	echo "$password";
	$query = "update usuarios set password='$password' where nick='$nick'";
	if (mysqli_query($con, $query)) {
    		echo "Contraseña cambiada con exito";
	} else {
    		echo "Error: " . $query . "<br>" . mysqli_error($con);
	}
	mysqli_close(con);
?> 
