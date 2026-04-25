package com.autogest.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Clase utilitaria para manejar la fábrica de sesiones de Hibernate.
 * Sigue el patrón Singleton para asegurar una única instancia de SessionFactory.
 * 
 * @author Juan David Giraldo Sánchez
 * @version 1.0
 */
public class HibernateUtil {
    
    // SessionFactory es un objeto pesado, por eso lo creamos una sola vez
    private static final SessionFactory sessionFactory = buildSessionFactory();
    
    /**
     * Construye la SessionFactory leyendo el archivo de configuración hibernate.cfg.xml
     * 
     * @return SessionFactory lista para usar
     */
    private static SessionFactory buildSessionFactory() {
        try {
            // Cargamos la configuración desde el archivo XML en resources
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Si algo sale mal, mostramos el error y detenemos la ejecución
            System.err.println("Error al inicializar SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    /**
     * Método público para obtener la SessionFactory
     * 
     * @return SessionFactory única del sistema
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    /**
     * Cierra la SessionFactory liberando recursos
     */
    public static void shutdown() {
        getSessionFactory().close();
    }
}