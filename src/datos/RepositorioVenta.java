/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import modelo.LineaDeVenta;
import modelo.Venta;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author ZURITA
 */
public class RepositorioVenta {
    private static SessionFactory miFactory;
    private static Session miSession;

    public static void addVenta (Venta venta){
        openFactoryAndSession();
        try {
            miSession.beginTransaction();
            miSession.save(venta);
            miSession.getTransaction().commit();
            miSession.close();
        } finally {
            miFactory.close();
        }
    }
    
    public static void addLdv(LineaDeVenta ldv){
        openFactoryAndSession();
        try {
            miSession.beginTransaction();
            miSession.save(ldv);
            miSession.getTransaction().commit();
            miSession.close();
        } finally {
            miFactory.close();
        }
    }
    
    private static void openFactoryAndSession(){
        miFactory = Conexion.getMiFactory();
        miSession = miFactory.openSession();
    }
}
