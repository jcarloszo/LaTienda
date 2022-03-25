/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.ArrayList;
import java.util.List;
import modelo.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author ZURITA
 */
public class RepositorioMarca {
    
    private static SessionFactory miFactory;
    private static Session miSession;

    public static void addMarca(Marca marca){
        openFactoryAndSession();
        try {
            miSession.beginTransaction();
            miSession.save(marca);
            miSession.getTransaction().commit();
            miSession.close();
        } finally {
            miFactory.close();
        }
    }
    
    public static List<Marca> getMarcas(){
        openFactoryAndSession();
        List<Marca> marcas = null;
        try {
            miSession.beginTransaction();
            marcas = miSession.createQuery("from Marca").getResultList();
            miSession.getTransaction().commit();
            miSession.close();
        } finally {
            miFactory.close();
            return marcas;
        }
    }
    
    public static void updateMarca(int idMarca, Marca marca){
        openFactoryAndSession();
        try {
            miSession.beginTransaction();
            Marca m = miSession.get(Marca.class, idMarca);
            m.setDescripcion(marca.getDescripcion());
            miSession.getTransaction().commit();
            miSession.close();
        } finally {
            miFactory.close();
        }
    }
    
    public static void deleteMarca(int idMarca){
        openFactoryAndSession();
        try {
            miSession.beginTransaction();
            Marca m = miSession.get(Marca.class, idMarca);
            miSession.delete(m);
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
