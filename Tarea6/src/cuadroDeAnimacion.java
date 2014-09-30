/**
 * cuadroDeAnimacion
 * 
 * Clase cuadroDeAnimacion que permite crear los cuadros que 
 * conformarán una animación.
 * 
 * @author Humberto Makoto Morimoto Burgos     A01280458
 * @author Eduardo Andrade Martínez     A01035059
 * @version 1.00 2014/10/01
 * 
 */

import java.awt.Image;

public class cuadroDeAnimacion {
    // Imagen que se mostrará en pantalla cuando el cuadro este activo
    Image imagen;
    // Tiempo en el que se realiza la transición del cuadro actual al siguiente
    long tiempoFinal;

    /**
     * cuadroDeAnimacion
     * 
     * Constructor vacío de objetos cuadroDeAnimacion.
     * 
     */
    public cuadroDeAnimacion(){
        //Guarda valor nulo en la imagen
        this.imagen = null;
        //Guarda en 0 el tiempo
        this.tiempoFinal = 0;
    }

    /**
     * cuadroDeAnimacion
     * 
     * Constructor con parámetros de objetos cuadroDeAnimacion 
     * que recibe la imagen y el tiempo del cuadro.
     * 
     * @param tiempoFinal es el tiempo del cuadro
     * @param imagen es la imagen que se recibe
     */
    public cuadroDeAnimacion(Image imagen, long tiempoFinal){
        //Guarda la imagen del cuadro
        this.imagen = imagen;
        //Guarda el tiempo en que se da la transición al 
        //siguiente cuadro
        this.tiempoFinal = tiempoFinal;
    }
    
    /**
     * getImagen
     * 
     * Obtiene la imagen de un cuadro.
     * 
     * @return regresa la imagen del cuadro.
     * 
     */
    public Image getImagen(){
        //Retorna la imagen
        return imagen;
    }

    /**
     * getTiempoFinal
     * 
     * Obtiene el tiempo en el que se realiza la transición al siguiente.
     * 
     * @return regresa el tiempo en que se realiza la transición al siguiente.
     * 
     */
    public long getTiempoFinal(){
        //Retorna el tiempo
        return tiempoFinal;
    }

    /**
     * setImagen
     * 
     * Cambia la imagen del cuadro por una que recibe como parámetro.
     * 
     * @param imagen es la imagen que se recibe.
     * 
     */
    public void setImagen (Image imagen){
        //Guarda la nueva imagen
        this.imagen = imagen;
    }

    /**
     * setTiempoFinal
     * 
     * Cambia el tiempo en el que cambia al siguiente cuadro.
     * 
     * @param tiempoFinal es el tiempo en el que cambia al siguiente cuadro.
     * 
     */
    public void setTiempoFinal(long tiempoFinal){
        //Guarda el nuevo tiempo
        this.tiempoFinal = tiempoFinal;
    }
}
