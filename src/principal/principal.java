/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import servicioAFIP.ServicioAFIP;
import datos.Conexion;
import datos.RepositorioEmpleado;
import vistas.VistaAutenticacion;
import servicioautorizacion.ServicioAutorizacion;

/**
 *
 * @author ZURITA
 */
public class principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
                
        if(Conexion.conexionDB()){
            RepositorioEmpleado.init();
            VistaAutenticacion vista = new VistaAutenticacion();
            vista.setVisible(true);
        } else {
            System.out.println("Error al conectar con la DB");
        }
        
    }
    
}
