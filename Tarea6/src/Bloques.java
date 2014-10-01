
/**
 * Bloques
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Bloques</code>
 *
 * @author Humberto Makoto Morimoto Burgos     A01280458
 * @author Eduardo Andrade Martínez     A01035059
 * @version 1.00 2014/10/01
 * 
 */

import java.awt.Rectangle;

public class Bloques {

    private int iX;     //posicion en x.       
    private int iY;     //posicion en y.
    private Animacion aniAnimacion;	// animacion.

    /**
     * Personaje
     * 
     * Metodo constructor usado para crear el objeto personaje
     * con una animación.
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param aniAnimacion es la <code>animacion</code> del objeto.
     * 
     */
    public Bloques(int iX, int iY, Animacion aniAnimacion) {
        this.iX = iX;
        this.iY = iY;
        this.aniAnimacion = aniAnimacion;
    }
    
    /**
     * setX
     * 
     * Metodo modificador usado para cambiar la posicion en x del objeto
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * 
     */
    public void setX(int iX) {
        this.iX = iX;
    }

    /**
     * getX
     * 
     * Metodo de acceso que regresa la posicion en x del objeto 
     * 
     * @return iX es la <code>posicion en x</code> del objeto.
     * 
     */
    public int getX() {
        return iX;
    }

    /**
     * setY
     * 
     * Metodo modificador usado para cambiar la posicion en y del objeto 
     * 
     * @param iY es la <code>posicion en y</code> del objeto.
     * 
     */
    public void setY(int iY) {
            this.iY = iY;
    }

    /**
     * getY
     * 
     * Metodo de acceso que regresa la posicion en y del objeto 
     * 
     * @return posY es la <code>posicion en y</code> del objeto.
     * 
     */
    public int getY() {
        return iY;
    }

    /**
     * setAnimacion
     * 
     * Metodo modificador usado para cambiar la animacion del objeto.
     * 
     * @param aniAnimacion es la <code>animacion</code> del objeto.
     * 
     */
    public void setAnimacion(Animacion aniAnimacion) {
        this.aniAnimacion = aniAnimacion;
    }

    /**
     * getAnimacion
     * 
     * Metodo de acceso que regresa la animacion del objeto.
     * 
     * @return aniAnimacion es la <code>animacion</code> del objeto.
     * 
     */
    public Animacion getAnimacion() {
        return aniAnimacion;
    }

    /**
     * getAncho
     * 
     * Metodo de acceso que regresa el ancho del icono 
     * 
     * @return un <code>entero</code> que es el ancho del icono.
     * 
     */
    public int getAncho() {
        return aniAnimacion.getImagen().getWidth(null);
    }

    /**
     * getAlto
     * 
     * Metodo que  da el alto del icono 
     * 
     * @return un <code>entero</code> que es el alto del icono.
     * 
     */
    public int getAlto() {
        return aniAnimacion.getImagen().getHeight(null);
    }
    
    /** 
     * colisiona
     * 
     * Metodo para revisar si un objeto <code>Personaje</code> colisiona con una
     * coordenada que tiene valor de x y valor de y
     * 
     * @param iX es el valor <code>entero</code> de x
     * @param iY es el valor <code>entero</code> de x
     * @return  un valor true si esta colisionando y false si no
     * 
     */
    public boolean colisiona(int iX, int iY) {
        // creo un objeto rectangulo a partir de este objeto Personaje
        Rectangle recObjeto = new Rectangle(this.getX(),this.getY(),
                this.getAncho(), this.getAlto());
               
        // si se colisionan regreso verdadero, sino regreso falso
        return recObjeto.contains(iX, iY);
    }    
}