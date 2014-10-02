
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

public class Poder {

    private int iX;     // posicion en x.       
    private int iY;     // posicion en y.
    private Animacion aniAnimacion;	// animacion.
    private int iPoder;  // Indicador de si el bloque contiene algun poder o no.
    private boolean bBueno;  // Indicador de si el poder es bueno o malo.

    /**
     * Personaje
     * 
     * Metodo constructor usado para crear el objeto personaje
     * con una animación.
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param aniAnimacion es la <code>animacion</code> del objeto.
     * @param bBueno es el <code>indicador</code> de si el poder es bueno o malo
     * 
     */
    public Poder(int iX, int iY, Animacion aniAnimacion, boolean bBueno) {
        this.iX = iX;
        this.iY = iY;
        this.aniAnimacion = aniAnimacion;
        this.iPoder = (int) (Math.random() * 3);
        this.bBueno = bBueno;
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
     * getBueno
     * 
     * Metodo de acceso que regresa si el poder es bueno o malo.
     * 
     * @return bBueno es el <code>indicador</code> de si el poder es bueno o 
     * malo.
     * 
     */
    public boolean getBueno() {
        return bBueno;
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
     * abajo
     * 
     * Metodo que baja al poder.
     * 
     */
    public void abajo() {
        this.setY(this.getY() + 2);
    }
    
    /** 
     * colisiona
     * 
     * Metodo para revisar si un objeto <code>Poder</code> colisiona con un
     * objeto de la clase <code>Barra</code>, esto se logra con un objeto
     * temporal de la clase <code>Rectangle</code>.
     * 
     * @param barBarra es el objeto <code>Barra</code> con el que se
     * compara.
     * @return un valor true si esta colisionando y false si no.
     * 
     */
    public boolean colisiona(Barra barBarra) {
        // Creo un objeto rectangulo a partir de este objeto Barra.
        Rectangle recObjeto = new Rectangle(this.getX(),this.getY(),
                this.getAncho(), this.getAlto());
        
        // Creo un objeto rectangulo a partir del objeto Proyectil parametro.
        Rectangle recParametro = new Rectangle(barBarra.getX(),
                barBarra.getY(), barBarra.getAncho(),
                barBarra.getAlto());
        
        // Se crea una variable que indica si se intersectan o no.
        boolean bIntersectado = recObjeto.intersects(recParametro);
        
        // Se revisa colision
        if(bIntersectado) {
            // Se revisa que tipo de poder es.
            if(bBueno) {
                // Se revisa poder obtenido.
                switch(iPoder) {
                    // Si se obtuvo barra mas larga.
                    case 0: {
                        // Se crea variable animacion temporal.
                        Animacion aniAnimacion = new Animacion();
                        
                        /* Se cargan las imágenes(cuadros) para la
                         * animacion de la barra. */
                        Image imaBarra1 = Toolkit.getDefaultToolkit().
                            getImage(this.getClass().
                                getResource("Barras/Barra Poder 0 larga.png"));
                        Image imaBarra2 = Toolkit.getDefaultToolkit().
                            getImage(this.getClass().
                                getResource("Barras/Barra Poder 1.png"));
                                                
                        // Se introducen las imagenes a la animacion de barra.
                        aniAnimacion.sumaCuadro(imaBarra1, 200);
                        aniAnimacion.sumaCuadro(imaBarra2, 200);
                        
                        // Se cambia animacion de barra.
                        barBarra.setAnimacion(aniAnimacion);
                        
                        break;
                    }
                    // Si se hace mas lenta la pelota.
                    case 1: {
                        // Se crea variable animacion temporal.
                        Animacion aniAnimacion = new Animacion();
                        
                        /* Se cargan las imágenes(cuadros) para la
                         * animacion de la barra. */
                        Image imaBarra1 = Toolkit.getDefaultToolkit().
                            getImage(this.getClass().
                                getResource("Barras/Barra Poder 0 corta.png"));
                        Image imaBarra2 = Toolkit.getDefaultToolkit().
                            getImage(this.getClass().
                                getResource("Barras/Barra Poder 2.png"));
                                                
                        // Se introducen las imagenes a la animacion de barra.
                        aniAnimacion.sumaCuadro(imaBarra1, 200);
                        aniAnimacion.sumaCuadro(imaBarra2, 200);
                        
                        // Se cambia animacion de barra.
                        barBarra.setAnimacion(aniAnimacion);
                        
                        break;
                    }
                    // Si se agregan vidas.
                    case 2: {
                        // Se crea variable animacion temporal.
                        Animacion aniAnimacion = new Animacion();
                        
                        /* Se cargan las imágenes(cuadros) para la
                         * animacion de la barra. */
                        Image imaBarra1 = Toolkit.getDefaultToolkit().
                            getImage(this.getClass().
                                getResource("Barras/Barra Poder 0 corta.png"));
                        Image imaBarra2 = Toolkit.getDefaultToolkit().
                            getImage(this.getClass().
                                getResource("Barras/Barra Poder 3.png"));
                                                
                        // Se introducen las imagenes a la animacion de barra.
                        aniAnimacion.sumaCuadro(imaBarra1, 200);
                        aniAnimacion.sumaCuadro(imaBarra2, 200);
                        
                        // Se cambia animacion de barra.
                        barBarra.setAnimacion(aniAnimacion);
                        
                        break;
                    }
                }
            }
            else {
                // Se regresa barra a estado original.
                // Se crea variable animacion temporal.
                Animacion aniAnimacion = new Animacion();

                /* Se cargan las imágenes(cuadros) para la
                 * animacion de la barra. */
                Image imaBarra = Toolkit.getDefaultToolkit().
                    getImage(this.getClass().
                        getResource("Barras/Barra Normal.png"));

                // Se introducen las imagenes a la animacion de barra.
                aniAnimacion.sumaCuadro(imaBarra, 200);

                // Se cambia animacion de barra.
                barBarra.setAnimacion(aniAnimacion);
                
                // Si se obtuvo barra mas corta.
                if(iPoder == 0){
                    // Se crea variable animacion temporal.
                    aniAnimacion = new Animacion();

                    /* Se cargan las imágenes(cuadros) para la
                     * animacion de la barra. */
                    Image imaBarra1 = Toolkit.getDefaultToolkit().
                        getImage(this.getClass().
                            getResource("Barras/Barra Corta.png"));

                    // Se introducen las imagenes a la animacion de barra.
                    aniAnimacion.sumaCuadro(imaBarra1, 200);

                    // Se cambia animacion de barra.
                    barBarra.setAnimacion(aniAnimacion);
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