<?php
function iniciarSesion($nick){
	$con=mysqli_connect("localhost","usuarioP2P","aplicacionP2P","MessengerP2P");

	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}	
	$password=htmlspecialchars($_POST['password']);
	$query = "select count(*) from usuarios";
	$result = mysqli_query($con, "select * from usuarios where nick = '$nick' and password = '$password'");
    	$count = mysqli_num_rows($result);  	
	if($count == 0){
		mysqli_close(con);
		return false;
	}
	else {
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
	    

	  
	</head>

	<body>
	  <div class="form">
		<div id="login">   
          <h1>Cambiar Contraseña</h1>
		<form method="post" action="cambiarPass.php">
			
			
<?php
$nick=htmlspecialchars($_POST['nick']);
if(iniciarSesion($nick)){?>
				<div class="field-wrap">
			    <label>
			      Nueva Contraseña<span class="req">*</span>
			    </label>
			<input type="hidden" name="nick" value="<?php echo $nick;?>" required autocomplete="off">
			    <input type="password" name="password" required autocomplete="off"/>
			<br><br>
			<button type="submit" class="button button-block"/>Cambiar</button>
			</div>
		
<?php
} 
else {
?>
	<div class="field-wrap">
	<label>
	      Error en la autenficacion<span class="req"></span>
	    </label>
	</div>
<?php
}
?>
		
		</form>
</div>
	</div>
	</div>
	<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

    		<script src="js/index.js"></script>
	</body>
</html>
