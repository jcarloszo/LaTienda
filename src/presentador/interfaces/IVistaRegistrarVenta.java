/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentador.interfaces;

import java.text.SimpleDateFormat;
import java.util.List;
import modelo.Cliente;
import modelo.Color;
import modelo.LineaDeVenta;
import modelo.Marca;
import modelo.Producto;
import modelo.Rubro;
import modelo.Talle;

/**
 *
 * @author ZURITA
 */
public interface IVistaRegistrarVenta {

    public void cargarListas(List<Marca> marcas, List<Rubro> rubros);

    public void listarProductosFiltrados(List<Producto> productosFiltrados);
    
    public void mostrarMensaje(String mje);

    public void cargarTallesDisponibles(List<Talle> tallesDisponibles);

    public void cargarColoresDisponibles(List<Color> coloresDisponibles);

    public void reserTalleYColor();

    public void mostrarLineasDeVenta(List<LineaDeVenta> lineasDeVenta);

    public void mostrarTotalVenta(double total);

    public void cargarCliente(Cliente cliente);

    public void limpiarFormCliente();

    public void limpiar();

    public void setDatos(String date, String empleado, int ptoDeVenta);

    
}
