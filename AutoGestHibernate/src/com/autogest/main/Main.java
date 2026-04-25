package com.autogest.main;

import com.autogest.dao.ClienteDAO;
import com.autogest.entity.Cliente;
import com.autogest.util.HibernateUtil;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal para probar el CRUD de clientes.
 * Se puede ejecutar como aplicación Java normal para verificar
 * que Hibernate funciona correctamente.
 * 
 * @author Juan David Giraldo Sánchez
 * @version 1.0
 */
public class Main {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static final ClienteDAO clienteDAO = new ClienteDAO();
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              AUTO-GEST TALLER PRO - HIBERNATE                 ║");
        System.out.println("║                     Módulo de Clientes                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        
        // Probar que Hibernate se configuró correctamente
        try {
            HibernateUtil.getSessionFactory();
            System.out.println("\n✅ Hibernate configurado correctamente!\n");
        } catch (Exception e) {
            System.err.println("❌ Error al configurar Hibernate: " + e.getMessage());
            System.exit(1);
        }
        
        // Menú principal
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    listarClientes();
                    break;
                case 2:
                    buscarClientePorId();
                    break;
                case 3:
                    crearCliente();
                    break;
                case 4:
                    actualizarCliente();
                    break;
                case 5:
                    eliminarCliente();
                    break;
                case 6:
                    System.out.println("\n👋 ¡Hasta luego!");
                    salir = true;
                    break;
                default:
                    System.out.println("❌ Opción inválida. Intente nuevamente.");
            }
        }
        
        // Cerramos Hibernate
        HibernateUtil.shutdown();
    }
    
    private static void mostrarMenu() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                        MENÚ PRINCIPAL                         ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Listar todos los clientes                                 ║");
        System.out.println("║  2. Buscar cliente por ID                                     ║");
        System.out.println("║  3. Crear nuevo cliente                                       ║");
        System.out.println("║  4. Actualizar cliente                                        ║");
        System.out.println("║  5. Eliminar cliente                                          ║");
        System.out.println("║  6. Salir                                                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.print("👉 Seleccione una opción: ");
    }
    
    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void listarClientes() {
        List<Cliente> clientes = clienteDAO.obtenerTodos();
        if (clientes.isEmpty()) {
            System.out.println("\n📋 No hay clientes registrados.\n");
            return;
        }
        
        System.out.println("\n📋 LISTADO DE CLIENTES:\n");
        for (Cliente c : clientes) {
            System.out.println("  ├─ ID: " + c.getIdCliente());
            System.out.println("  │   NIT: " + c.getNit());
            System.out.println("  │   Nombre: " + c.getNombre());
            System.out.println("  │   Representante: " + c.getRepresentanteLegal());
            System.out.println("  │   Teléfono: " + c.getTelefono());
            System.out.println("  └─ Tipo: " + c.getTipoEntidad());
            System.out.println();
        }
    }
    
    private static void buscarClientePorId() {
        System.out.print("\n🔍 Ingrese el ID del cliente: ");
        int id = leerOpcion();
        
        Cliente cliente = clienteDAO.obtenerPorId(id);
        if (cliente != null) {
            System.out.println("\n📄 CLIENTE ENCONTRADO:");
            System.out.println("  ID: " + cliente.getIdCliente());
            System.out.println("  NIT: " + cliente.getNit());
            System.out.println("  Nombre: " + cliente.getNombre());
            System.out.println("  Representante: " + cliente.getRepresentanteLegal());
            System.out.println("  Teléfono: " + cliente.getTelefono());
            System.out.println("  Tipo: " + cliente.getTipoEntidad());
            if (cliente.getEmail() != null) System.out.println("  Email: " + cliente.getEmail());
            if (cliente.getDireccion() != null) System.out.println("  Dirección: " + cliente.getDireccion());
        } else {
            System.out.println("❌ No se encontró cliente con ID: " + id);
        }
    }
    
    private static void crearCliente() {
        System.out.println("\n➕ CREAR NUEVO CLIENTE:\n");
        
        System.out.print("   NIT (formato: 123456789-1): ");
        String nit = scanner.nextLine();
        
        System.out.print("   Nombre de la entidad: ");
        String nombre = scanner.nextLine();
        
        System.out.print("   Representante Legal: ");
        String representante = scanner.nextLine();
        
        System.out.print("   Teléfono: ");
        String telefono = scanner.nextLine();
        
        System.out.print("   Email (opcional): ");
        String email = scanner.nextLine();
        
        System.out.print("   Dirección (opcional): ");
        String direccion = scanner.nextLine();
        
        System.out.print("   Tipo Entidad (Alcaldía/Gobernación/Ministerio/Instituto/Empresa): ");
        String tipoEntidad = scanner.nextLine();
        
        Cliente cliente = new Cliente(nit, nombre, representante, telefono, tipoEntidad);
        if (email != null && !email.isEmpty()) cliente.setEmail(email);
        if (direccion != null && !direccion.isEmpty()) cliente.setDireccion(direccion);
        
        int id = clienteDAO.guardar(cliente);
        if (id > 0) {
            System.out.println("✅ Cliente creado exitosamente con ID: " + id);
        } else {
            System.out.println("❌ Error al crear el cliente.");
        }
    }
    
    private static void actualizarCliente() {
        System.out.print("\n✏️ Ingrese el ID del cliente a actualizar: ");
        int id = leerOpcion();
        
        Cliente cliente = clienteDAO.obtenerPorId(id);
        if (cliente == null) {
            System.out.println("❌ No se encontró cliente con ID: " + id);
            return;
        }
        
        System.out.println("\n📄 Datos actuales del cliente:");
        System.out.println("  NIT: " + cliente.getNit());
        System.out.println("  Nombre: " + cliente.getNombre());
        System.out.println("  Representante: " + cliente.getRepresentanteLegal());
        System.out.println("  Teléfono: " + cliente.getTelefono());
        System.out.println("  Tipo: " + cliente.getTipoEntidad());
        
        System.out.println("\n📝 Ingrese los nuevos datos (deje en blanco para mantener):\n");
        
        System.out.print("   Nuevo NIT [" + cliente.getNit() + "]: ");
        String nit = scanner.nextLine();
        if (!nit.isEmpty()) cliente.setNit(nit);
        
        System.out.print("   Nuevo nombre [" + cliente.getNombre() + "]: ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) cliente.setNombre(nombre);
        
        System.out.print("   Nuevo representante [" + cliente.getRepresentanteLegal() + "]: ");
        String representante = scanner.nextLine();
        if (!representante.isEmpty()) cliente.setRepresentanteLegal(representante);
        
        System.out.print("   Nuevo teléfono [" + cliente.getTelefono() + "]: ");
        String telefono = scanner.nextLine();
        if (!telefono.isEmpty()) cliente.setTelefono(telefono);
        
        System.out.print("   Nuevo tipo entidad [" + cliente.getTipoEntidad() + "]: ");
        String tipoEntidad = scanner.nextLine();
        if (!tipoEntidad.isEmpty()) cliente.setTipoEntidad(tipoEntidad);
        
        if (clienteDAO.actualizar(cliente)) {
            System.out.println("✅ Cliente actualizado correctamente.");
        } else {
            System.out.println("❌ Error al actualizar el cliente.");
        }
    }
    
    private static void eliminarCliente() {
        System.out.print("\n🗑️ Ingrese el ID del cliente a eliminar: ");
        int id = leerOpcion();
        
        Cliente cliente = clienteDAO.obtenerPorId(id);
        if (cliente == null) {
            System.out.println("❌ No se encontró cliente con ID: " + id);
            return;
        }
        
        System.out.println("\n⚠️ ¿Está seguro de eliminar a " + cliente.getNombre() + "? (s/N): ");
        String confirmacion = scanner.nextLine();
        
        if (confirmacion.equalsIgnoreCase("s")) {
            if (clienteDAO.eliminar(id)) {
                System.out.println("✅ Cliente eliminado correctamente.");
            } else {
                System.out.println("❌ Error al eliminar el cliente.");
            }
        } else {
            System.out.println("❌ Eliminación cancelada.");
        }
    }
}