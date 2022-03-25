/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.List;
import modelo.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author ZURITA
 */
public class RepositorioRubro {
    private static SessionFactory miFactory;
    private static Session miSession;

    public static void addRubro(Rubro rubro){
        openFactoryAndSession();
        try {
            miSession.beginTransaction();
            miSession.save(rubro);
            miSession.getTransaction().commit();
            miSession.close();
        } finally {
            miFactory.close();
        }
    }
    
    public static List<Rubro> getRubros(){
        openFactoryAndSession();
        List<Rubro> rubros = null;
        try {
            miSession.beginTransaction();
            rubros = miSession.createQuery("from Rubro").getResultList();
            miSession.getTransaction().commit();
            miSession.close();
        } finally {
            miFactory.close();
            return rubros;
        }
    }
    
    public static void updateRubro(int idRubro, Rubro rubro){
        openFactoryAndSession();
        try {
            miSession.beginTransaction();
            Rubro r = miSession.get(Rubro.class, idRubro);
            r.setDescripcion(rubro.getDescripcion());
            miSession.getTransaction().commit();
            miSession.close();
        } finally {
            miFactory.close();
        }
    }
    
    public static void deleteRubro(int idRubro){
        openFactoryAndSession();
        try {
            miSession.beginTransaction();
            Rubro r = miSession.get(Rubro.class, idRubro);
            miSession.delete(r);
            miSession.getTransaction().commit();
            miSession.close();
        } finally {
            miFactory.close();
        }
    }
    
    private static void openFactoryAndSession(){
        miFactory = miFactory = Conexion.getMiFactory();
        miSession = miFactory.openSession();
    }
}
