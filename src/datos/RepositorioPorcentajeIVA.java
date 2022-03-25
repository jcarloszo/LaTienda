/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.ArrayList;
import java.util.List;
import modelo.PorcentajeIVA;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author ZURITA
 */
public class RepositorioPorcentajeIVA {
    private static SessionFactory miFactory;
    private static Session miSession;

    public static List<PorcentajeIVA> getPorcentajesIVA(){
        openFactoryAndSession();
        List<PorcentajeIVA> porcentajesIva = new ArrayList<>();
        try {
            miSession.beginTransaction();
            porcentajesIva = miSession.createQuery("from PorcentajeIVA").getResultList();
            miSession.getTransaction().commit();
            miSession.close();
        } finally {
            miFactory.close();
            return porcentajesIva;
        }
    }

    private static void openFactoryAndSession(){
        miFactory = Conexion.getMiFactory();
        miSession = miFactory.openSession();
    }
}
