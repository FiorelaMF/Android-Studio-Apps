<?php
date_default_timezone_set("America/Lima");
$pdo = new PDO('mysql:host=localhost;port=3306;dbname=id17957672_restaurante','id17957672_fmanco2','IX]_KY#@^Nd0BqX%');
$pdo->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
$hoy = date('Y-m-d H:i:s');

if($_SERVER['REQUEST_METHOD']=="GET"){
    if(isset($_GET['key'])){
        $key=$_GET['key'];
        if($key!="12345")
            return;
        else{
            
            $query = "SELECT m.nombre, m.precio, m.cantidad-DT.cant_total AS menus_disp FROM menus m
            LEFT JOIN (SELECT idmenu, SUM(cantidad) AS cant_total FROM reservas GROUP BY idmenu) DT 
            ON m.idmenu = DT.idmenu";
            $json=array();
            header("Content-Type:application/json");

                try{
                    $stmt=$pdo->prepare($query);
                    $stmt->execute();
                    while($resultado = $stmt->fetch(PDO::FETCH_ASSOC)){
                        extract($resultado);
                        $json[]=array("nombre"=>$nombre,"precio"=>$precio,"menus_disp"=>$menus_disp);
                    }                  
                }catch(Exception $ex){
                    $json=array("QUERY2"=>"ER");
                    header('Content-Type: application/json');
                    echo json_encode($json);
                    return;
                }
                
                
                echo json_encode($json);
                return;
            
        }
          
    } else{
        return;
    }
    
                  
} else{
    $json=array("QUERY1"=>"ER");
                header('Content-Type: application/json');
                echo json_encode($json);
                return;
}
?>