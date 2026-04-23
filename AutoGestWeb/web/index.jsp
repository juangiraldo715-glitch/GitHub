<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>AutoGest Web - Inicio</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body class="container mt-5">
        <h1>Gestión de Clientes</h1>
        <a href="formularioCliente.jsp" class="btn btn-primary">Crear Nuevo Cliente</a>
        
        <% if(request.getParameter("msj") != null) { %>
            <div class="alert alert-success mt-3">¡Cliente guardado con éxito!</div>
        <% } %>
    </body>
</html>