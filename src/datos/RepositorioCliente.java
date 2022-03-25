/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author ZURITA
 */
public class RepositorioCliente {
    private static SessionFactory miFactory;
    private static Session miSession;

    public static Cliente getClientePorDefecto(){
        Cliente cliente = null;
        openFactoryAndSession();
        try {
            miSession.beginTransaction();
            cliente = miSession.get(Cliente.class, 1);
            miSession.getTransaction().commit();
            miSession.close();
        } finally {
            miFactory.close();
            return cliente;
        }
    }
    
    public static List<Cliente> getClientesPorCuit(String cuit){
        List<Cliente> clientes = new ArrayList<>();
        openFactoryAndSession();
        try {
            miSession.beginTransaction();
            clientes = miSession.createQuery("from Cliente c where c.cuit = '"+cuit+"'").getResultList();
            miSession.getTransaction().commit();
            miSession.close();
        } finally {
            miFactory.close();
            return clientes;
        }
    }

    private static void openFactoryAndSession(){
        miFactory = Conexion.getMiFactory();
        miSession = miFactory.openSession();
    }
}
