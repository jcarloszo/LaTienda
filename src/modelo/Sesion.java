/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author ZURITA
 */
public class Sesion {
    private Empleado empleado;
    private PuntoDeVenta puntoDeVenta;

    public Sesion(Empleado empleado, PuntoDeVenta puntoDeVenta) {
        this.empleado = empleado;
        this.puntoDeVenta = puntoDeVenta;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public PuntoDeVenta getPuntoDeVenta() {
        return puntoDeVenta;
    }

    public void setPuntoDeVenta(PuntoDeVenta puntoDeVenta) {
        this.puntoDeVenta = puntoDeVenta;
    }
    
    
}
