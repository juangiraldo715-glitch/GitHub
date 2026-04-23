<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>Nuevo Cliente</title></head>
<body>
    <h2>Registrar Nuevo Cliente</h2>
    <form action="ClienteServlet" method="POST">
        NIT: <input type="text" name="nit" required><br>
        Nombre: <input type="text" name="nombre" required><br>
        Representante: <input type="text" name="representante" required><br>
        Teléfono: <input type="text" name="telefono"><br>
        Email: <input type="email" name="email"><br>
        Dirección: <input type="text" name="direccion"><br>
        Tipo: <select name="tipo">
            <option value="Alcaldía">Alcaldía</option>
            <option value="Gobernación">Gobernación</option>
        </select><br>
        <button type="submit">Guardar Cliente</button>
    </form>
</body>
</html>