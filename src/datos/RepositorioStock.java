/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.List;
import modelo.Stock;
import modelo.Talle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author ZURITA
 */
public class RepositorioStock {
    private static SessionFactory miFactory;
    private static Session miSession;
    
    public static void addStock(Stock stock){
        openFactoryAndSession();
        try {
            miSession.beginTransaction();
            miSession.save(stock);
            miSession.getTransaction().commit();     
            miSession.close();
        } finally {
            miFactory.close();
        }
    }
    
    public static void updateStock(int idStock, int nuevaCantidad){
        openFactoryAndSession();
        try {
            miSession.beginTransaction();
            int count = miSession.createQuery("update Stock s set s.cantidad="+nuevaCantidad+" where s.id="+idStock).executeUpdate();
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
