/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import modelo.Sesion;

/**
 *
 * @author ZURITA
 */
public class RepositorioSesion {
    private static Sesion ultimaSesion;

    public static Sesion getUltimaSesion() {
        return ultimaSesion;
    }

    public static void setUltimaSesion(Sesion ultimaSesion) {
        RepositorioSesion.ultimaSesion = ultimaSesion;
    }
    
    
}
