package com.autogest.dao;

import com.autogest.config.DBConfig;
import com.autogest.models.Cliente;
import java.sql.*;

public class ClienteDAO {
    public boolean insertar(Cliente c) {
        String sql = "INSERT INTO CLIENTE (nit, nombre, representante_legal, telefono, email, direccion, tipo_entidad) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = DBConfig.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNit());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getRepresentanteLegal());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getEmail());
            ps.setString(6, c.getDireccion());
            ps.setString(7, c.getTipoEntidad());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}