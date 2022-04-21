<?php
date_default_timezone_set("America/Lima");
$pdo = new PDO('mysql:host=localhost;port=3306;dbname=id17957672_restaurante','id17957672_fmanco2','IX]_KY#@^Nd0BqX%');
$pdo->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
$hoy = date('Y-m-d H:i:s');
if($_SERVER['REQUEST_METHOD'] == "POST"){
    parse_str(file_get_contents("php://input"),$post_vars);
    /*Se asume que el App se encarga de verificar que los datos se envíen
    correctamente*/
    $nombre   = $post_vars['nombre'];
    $apellidos   = $post_vars['apellidos'];
    $correo   = $post_vars['correo'];
    $telefono   = $post_vars['telefono'];
    $idmenu  = $post_vars['idmenu'];
    $cantidad   = $post_vars['cantidad'];
    $key      = $post_vars['key'];//TOKEN
    
    /*Se envío correctamente la clave para iniciar el registro*/
    if($key=="12345")
    {
                $query = "INSERT INTO `reservas` (`nombre`, `apellidos`, `correo`, `telefono`, `idmenu`, `cantidad`) VALUES 
                            ('$nombre', '$apellidos', '$correo', '$telefono', '$idmenu', '$cantidad')";

                try{
                        $stmt=$pdo->prepare($query);
                        $stmt->execute();
                }catch(Exception $ex){
                        $json=array("REGISTRO"=>"NO");
                        header('Content-Type: application/json');
                        echo json_encode($json);
                        return;   
                }
                $json=array("REGISTRO"=>"OK");
                header('Content-Type: application/json');
                echo json_encode($json);
                return;   
                
            
            
           
    }//Fin del if de KEY
    else{
        $json=array("KEY"=>"BAD KEY");
        header('Content-Type: application/json');
        echo json_encode($json);
    }
}
else{
        $json=array("Respuesta"=>"NO POST");
        header('Content-Type: application/json');
        echo json_encode($json);
}
?>