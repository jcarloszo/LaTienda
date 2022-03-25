/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Agustin
 */
public class FondoPanel extends JPanel{
    private Image imagen; 
    private String ruta; 
    @Override
    public void paint(Graphics g)
    {
        imagen = new ImageIcon(getClass().getResource("/vistas/imagenes/"+ruta)).getImage();
            
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            
        setOpaque(false);
            
        super.paint(g);
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    
}
