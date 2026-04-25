package com.autogest.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa un cliente (entidad estatal) en el sistema.
 * Las anotaciones de JPA (Jakarta Persistence) le indican a Hibernate 
 * cómo mapear esta clase a la tabla correspondiente en la base de datos.
 * 
 * @author Juan David Giraldo Sánchez
 * @version 1.0
 */
@Entity 
@Table(name = "cliente") // Especificamos el nombre de la tabla en la BD
public class Cliente implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremental
    @Column(name = "id_cliente")
    private int idCliente;
    
    @Column(name = "nit", unique = true, nullable = false, length = 20)
    private String nit;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "representante_legal", nullable = false, length = 100)
    private String representanteLegal;
    
    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;
    
    @Column(name = "email", length = 50)
    private String email;
    
    @Column(name = "direccion", length = 100)
    private String direccion;
    
    @Column(name = "tipo_entidad", nullable = false, length = 30)
    private String tipoEntidad;
    
    @Column(name = "fecha_registro", insertable = false, updatable = false)
    private Timestamp fechaRegistro;
    
    // ========== CONSTRUCTORES ==========
    
    public Cliente() {
        // Constructor vacío necesario para Hibernate
    }
    
    public Cliente(String nit, String nombre, String representanteLegal, 
                   String telefono, String tipoEntidad) {
        this.nit = nit;
        this.nombre = nombre;
        this.representanteLegal = representanteLegal;
        this.telefono = telefono;
        this.tipoEntidad = tipoEntidad;
    }
    
    // ========== GETTERS Y SETTERS ==========
    
    public int getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    public String getNit() {
        return nit;
    }
    
    public void setNit(String nit) {
        this.nit = nit;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getRepresentanteLegal() {
        return representanteLegal;
    }
    
    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getTipoEntidad() {
        return tipoEntidad;
    }
    
    public void setTipoEntidad(String tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }
    
    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    // ========== MÉTODO toString ==========
    
    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nit='" + nit + '\'' +
                ", nombre='" + nombre + '\'' +
                ", representanteLegal='" + representanteLegal + '\'' +
                ", telefono='" + telefono + '\'' +
                ", tipoEntidad='" + tipoEntidad + '\'' +
                '}';
    }
}