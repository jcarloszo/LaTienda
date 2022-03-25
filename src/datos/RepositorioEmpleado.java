/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.ArrayList;
import java.util.List;
import modelo.Empleado;

/**
 *
 * @author ZURITA
 */
public class RepositorioEmpleado {
    private static List<Empleado> empleados = new ArrayList<>();
    
    public static void init(){
        empleados.add(new Empleado(46296, "1234", "Carlos Zurita"));
    }
    
    public static Empleado autentificarEmpleado(int legajo, String password){
        Empleado empleado = null;
        for(Empleado empl : empleados){
            if(empl.getLegajo() == legajo && empl.getPassword().equals(password)){
                empleado = empl;
                break;
            }
        }
        return empleado;
    }
}
