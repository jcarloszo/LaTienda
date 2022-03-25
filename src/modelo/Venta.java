/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author ZURITA
 */
@Entity
@Table(name="ventas")
public class Venta {

    public Venta() {
        SimpleDateFormat dtf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();

        Date dateObj = calendar.getTime();
        String formattedDate = dtf.format(dateObj);
        fecha = formattedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<LineaDeVenta> getLineasDeVenta() {
        return lineasDeVenta;
    }

    public void setLineasDeVenta(List<LineaDeVenta> lineasDeVenta) {
        this.lineasDeVenta = lineasDeVenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public double getTotal(){
        double total = 0;
        for(LineaDeVenta ldv : lineasDeVenta){
            total += ldv.getSubTotal();
        }
        return total;
    }
    
    public double getTotalNeto(){
        double total = 0;
        for(LineaDeVenta ldv : lineasDeVenta){
            total += ldv.getNeto();
        }
        return total;
    }
    
    public double getTotalIva(){
        double total = 0;
        for(LineaDeVenta ldv : lineasDeVenta){
            total += ldv.getIva();
        }
        return total;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Column(name="fecha")
    String fecha;
    
    @OneToMany(mappedBy="venta", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.EAGER)
    private List<LineaDeVenta> lineasDeVenta;
    
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="idCliente")
    private Cliente cliente;
}
