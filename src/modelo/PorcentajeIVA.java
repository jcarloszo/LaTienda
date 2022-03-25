/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author ZURITA
 */

@Entity
@Table(name="porcentajeiva")
public class PorcentajeIVA {

    public PorcentajeIVA() {
    }

    public PorcentajeIVA(int idAfip, double valor) {
        this.idAfip = idAfip;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAfip() {
        return idAfip;
    }

    public void setIdAfip(int idAfip) {
        this.idAfip = idAfip;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public List<Producto> getProductos() {
        if(productos == null) productos = new ArrayList<>();
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Column(name="idAfip")
    private int idAfip;
    
    @Column(name="valor")
    private double valor;
    
    @OneToMany(mappedBy="porcentajeIva", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.EAGER)
    private List<Producto> productos;
}
