/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import modelo.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author ZURITA
 */
public class Conexion {
    private static String url = "jdbc:mysql://localhost/dbtpsoft";
    private static String user = "root";
    private static String password = "";
    private static SessionFactory miFactory;
    
    public static boolean conexionDB(){
        try {
            System.out.println("Intentando");
            Connection miConexion = DriverManager.getConnection(url, user, password);
            System.out.println("Conectado");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private static void initFactory(){
        miFactory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Rubro.class)
                .addAnnotatedClass(Producto.class)
                .addAnnotatedClass(Marca.class)
                .addAnnotatedClass(PorcentajeIVA.class)
                .addAnnotatedClass(Talle.class)
                .addAnnotatedClass(Stock.class)
                .addAnnotatedClass(Color.class)
                .addAnnotatedClass(Producto.class)
                .addAnnotatedClass(PorcentajeIVA.class)
                .addAnnotatedClass(LineaDeVenta.class)
                .addAnnotatedClass(Venta.class)
                .addAnnotatedClass(Cliente.class)
                .addAnnotatedClass(CondicionTributaria.class)
                .buildSessionFactory();
    }

    public static SessionFactory getMiFactory() {
        initFactory();
        return miFactory;
    }
}
