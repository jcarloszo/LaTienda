/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentador.interfaces;

import java.util.List;
import modelo.Color;
import modelo.Marca;
import modelo.PorcentajeIVA;
import modelo.Rubro;
import modelo.Talle;

/**
 *
 * @author ZURITA
 */
public interface IVistaAltaProducto {

    public void cargarListas(List<Marca> marcas, List<Color> colores, List<Rubro> rubros, List<PorcentajeIVA> porcentajesIVA);

    public void listarTalles(List<Talle> talles);

    public void mostrarMje(String mje);

    public void limpiar();
    
}
