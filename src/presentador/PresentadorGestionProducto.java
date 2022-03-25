/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentador;

import datos.RepositorioMarca;
import datos.RepositorioProducto;
import datos.RepositorioRubro;
import java.util.ArrayList;
import java.util.List;
import modelo.Marca;
import modelo.Producto;
import modelo.Rubro;
import presentador.interfaces.IVistaGestionProducto;

/**
 *
 * @author ZURITA
 */
public class PresentadorGestionProducto {
    private IVistaGestionProducto vista;
    private List<Marca> marcas;
    private List<Rubro> rubros;
    private List<Producto> productosFiltrados;

    public PresentadorGestionProducto(IVistaGestionProducto vista) {
        this.vista = vista;
    }
    
    public void getMarcasYRubros() {
        marcas = RepositorioMarca.getMarcas();
        rubros = RepositorioRubro.getRubros();
        vista.cargarListas(marcas, rubros);
    }
    
    public void getProductos(String codOrDesc, String rubro, String marca) {
        List<Producto> productos = RepositorioProducto.getProductos();///DEBERIA SER RESPONSABILIDAD DEL REPOSITORIO
        productosFiltrados = new ArrayList<>();
        
        for(Producto p : productos){
            if(
               (p.getCodigobarra().equals(codOrDesc) || p.getDescripcion().toLowerCase().contains(codOrDesc.toLowerCase()))
               && p.getMarca().getDescripcion().contains(marca)
               && p.getRubro().getDescripcion().contains(rubro)
               )
            {
                productosFiltrados.add(p);
            }
        }
            
        vista.listarProductosFiltrados(productosFiltrados);
        if(productosFiltrados.size()==0) vista.mostrarMensaje("No se encontraron productos");
    }
}
