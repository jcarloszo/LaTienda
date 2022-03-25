/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentador;

import datos.RepositorioEmpleado;
import datos.RepositorioSesion;
import modelo.Empleado;
import modelo.PuntoDeVenta;
import modelo.Sesion;
import presentador.interfaces.IVistaAutenticacion;
import vistas.VistaAutenticacion;

/**
 *
 * @author ZURITA
 */
public class PresentadorAutenticacion {
    
    private IVistaAutenticacion vista;

    public PresentadorAutenticacion(IVistaAutenticacion vista) {
        this.vista = vista;
    }

    public void autentificarEmpleado(int legajo, String password) {
        Empleado empleado = RepositorioEmpleado.autentificarEmpleado(legajo, password);
        if(empleado != null){
            Sesion sesion = new Sesion(empleado, new PuntoDeVenta(12));
            RepositorioSesion.setUltimaSesion(sesion);
            vista.iniciarSesion();
        } else {
            vista.mostrarMje("Credenciales invalidas");
        }
    }

    
    
}
