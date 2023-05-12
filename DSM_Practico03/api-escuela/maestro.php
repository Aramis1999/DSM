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
    case 'GET':
        // Obtener un maestro específico o todos los maestros
        if (isset($_GET['id'])) {
            // Obtener un maestro específico
            $stmt = $pdo->prepare("SELECT * FROM maestros WHERE id = ? ");
            $stmt->execute([$_GET['id']]);
            $maestro = $stmt->fetch(PDO::FETCH_ASSOC);
            echo json_encode($maestro);
        } else {
            // Obtener todos los maestros
            $stmt = $pdo->query("SELECT * FROM maestros order by id desc");
            $maestros = $stmt->fetchAll(PDO::FETCH_ASSOC);
            echo json_encode($maestros);
        }
        break;
    case 'POST':
        // Crear un nuevo maestro
        $data = json_decode(file_get_contents('php://input'), true);
        $stmt = $pdo->prepare("INSERT INTO maestros (nombre, apellido, edad) VALUES (?, ?, ?)");
        $stmt->execute([$data['nombre'], $data['apellido'], $data['edad']]);
        $maestro_id = $pdo->lastInsertId();
        $maestro = [
            'id' => $maestro_id,
            'nombre' => $data['nombre'],
            'apellido' => $data['apellido'],
            'edad' => $data['edad']
        ];
        echo json_encode($maestro);
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