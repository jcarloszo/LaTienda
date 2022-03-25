/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentador;

import datos.*;
import java.util.ArrayList;
import java.util.List;
import modelo.*;
import presentador.interfaces.IVistaAltaProducto;

/**
 *
 * @author ZURITA
 */
public class PresentadorAltaProducto {
    private IVistaAltaProducto vista;
    private List<Marca> marcas;
    private List<Color> colores;
    private List<Rubro> rubros;
    private List<Talle> talles;
    private List<Stock> stocks;
    private List<PorcentajeIVA> porcentajesIVA;
    private Producto producto;

    public PresentadorAltaProducto(IVistaAltaProducto vista) {
        this.vista = vista;
        stocks = new ArrayList<>();
        producto = new Producto();
    }

    public void getMarcaColorYRubro() {
        marcas = RepositorioMarca.getMarcas();
        colores = RepositorioColor.getColores();
        rubros = RepositorioRubro.getRubros();
        porcentajesIVA = RepositorioPorcentajeIVA.getPorcentajesIVA();
        vista.cargarListas(marcas, colores, rubros, porcentajesIVA);
    }

    public void getTallesDelRubro(String rubro) {
        Rubro rub = getRubro(rubro);
        talles = rub.getTalles();
        vista.listarTalles(talles);
    }

    public void agregarStock(String color, String talle) {
        Color c = getColor(color);
        Talle t = getTalle(talle);
        Stock s = new Stock();
        s.setColor(c);
        s.setTalle(t);
        stocks.add(s);
    }

    public void registrarProducto(String codigobarra, String descripcion, double costo, double margenganancia, String rubro, String marca, double valor) {
        boolean validacion = codigobarra.equals("") || descripcion.equals("") || costo <= 0 || margenganancia <=0 || stocks.size() < 1;
                
        if (!validacion) {
            producto.setCodigobarra(codigobarra);
            producto.setDescripcion(descripcion);
            producto.setCosto(costo);
            producto.setMargenganancia(margenganancia);
            producto.setRubro(getRubro(rubro));
            producto.setMarca(getMarca(marca));
            producto.setPorcentajeIva(getPorcentajeIVA(valor));
            
            RepositorioProducto.addProducto(producto);
            
            for (Stock stock : stocks) {
                stock.setProducto(producto);
                RepositorioStock.addStock(stock);
            }
            
            vista.mostrarMje("El producto se ha cargado correctamente");
            vista.limpiar();
        } else {
            vista.mostrarMje("Error en alguno de los campos");
        }
    }
    
    private Marca getMarca(String marcaDesc){
        Marca marca = null;
        for(Marca m : marcas){
            if(marcaDesc.equals(m.getDescripcion())){
                marca = m;
                break;
            }
        }
        return marca;
    }
    private Color getColor(String colorDesc) {
        Color color = null;
        for(Color c : colores){
            if(colorDesc.equals(c.getDescripcion())){
                color = c;
                break;
            }
        }
        return color;
    }
    private Talle getTalle(String talleDesc) {
        Talle talle = null;
        for(Talle t : talles){
            if(t.getDescripcion().equals(talleDesc)){
                talle = t;
                break;
            }
        }
        return talle;
    }
    private Rubro getRubro(String rubroDesc) {
        Rubro rubro = null;
        for(Rubro r : rubros){
            if(r.getDescripcion().equals(rubroDesc)){
                rubro = r;
                break;
            }
        }
        return rubro;
    }
    private PorcentajeIVA getPorcentajeIVA(double valor) {
        PorcentajeIVA porc = null;
        for(PorcentajeIVA p : porcentajesIVA){
            if(p.getValor() == valor){
                porc = p;
                break;
            }
        }
        return porc;
    }
}
