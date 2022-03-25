/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.List;
import modelo.Color;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author ZURITA
 */
public class RepositorioColor {
    private static SessionFactory miFactory;
    private static Session miSession;

    public static List<Color> getColores(){
        openFactoryAndSession();
        List<Color> colores = null;
        try {
            miSession.beginTransaction();
            colores = miSession.createQuery("from Color").getResultList();
            miSession.getTransaction().commit();
            miSession.close();
        } finally {
            miFactory.close();
            return colores;
        }
    }

    private static void openFactoryAndSession(){
        miFactory = Conexion.getMiFactory();
        miSession = miFactory.openSession();
    }
}
