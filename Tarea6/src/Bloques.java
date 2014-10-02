
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

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Bloques {

    private int iX;     // posicion en x.       
    private int iY;     // posicion en y.
    private Animacion aniAnimacion;	// animacion.
    private int iDano;  // Daño que lleva hasta el momento el bloque.
    private int iPoder;  // Indicador de si el bloque contiene algun poder o no.
    private int iColor;  // Indicador del color del bloque.
    
    // Arreglo que contiene los colores que puede tener un bloque.
    private final String strColores[] = {"Amarillo", "Azul", "Morado",
        "Naranja", "Rojo", "Rosa", "Verde"};

    /**
     * Personaje
     * 
     * Metodo constructor usado para crear el objeto personaje
     * con una animación.
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param aniAnimacion es la <code>animacion</code> del objeto.
     * @param iDano es el <code>daño</code> inicial del objeto.
     * @param iPoder es el <code>indicador</code> de si el objeto contiene un
     * poder o no.
     * @param iColor es el <code>color</code> del bloque.
     * 
     */
    public Bloques(int iX, int iY, Animacion aniAnimacion, int iDano,
            int iPoder, int iColor) {
        this.iX = iX;
        this.iY = iY;
        this.aniAnimacion = aniAnimacion;
        this.iDano = iDano;
        this.iPoder = iPoder;
        this.iColor = iColor;
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
     * Metodo modificador usado para cambiar la posicion en y del objeto.
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
     * Metodo de acceso que regresa la posicion en y del objeto.
     * 
     * @return posY es la <code>posicion en y</code> del objeto.
     * 
     */
    public int getY() {
        return iY;
    }
    
    /**
     * setDano
     * 
     * Metodo modificador usado para cambiar el dano del objeto.
     * 
     * @param iDano es el <code>daño</code> del objeto.
     * 
     */
    public void setDano(int iDano) {
        this.iDano = iDano;
    }
    
    /**
     * getDano
     * 
     * Metodo de acceso que regresa el dano del objeto.
     * 
     * @return iDano es el <code>daño</code> del objeto.
     * 
     */
    public int getDano() {
        return iDano;
    }
    
    /**
     * setPoder
     * 
     * Metodo modificador usado para cambiar el poder del objeto.
     * 
     * @param iPoder es el <code>poder</code> del objeto.
     * 
     */
    public void setPoder(int iPoder) {
        this.iPoder = iPoder;
    }
    
    /**
     * getPoder
     * 
     * Metodo de acceso que regresa el poder del objeto.
     * 
     * @return iPoder es el <code>poder</code> del objeto.
     * 
     */
    public int getPoder() {
        return iPoder;
    }
    
    /**
     * setColor
     * 
     * Metodo modificador usado para cambiar el color del objeto.
     * 
     * @param iColor es el <code>color</code> del objeto.
     * 
     */
    public void setColor(int iColor) {
        this.iColor = iColor;
    }
    
    /**
     * getColor
     * 
     * Metodo de acceso que regresa el color del objeto.
     * 
     * @return iColor es el <code>color</code> del objeto.
     * 
     */
    public int getColor() {
        return iColor;
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
     * Metodo para revisar si un objeto <code>Bloques</code> colisiona con un
     * objeto de la clase <code>Proyectil</code>, esto se logra con un objeto
     * temporal de la clase <code>Rectangle</code>.
     * 
     * @param proObjeto es el objeto <code>Proyectil</code> con el que se
     * compara.
     * @return un valor true si esta colisionando y false si no.
     * 
     */
    public boolean colisiona(Proyectil proObjeto) {
        // Creo un objeto rectangulo a partir de este objeto Barra.
        Rectangle recObjeto = new Rectangle(this.getX(),this.getY(),
                this.getAncho(), this.getAlto());
        
        // Creo un objeto rectangulo a partir del objeto Proyectil parametro.
        Rectangle recParametro = new Rectangle(proObjeto.getX(),
                proObjeto.getY(), proObjeto.getAncho(),
                proObjeto.getAlto());
        
        // Se crea una variable que indica si se intersectan o no.
        boolean bIntersectado = recObjeto.intersects(recParametro);
        
        // Se revisa si el bloque contiene algun poder.
        if(iPoder > 0 && bIntersectado) {
            iDano = 3;
        }
        // Si no lo tiene se actualiza el daño en el bloque.
        else if(recObjeto.intersects(recParametro)) {
            iDano ++;  // Se aumenta en 1 el dano.
            // Se revisa el daño.
            switch(iDano) {
                // Si tiene uno de daño.
                case 1: {
                    /* Se cargan las imágenes(cuadros) para la
                     * animacion del bloque. */
                    Image imaBloque = Toolkit.getDefaultToolkit().
                        getImage(this.getClass().
                            getResource("Bloques/" + strColores[iColor] 
                                    + " dañado 1.png"));

                    // Se reinicializa la animación del bloque.
                    aniAnimacion = new Animacion();
                    
                    // Se introducen las imagenes a la animacion del bloque.
                    aniAnimacion.sumaCuadro(imaBloque, 200);
                    
                    break;
                }
                // Si tiene dos de daño.
                case 2: {
                    /* Se cargan las imágenes(cuadros) para la
                     * animacion del bloque. */
                    Image imaBloque = Toolkit.getDefaultToolkit().
                        getImage(this.getClass().
                            getResource("Bloques/" + strColores[iColor] 
                                    + " dañado 2.png"));

                    // Se reinicializa la animación del bloque.
                    aniAnimacion = new Animacion();
                    
                    // Se introducen las imagenes a la animacion del bloque.
                    aniAnimacion.sumaCuadro(imaBloque, 200);
                    
                    break;
                }
                // Si ya se destruyó.
                case 3: {
                     // Se mueve fuera de pantalla.
                    break;
                }
            }
        }
        
        // Si se colisionan regreso verdadero, si no regreso falso.
        return bIntersectado;
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