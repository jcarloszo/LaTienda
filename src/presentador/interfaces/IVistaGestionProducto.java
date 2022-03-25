/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentador.interfaces;

import java.util.List;
import modelo.Marca;
import modelo.Producto;
import modelo.Rubro;

/**
 *
 * @author ZURITA
 */
public interface IVistaGestionProducto {

    public void cargarListas(List<Marca> marcas, List<Rubro> rubros);

    public void listarProductosFiltrados(List<Producto> productosFiltrados);

    public void mostrarMensaje(String no_se_encontraron_productos);
    
}
