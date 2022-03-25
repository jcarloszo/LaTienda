/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentador;

//import afip.ServicioAfip;
import servicioAFIP.ServicioAFIP;
import datos.RepositorioCliente;
import datos.RepositorioMarca;
import datos.RepositorioProducto;
import datos.RepositorioRubro;
import datos.RepositorioSesion;
import datos.RepositorioStock;
import datos.RepositorioVenta;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.*;
import presentador.interfaces.IVistaRegistrarVenta;
import servicioafip.SolicitudAFIP;

/**
 *
 * @author ZURITA
 */
public class PresentadorRegistrarVenta {
    private IVistaRegistrarVenta vista;
    private List<Marca> marcas;
    private List<Rubro> rubros;
    private List<Producto> productosFiltrados;
    private List<Stock> stockDisponible;
    private List<LineaDeVenta> lineasDeVenta = new ArrayList<>();
    private Venta ventaActual = new Venta();
    private Cliente cliente = null;
    private boolean superaMonto = false;
    private Empleado empleado;
    private PuntoDeVenta puntoDeVenta;

    public PresentadorRegistrarVenta(IVistaRegistrarVenta vista) {
        this.vista = vista;
        empleado = RepositorioSesion.getUltimaSesion().getEmpleado();
        puntoDeVenta = RepositorioSesion.getUltimaSesion().getPuntoDeVenta();
    }
    
    public void getDatos(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = dtf.format(LocalDateTime.now());
        vista.setDatos(date, empleado.getNombre(), puntoDeVenta.getNumero());
    }

    public void getMarcasYRubros() {
        marcas = RepositorioMarca.getMarcas();
        rubros = RepositorioRubro.getRubros();
        vista.cargarListas(marcas, rubros);
    }

    public void getProductos(String codOrDesc, String rubro, String marca) {
        List<Producto> productos = RepositorioProducto.getProductos();
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

    public void getStockDisponible(String codigobarra) {
        Producto p = getProducto(codigobarra);
        stockDisponible = new ArrayList<>();
        for(Stock s : p.getStocks()){
            if(s.getCantidad() > 0) stockDisponible.add(s);
        }
        
        if(stockDisponible.size() > 0){
            List<Talle> tallesDisponibles = new ArrayList<>();
            for(Stock s : stockDisponible){
                tallesDisponibles.add(s.getTalle());
                vista.cargarTallesDisponibles(tallesDisponibles);
            }
            
        } else {
            vista.reserTalleYColor();
            vista.mostrarMensaje("No hay Stock para el producto seleccionado");
        }
    }

    public void getColoresDisponibles(String talleDesc) {
        List<Color> coloresDisponibles = new ArrayList<>();
        for(Stock s : stockDisponible){
            if(s.getTalle().getDescripcion().equals(talleDesc)){
                coloresDisponibles.add(s.getColor());
            }
        }
        vista.cargarColoresDisponibles(coloresDisponibles);
    }
    
    private Producto getProducto(String codigobarra) {
        Producto p = null;
        for(Producto prod : productosFiltrados){
            if(prod.getCodigobarra().equals(codigobarra)){
                p = prod;
                break;
            }
        }
        return p;
    }

    public void agregarLineaDeVenta(String codigobarra, String talle, String color, int cantidad) {
        Stock stock = null;
        for(Stock s : stockDisponible){
            if(
               s.getProducto().getCodigobarra().equals(codigobarra)
               && s.getTalle().getDescripcion().equals(talle)
               && s.getColor().getDescripcion().equals(color)
               && cantidad <= s.getCantidad()
              ) 
            {
                stock = s;
                break;
            }
        }
        
        LineaDeVenta ldv = new LineaDeVenta();
        stock.decrementarStock(cantidad);
        ldv.setStock(stock);
        ldv.setCantidad(cantidad);
        ldv.setPrecioUnitario(stock.getProducto().getPrecioVenta());
        lineasDeVenta.add(ldv);
        ventaActual.setLineasDeVenta(lineasDeVenta);
            
        vista.mostrarLineasDeVenta(lineasDeVenta);
        vista.mostrarTotalVenta(ventaActual.getTotal());
        ventaSuperaElMonto(ventaActual.getTotal());
    }

    public void eliminarLineaDeVenta(String codigobarra, String talle, String color, int cantidad){
        for(int i = 0; i < lineasDeVenta.size(); i++){
            Stock s = lineasDeVenta.get(i).getStock();
            s.incrementarStock(cantidad);
            if(s.getProducto().getCodigobarra().equals(codigobarra) && s.getTalle().getDescripcion().equals(talle) && s.getColor().getDescripcion().equals(color)){
                lineasDeVenta.remove(i);
                break;
            }
        }
        
        vista.mostrarLineasDeVenta(lineasDeVenta);
        vista.mostrarTotalVenta(ventaActual.getTotal());     
        ventaSuperaElMonto(ventaActual.getTotal());
    }
    
    private void ventaSuperaElMonto(double totalVenta) {
        if(totalVenta > 10000){
            if(!superaMonto) vista.mostrarMensaje("La venta a superado los $10000. El comprobante debe ser nominal");
            superaMonto = true;
            cliente = null;
            vista.limpiarFormCliente();
        } else {
            superaMonto = false;
            cliente = RepositorioCliente.getClientePorDefecto();
            vista.cargarCliente(cliente);
        }
    }

    public void buscarCliente(String cuit) {
       List<Cliente> clientes = RepositorioCliente.getClientesPorCuit(cuit);
       
       if(clientes.size()>0){
           cliente = clientes.get(0);
           vista.cargarCliente(cliente);
       } else {
           cliente = null;
           vista.limpiarFormCliente();
           vista.mostrarMensaje("No se encontró al cliente");
       }
    }
    
    public void confirmarVenta() {
        if(cliente != null && lineasDeVenta.size() > 0){
            if(!cliente.getRazonsocial().equals("Anonimo") || ventaActual.getTotal() < 10000){
                ventaActual.setCliente(cliente);
            
                Comprobante comprobante = new Comprobante();
                comprobante.setVenta(ventaActual);
                comprobante.setTipoComprobante(determinarTipoComprobante(cliente));
                comprobante.setPuntoDeVenta(puntoDeVenta);

                boolean autorizado = ServicioAFIP.autorizarComprobante(comprobante);

                if(autorizado){
                    RepositorioVenta.addVenta(ventaActual);
                    for(LineaDeVenta ldv : ventaActual.getLineasDeVenta()){
                        ldv.setVenta(ventaActual);
                        RepositorioVenta.addLdv(ldv);

                        Stock s = ldv.getStock();
                        RepositorioStock.updateStock(s.getId(), s.getCantidad());
                    }
                    vista.mostrarMensaje("AFIP ha autorizado el comprobante");
                    vista.mostrarMensaje("La venta se ha registrado con éxito");
                    vista.limpiar();
                } else {
                    vista.mostrarMensaje("AFIP no autorizó la venta");
                }    
            } else {
                vista.mostrarMensaje("Para ventas mayor a 10000 el comprobante debe ser nominal");
            }
        } else {
            vista.mostrarMensaje("Error en uno de los campos");
        }
    }
    
    private EnumTipoComprobante determinarTipoComprobante(Cliente cliente){
        if(
          cliente.getCondicionTributaria().getDescripcion().equals(EnumCondicionTributaria.ResponsableInscripto.toString())
          || cliente.getCondicionTributaria().getDescripcion().equals(EnumCondicionTributaria.Monotributo.toString())
          )
            return EnumTipoComprobante.FacturaA;
        {
            return EnumTipoComprobante.FacturaB;
        }
        
    }
}
