/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.List;
import modelo.Producto;
import modelo.Stock;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author ZURITA
 */
public class RepositorioProducto {
    private static SessionFactory miFactory;
    private static Session miSession;

    public static void addProducto(Producto producto){
        openFactoryAndSession();
        try {
            miSession.beginTransaction();
            miSession.save(producto);
            miSession.getTransaction().commit();     
            miSession.close();
        } finally {
            miFactory.close();
        }
    }
    
    public static Producto getProducto(int idProducto){
        Producto prod = null;
        openFactoryAndSession();
        try {
            miSession.beginTransaction();
            prod = miSession.get(Producto.class, idProducto);
            miSession.getTransaction().commit();
            miSession.close();
        } finally {
            miFactory.close();
            return prod;
        }
    }
    
    public static List<Producto> getProductos(){
        openFactoryAndSession();
        List<Producto> productos = null;
        try {
            miSession.beginTransaction();
            productos = miSession.createQuery("from Producto").getResultList();
            miSession.getTransaction().commit();
            miSession.close();
        } finally {
            miFactory.close();
            return productos;
        }
    }
    
    private static void openFactoryAndSession(){
        miFactory = miFactory = Conexion.getMiFactory();
        miSession = miFactory.openSession();
    }
}
