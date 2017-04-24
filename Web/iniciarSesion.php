<?php
$nick=htmlspecialchars($_POST['nick']);
function iniciarSesion(){
	$con=mysqli_connect("localhost","usuarioP2P","aplicacionP2P","MessengerP2P");

	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	
	
	$password=htmlspecialchars($_POST['password']);

	$query = "select count(*) from usuarios where nick = '$nick' and password='$password'";
	$result = mysqli_query($con, $query);
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

<?php
if(iniciarSesion()){?>
	<form method="post" action="cambiarPass.php">
		<p>Contraseña Nueva</p>
		<input type="password" name="password" required>
		<p><?php echo "$nick"; ?></p>
		<input type="hidden" name="nick" value="<?php $nick?>">
		<input type="submit" value="Cambiar">
	</form>
<?php
} 
else {
	echo "Error en la contraseña";
}
?>
