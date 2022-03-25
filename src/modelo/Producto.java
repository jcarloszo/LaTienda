/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author ZURITA
 */
@Entity
@Table(name="productos")
public class Producto {

    public Producto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigobarra() {
        return codigobarra;
    }

    public void setCodigobarra(String codigobarra) {
        this.codigobarra = codigobarra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getMargenganancia() {
        return margenganancia;
    }

    public void setMargenganancia(double margenganancia) {
        this.margenganancia = margenganancia;
    }

    public PorcentajeIVA getPorcentajeIva() {
        return porcentajeIva;
    }

    public void setPorcentajeIva(PorcentajeIVA porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public List<Stock> getStocks() {
        if(stocks==null) stocks = new ArrayList<>();
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
    
    public void addStock(Stock s) {
        getStocks().add(s);
        s.setProducto(this);
    }
    
    public double getNetoAgravado(){
        return costo + costo*margenganancia;
    }
    
    public double calcularIVA(){
        return this.getNetoAgravado() * porcentajeIva.getValor();
    }
    
    public double getPrecioVenta(){
        return this.getNetoAgravado() + this.calcularIVA();
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Column(name="codigobarra")
    private String codigobarra;
    
    @Column(name="descripcion")
    private String descripcion;
    
    @Column(name="costo")
    private double costo;
    
    @Column(name="margenganancia")
    private double margenganancia;
    
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="idPorcentajeiva")
    private PorcentajeIVA porcentajeIva;
    
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="idRubro")
    private Rubro rubro;
    
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="idMarca")
    private Marca marca;
    
    @OneToMany(mappedBy="producto", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Stock> stocks;

    
}
