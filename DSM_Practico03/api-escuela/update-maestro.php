<?php
$host = "localhost";
$dbname = "id20739115_escuela";
$username = "id20739115_root";
$password = '$angrefriaPxndx123';
// Establecer credenciales para la autenticación básica
$auth_username = "admin";
$auth_password = "admin123";

// Obtener las credenciales de autenticación del encabezado HTTP
if (!isset($_SERVER['PHP_AUTH_USER']) || !isset($_SERVER['PHP_AUTH_PW'])
    || $_SERVER['PHP_AUTH_USER'] != $auth_username || $_SERVER['PHP_AUTH_PW'] != $auth_password) {
    header('HTTP/1.1 401 Unauthorized');
    header('WWW-Authenticate: Basic realm="Acceso restringido"');
    exit;
}

// Conectar a la base de datos
try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
} catch (PDOException $e) {
    die("Error de conexión: " . $e->getMessage());
}

// Establecer el encabezado de respuesta a JSON
header('Content-Type: application/json');

// Comprobar el método HTTP utilizado
$method = $_SERVER['REQUEST_METHOD'];
switch ($method) {
    case 'POST':
        try {
            //code...
            $data = json_decode(file_get_contents('php://input'), true);
            if ($data['edad']==-987) {
                $stmt = $pdo->prepare("DELETE FROM maestros WHERE id = ?");
                $stmt->execute([$data['id']]);
                echo json_encode(['mensaje' => 'El maestro ha sido eliminado correctamente.']);          
            } else {
            // Actualizar un maestro existente              
            $stmt = $pdo->prepare("UPDATE maestros SET nombre = ?, apellido = ?, edad = ? WHERE id = ?");
            $stmt->execute([$data['nombre'], $data['apellido'], $data['edad'], $data['id']]);
            $maestro = [
                'id' => $data['id'],
                'nombre' => $data['nombre'],
                'apellido' => $data['apellido'],
                'edad' => $data['edad']
                ];
            echo json_encode($maestro);    
            }   
        } catch (\Throwable $th) {
            //throw $th;
            echo json_encode($th);
        } 
        break;
     default:
        // Método HTTP no válido
        header('HTTP/1.1 405 Method Not Allowed');
        echo json_encode(['error' => 'Método HTTP no válido']);
        break;
  }

//Cerrar la conexión con la base de datos
$pdo = null;
?>