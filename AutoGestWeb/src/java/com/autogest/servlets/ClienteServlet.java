package com.autogest.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.autogest.dao.ClienteDAO;
import com.autogest.models.Cliente;
import java.io.IOException;

@WebServlet("/ClienteServlet")
public class ClienteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cliente c = new Cliente();
        c.setNit(request.getParameter("nit"));
        c.setNombre(request.getParameter("nombre"));
        c.setRepresentanteLegal(request.getParameter("representante"));
        c.setTelefono(request.getParameter("telefono"));
        c.setEmail(request.getParameter("email"));
        c.setDireccion(request.getParameter("direccion"));
        c.setTipoEntidad(request.getParameter("tipo"));

        ClienteDAO dao = new ClienteDAO();
        if(dao.insertar(c)) {
            response.sendRedirect("index.jsp?msj=exito");
        } else {
            response.sendRedirect("formularioCliente.jsp?err=1");
        }
    }
}