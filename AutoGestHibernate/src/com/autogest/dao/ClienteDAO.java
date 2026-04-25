package com.autogest.dao;

import com.autogest.entity.Cliente;
import com.autogest.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Clase DAO para la entidad Cliente.
 * Contiene todas las operaciones CRUD (Create, Read, Update, Delete)
 * utilizando Hibernate como ORM.
 * 
 * @author Juan David Giraldo Sánchez
 * @version 1.0
 */
public class ClienteDAO {
    
    // ==================== CREATE (Crear) ====================
    
    /**
     * Guarda un nuevo cliente en la base de datos.
     * 
     * @param cliente El objeto Cliente a guardar
     * @return El ID generado para el nuevo cliente, o -1 si hubo error
     */
    public int guardar(Cliente cliente) {
        Transaction transaction = null;
        int idGenerado = -1;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Iniciamos la transacción
            transaction = session.beginTransaction();
            
            // Guardamos el objeto en la base de datos
            idGenerado = (int) session.save(cliente);
            
            // Confirmamos la transacción
            transaction.commit();
            
            System.out.println("✅ Cliente guardado exitosamente. ID: " + idGenerado);
            
        } catch (Exception e) {
            // Si algo sale mal, hacemos rollback
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("❌ Error al guardar cliente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return idGenerado;
    }
    
    // ==================== READ (Leer) ====================
    
    /**
     * Obtiene todos los clientes de la base de datos.
     * 
     * @return Lista de todos los clientes
     */
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Usamos HQL (Hibernate Query Language)
            Query<Cliente> query = session.createQuery("FROM Cliente ORDER BY idCliente", Cliente.class);
            clientes = query.list();
            
            System.out.println("📋 Se encontraron " + clientes.size() + " clientes");
            
        } catch (Exception e) {
            System.err.println("❌ Error al obtener clientes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return clientes;
    }
    
    /**
     * Busca un cliente por su ID.
     * 
     * @param id El ID del cliente a buscar
     * @return El cliente encontrado, o null si no existe
     */
    public Cliente obtenerPorId(int id) {
        Cliente cliente = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            cliente = session.get(Cliente.class, id);
            
            if (cliente != null) {
                System.out.println("🔍 Cliente encontrado: " + cliente.getNombre());
            } else {
                System.out.println("⚠️ No se encontró cliente con ID: " + id);
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error al buscar cliente por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return cliente;
    }
    
    /**
     * Busca un cliente por su NIT.
     * 
     * @param nit El NIT del cliente a buscar
     * @return El cliente encontrado, o null si no existe
     */
    public Cliente obtenerPorNit(String nit) {
        Cliente cliente = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Usamos HQL con parámetros
            Query<Cliente> query = session.createQuery(
                "FROM Cliente WHERE nit = :nit", Cliente.class);
            query.setParameter("nit", nit);
            
            cliente = query.uniqueResult();
            
        } catch (Exception e) {
            System.err.println("❌ Error al buscar cliente por NIT: " + e.getMessage());
            e.printStackTrace();
        }
        
        return cliente;
    }
    
    // ==================== UPDATE (Actualizar) ====================
    
    /**
     * Actualiza los datos de un cliente existente.
     * 
     * @param cliente El cliente con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(Cliente cliente) {
        Transaction transaction = null;
        boolean exito = false;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // Actualizamos el objeto en la base de datos
            session.update(cliente);
            
            transaction.commit();
            exito = true;
            
            System.out.println("✅ Cliente actualizado exitosamente. ID: " + cliente.getIdCliente());
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("❌ Error al actualizar cliente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return exito;
    }
    
    /**
     * Actualiza un cliente usando merge (otra forma de hacerlo).
     * 
     * @param cliente El cliente con los datos actualizados
     * @return El cliente actualizado
     */
    public Cliente actualizarConMerge(Cliente cliente) {
        Transaction transaction = null;
        Cliente clienteActualizado = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // Merge actualiza o inserta según corresponda
            clienteActualizado = session.merge(cliente);
            
            transaction.commit();
            System.out.println("✅ Cliente actualizado con merge: " + clienteActualizado.getNombre());
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("❌ Error en merge: " + e.getMessage());
            e.printStackTrace();
        }
        
        return clienteActualizado;
    }
    
    // ==================== DELETE (Eliminar) ====================
    
    /**
     * Elimina un cliente de la base de datos por su ID.
     * 
     * @param id El ID del cliente a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(int id) {
        Transaction transaction = null;
        boolean exito = false;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // Primero obtenemos el cliente
            Cliente cliente = session.get(Cliente.class, id);
            
            if (cliente != null) {
                // Luego lo eliminamos
                session.delete(cliente);
                transaction.commit();
                exito = true;
                System.out.println("✅ Cliente eliminado exitosamente. ID: " + id);
            } else {
                System.out.println("⚠️ No se encontró cliente con ID: " + id);
            }
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("❌ Error al eliminar cliente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return exito;
    }
    
    /**
     * Elimina un cliente de la base de datos por el objeto.
     * 
     * @param cliente El cliente a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(Cliente cliente) {
        return eliminar(cliente.getIdCliente());
    }
    
    // ==================== MÉTODOS ADICIONALES ÚTILES ====================
    
    /**
     * Obtiene el total de clientes registrados.
     * 
     * @return Número total de clientes
     */
    public long contarClientes() {
        long total = 0;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Cliente", Long.class);
            total = query.uniqueResult();
            
            System.out.println("📊 Total de clientes: " + total);
            
        } catch (Exception e) {
            System.err.println("❌ Error al contar clientes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return total;
    }
    
    /**
     * Busca clientes por nombre (búsqueda parcial).
     * 
     * @param nombreParte Parte del nombre a buscar
     * @return Lista de clientes que coinciden
     */
    public List<Cliente> buscarPorNombre(String nombreParte) {
        List<Cliente> clientes = null;
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Cliente> query = session.createQuery(
                "FROM Cliente WHERE nombre LIKE :nombre", Cliente.class);
            query.setParameter("nombre", "%" + nombreParte + "%");
            
            clientes = query.list();
            System.out.println("🔍 Se encontraron " + clientes.size() + " clientes con nombre similar");
            
        } catch (Exception e) {
            System.err.println("❌ Error en búsqueda por nombre: " + e.getMessage());
            e.printStackTrace();
        }
        
        return clientes;
    }
}