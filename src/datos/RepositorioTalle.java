/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.List;
import modelo.Marca;
import modelo.PorcentajeIVA;
import modelo.Producto;
import modelo.Rubro;
import modelo.Talle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author ZURITA
 */
public class RepositorioTalle {
    private static SessionFactory miFactory;
    private static Session miSession;

    public static List<Talle> getTalles(){
        openFactoryAndSession();
        List<Talle> talles = null;
        try {
            miSession.beginTransaction();
            talles = miSession.createQuery("from Talle").getResultList();
            miSession.getTransaction().commit();
            miSession.close();
        } finally {
            miFactory.close();
            return talles;
        }
    }

    private static void openFactoryAndSession(){
        miFactory = Conexion.getMiFactory();
        miSession = miFactory.openSession();
    }
}
