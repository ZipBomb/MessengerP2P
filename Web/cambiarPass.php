<?php

function cambiarPass(){
	$con=mysqli_connect("localhost","usuarioP2P","aplicacionP2P","MessengerP2P");

	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	
	$nick=htmlspecialchars($_POST['nick']);
	$password=htmlspecialchars($_POST['password']);
	
	$query = "UPDATE usuarios SET password='$password' WHERE nick='$nick'";
	if (mysqli_query($con, $query)) {
    		mysqli_close(con);
		return true;
	} else {
    		mysqli_close(con);
		return true;
	}
}
?> 
<!DOCTYPE html>
	<html >
	<head>
	  <meta charset="UTF-8">
	  <title>Sign-Up/Login Form</title>
	  <link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
	  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
	  <link rel="stylesheet" href="css/style.css">
	    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

    <script src="js/index.js"></script>

	  
	</head>

	<body>
		<div class="form">
		<div id="login"> 
	  
<?php
if(cambiarPass()){?>
			<h1>Contraseña cambiada con Exito</h1>			    					
<?php
}
else {?>
			<h1>Error al cambiar la contraseña</h1>
<?php
}
?>			
		</div>
		</div>	
	</body>
	</html>
