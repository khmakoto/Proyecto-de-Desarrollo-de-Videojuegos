/**
 * JFrameJuego
 *
 * Juego tipo brick breaker basado en la serie de televisión Breaking Bad.
 *
 * @author Humberto Makoto Morimoto Burgos     A01280458
 * @author Eduardo Andrade Martínez     A01035059
 * @version 1.00 2014/10/01
 * 
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class JFrameJuego extends JFrame implements Runnable, KeyListener {
    
    private Barra barBarra;  // Barra de juego que golpeara el proyectil.
    private Proyectil proPelota;  // Pelota que se movera por la ventana.
    private LinkedList lnkBloques;  // Coleccion de Bloques.
    private LinkedList lnkPoderes;  // Coleccion de Poderes.
    private boolean bPausado;  // Variable que indica si el juego esta pausado.
    private int iVidas;  // Vidas del jugador
    /* Variable que indica la direccion de la barra
     * (false-izquierda, true-derecha). */
    private boolean bDireccionBarra;
    /* Variable que indica la direccion de la pelota
     * (1-arriba/derecha, 2-arriba/izquierda, 3-abajo/izquierda,
     * 4-abajo/derecha). */
    private int iDireccionPelota;
    // Variable para controlar el movimiento de la barra.
    private boolean bMovimientoBarra;
    // Variable para indicar que apenas se lanzara pelota.
    private boolean bApenasIniciado;
    // Variable para indicar que no se ha iniciado el juego.
    private boolean bNoIniciado;
    
    // Variable de control de tiempo de la animacion.
    private long tiempoActual;
    
    /* objetos para manejar el buffer del JFrame y este no parpadee */
    private Image    imaImagenJFrame;   // Imagen a proyectar en Applet.
    private Graphics graGraficaJFrame;  // Objeto grafico de la Imagen.
    
    /**
     * JFrameJuego
     * 
     * Metodo constructor de la clase <code>JFrameJuego</code> en donde se
     * definen las caracteristicas de la ventana.
     * 
     */
    public JFrameJuego() {
        //Se define el título de la ventana.
        setTitle("Breaking Bad - Brick Breaker");
        
        /* Se define la operación que se llevará acabo cuando la ventana sea
         * cerrada. */
        // Al cerrar, el programa terminará su ejecución.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Se define el tamaño inicial de la ventana.
        setSize(512, 500);
        
        // Se mandan llamar a los metodos init y start.
        init();
        start();
    }
	
    /** 
     * init
     * 
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>JFrame</code> y se definen funcionalidades.
     * 
     */
    public void init() {
        // No se ha iniciado el juego.
        bNoIniciado = true;
        
        // Se inicializa el juego con 5 vidas.
        iVidas = 5;
        
        // Se indica que apenas inicio el juego.
        bApenasIniciado = true;
        
        // La direccion inicial de la pelota es hacia arriba/derecha.
        iDireccionPelota = 1;
        
        // La direccion inicial de la barra es hacia la derecha.
        bDireccionBarra = true;
        
        // Se inicializa el movimiento de la barra como falso.
        bMovimientoBarra = false;
        
        // Se inicializa el juego como no pausado.
        bPausado = false;
        
        /* Se declara una variable temporal donde se va a guardar la informacion
         * de las animaciones. */
        Animacion aniAnimacionTemporal;
        
        // Se cargan las imágenes(cuadros) para la animacion de la barra.
        Image imaBarra = Toolkit.getDefaultToolkit().
                getImage(this.getClass().getResource("Barras/Barra Normal.png"));
        
        /* Se inicializa la variable de animacion con la animacion de la barra y
         * se introducen las imagenes como parte de ella */
        aniAnimacionTemporal = new Animacion();
        aniAnimacionTemporal.sumaCuadro(imaBarra, 100);
        
        // Se inicializa objeto de barra.
        barBarra = new Barra(0, 450, aniAnimacionTemporal);
        
        // Se posiciona a la barra en el centro horizontalmente.
        barBarra.setX(getWidth() / 2 - 50);
        
         // Se cargan las imágenes(cuadros) para la animacion de la pelota.
        Image imaPelota = Toolkit.getDefaultToolkit().
                getImage(this.getClass().getResource("Proyectil.png"));
        
        /* Se inicializa la variable de animacion con la animacion de la pelota
         * y se introducen las imagenes como parte de ella */
        aniAnimacionTemporal = new Animacion();
        aniAnimacionTemporal.sumaCuadro(imaPelota, 100);
        
        // Se inicializa objeto de pelota encima de barra.
        proPelota = new Proyectil(0, 430,
                aniAnimacionTemporal);
         
        // Se posiciona a la pelota en el centro horizontalmente.
        proPelota.setX(getWidth() / 2 - 22);
        
        // Se define lista de Bloques.
        lnkBloques = new LinkedList();
        
        // Se define lista de Poderes.
        lnkPoderes = new LinkedList();
        
        // Se crearan 48 bloques de tipo y color aleatorio cada uno.
        for(int iCont = 0; iCont < 6; iCont ++) {
            for(int iCont2 = 0; iCont2 < 8; iCont2 ++) {
                // Se genera aleatoriamente color y tipo de bloque.
                int iColor = (int) (Math.random() * 7);
                int iTipo = (int) (Math.random() * 8);
                
                // Se reinicializa la animacion temporal.
                aniAnimacionTemporal = new Animacion();
                
                /* Se inicializan variables para indicar si el bloque tiene
                 * poder y/o daño. */
                int iPoder = 0;
                int iDano = 0;
                
                // Se revisa color generado.
                switch(iColor) {
                    case 0: {
                        // Se revisa tipo.
                        if(iTipo == 0 || iTipo == 5) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque1 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Amarillo 1.png"));
                            Image imaBloque2 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Amarillo 2.png"));
                            Image imaBloque3 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Amarillo 3.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque1,
                                    200);
                            aniAnimacionTemporal.sumaCuadro(imaBloque2,
                                    200);
                            aniAnimacionTemporal.sumaCuadro(imaBloque3,
                                    300);
                        }
                        else if(iTipo == 1 || iTipo == 6) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Amarillo dañado 1.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece el daño en 1.
                            iDano = 1;
                        }
                        else if(iTipo == 2 || iTipo == 7) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Amarillo dañado 2.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece el daño en 2.
                            iDano = 2;
                        }
                        else if(iTipo == 3) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Amarillo poderoso 1.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece el poder en 1.
                            iPoder = 1;
                        }
                        else if(iTipo == 4) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Amarillo poderoso 2.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece el poder en 2.
                            iPoder = 2;
                        }
                        break;
                    }
                    case 1: {
                        // Se revisa tipo.
                        if(iTipo == 0 || iTipo == 5) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque1 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Azul 1.png"));
                            Image imaBloque2 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Azul 2.png"));
                            Image imaBloque3 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Azul 3.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque1,
                                    200);
                            aniAnimacionTemporal.sumaCuadro(imaBloque2,
                                    200);
                            aniAnimacionTemporal.sumaCuadro(imaBloque3,
                                    300);
                        }
                        else if(iTipo == 1 || iTipo == 6) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Azul dañado 1.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece daño en 1.
                            iDano = 1;
                        }
                        else if(iTipo == 2 || iTipo == 7) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Azul dañado 2.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece daño en 2.
                            iDano = 2;
                        }
                        else if(iTipo == 3) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Azul poderoso 1.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece poder en 1.
                            iPoder = 1;
                        }
                        else if(iTipo == 4) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Azul poderoso 2.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece poder en 2.
                            iPoder = 2;
                        }
                        break;
                    }
                    case 2: {
                        // Se revisa tipo.
                        if(iTipo == 0 || iTipo == 5) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque1 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Morado 1.png"));
                            Image imaBloque2 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Morado 2.png"));
                            Image imaBloque3 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Morado 3.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque1,
                                    200);
                            aniAnimacionTemporal.sumaCuadro(imaBloque2,
                                    200);
                            aniAnimacionTemporal.sumaCuadro(imaBloque3,
                                    300);
                        }
                        else if(iTipo == 1 || iTipo == 6) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Morado dañado 1.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece daño en 1.
                            iDano = 1;
                        }
                        else if(iTipo == 2 || iTipo == 7) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Morado dañado 2.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece daño en 2.
                            iDano = 2;
                        }
                        else if(iTipo == 3) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Morado poderoso 1.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece poder en 1.
                            iPoder = 1;
                        }
                        else if(iTipo == 4) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Morado poderoso 2.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece poder en 2.
                            iPoder = 2;
                        }
                        break;
                    }
                    case 3: {
                        // Se revisa tipo.
                        if(iTipo == 0 || iTipo == 5) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque1 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Naranja 1.png"));
                            Image imaBloque2 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Naranja 2.png"));
                            Image imaBloque3 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Naranja 3.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque1,
                                    200);
                            aniAnimacionTemporal.sumaCuadro(imaBloque2,
                                    200);
                            aniAnimacionTemporal.sumaCuadro(imaBloque3,
                                    300);
                        }
                        else if(iTipo == 1 || iTipo == 6) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Naranja dañado 1.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece dano en 1.
                            iDano = 1;
                        }
                        else if(iTipo == 2 || iTipo == 7) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Naranja dañado 2.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece dano en 2.
                            iDano = 2;
                        }
                        else if(iTipo == 3) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Naranja poderoso 1.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece poder en 1.
                            iPoder = 1;
                        }
                        else if(iTipo == 4) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Naranja poderoso 2.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece poder en 2.
                            iPoder = 2;
                        }
                        break;
                    }
                    case 4: {
                        // Se revisa tipo.
                        if(iTipo == 0 || iTipo == 5) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque1 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Rojo 1.png"));
                            Image imaBloque2 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Rojo 2.png"));
                            Image imaBloque3 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Rojo 3.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque1,
                                    200);
                            aniAnimacionTemporal.sumaCuadro(imaBloque2,
                                    200);
                            aniAnimacionTemporal.sumaCuadro(imaBloque3,
                                    300);
                        }
                        else if(iTipo == 1 || iTipo == 6) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Rojo dañado 1.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece dano en 1.
                            iDano = 1;
                        }
                        else if(iTipo == 2 || iTipo == 7) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Rojo dañado 2.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece daño en 2.
                            iDano = 2;
                        }
                        else if(iTipo == 3) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Rojo poderoso 1.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece poder en 1.
                            iPoder = 1;
                        }
                        else if(iTipo == 4) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Rojo poderoso 2.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece poder en 2.
                            iPoder = 2;
                        }
                        break;
                    }
                    case 5: {
                        // Se revisa tipo.
                        if(iTipo == 0 || iTipo == 5) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque1 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Rosa 1.png"));
                            Image imaBloque2 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Rosa 2.png"));
                            Image imaBloque3 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Rosa 3.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque1,
                                    200);
                            aniAnimacionTemporal.sumaCuadro(imaBloque2,
                                    200);
                            aniAnimacionTemporal.sumaCuadro(imaBloque3,
                                    300);
                        }
                        else if(iTipo == 1 || iTipo == 6) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Rosa dañado 1.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece daño en 1.
                            iDano = 1;
                        }
                        else if(iTipo == 2 || iTipo == 7) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Rosa dañado 2.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece daño en 2.
                            iDano = 2;
                        }
                        else if(iTipo == 3) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Rosa poderoso 1.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece poder en 1.
                            iPoder = 1;
                        }
                        else if(iTipo == 4) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Rosa poderoso 2.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece poder en 2.
                            iPoder = 2;
                        }
                        break;
                    }
                    case 6: {
                        // Se revisa tipo.
                        if(iTipo == 0 || iTipo == 5) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque1 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Verde 1.png"));
                            Image imaBloque2 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Verde 2.png"));
                            Image imaBloque3 = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Verde 3.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque1,
                                    200);
                            aniAnimacionTemporal.sumaCuadro(imaBloque2,
                                    200);
                            aniAnimacionTemporal.sumaCuadro(imaBloque3,
                                    300);
                        }
                        else if(iTipo == 1 || iTipo == 6) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Verde dañado 1.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece daño en 1.
                            iDano = 1;
                        }
                        else if(iTipo == 2 || iTipo == 7) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Verde dañado 2.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece daño en 2.
                            iDano = 2;
                        }
                        else if(iTipo == 3) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Verde poderoso 1.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece poder en 1.
                            iPoder = 1;
                        }
                        else if(iTipo == 4) {
                            /* Se cargan las imágenes(cuadros) para la
                             * animacion del bloque. */
                            Image imaBloque = Toolkit.getDefaultToolkit().
                                getImage(this.getClass().
                                    getResource("Bloques/Verde poderoso 2.png"));

                            /* Se introducen las imagenes a la variable
                             * animacion temporal. */
                            aniAnimacionTemporal.sumaCuadro(imaBloque,
                                    200);
                            
                            // Se establece poder en 2.
                            iPoder = 2;
                        }
                        break;
                    }
                }
                
                // Se genera un bloque en la posicion correcta.
                Bloques bloBloque = new Bloques(iCont2 * 64, 50 + iCont * 40,
                        aniAnimacionTemporal, iDano, iPoder, iColor);

                // Se agrega el Bloque a la coleccion de Bloques.
                lnkBloques.add(bloBloque);
            }
        }
        
        /* Se le añade la opcion al applet de ser escuchado por los eventos
           del teclado.  */
	addKeyListener(this);
    }
	
    /** 
     * start
     * 
     * En este metodo se crea e inicializa el hilo
     * para la animacion. Este metodo es llamado despues del init por
     * el metodo constructor del JFrameJuego.
     * 
     */
    public void start () {
        // Declaras un hilo
        Thread th = new Thread (this);
        // Empieza el hilo
        th.start ();
    }
	
    /** 
     * run
     * 
     * Metodo sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, que contendrá las instrucciones
     * de nuestro juego.
     * 
     */
    public void run () {
        // se realiza el ciclo del juego mientras todavia hayan vidas
        while (true) {
            /* mientras no este pausado el juego, se actualizan posiciones
             * de jugadores se checa si hubo colisiones para desaparecer
             * jugadores o corregir movimientos */
            if(!bPausado && !lnkBloques.isEmpty() && iVidas > 0 && !bNoIniciado) {
                actualiza();
                checaColision();
            }
            // Se vuelve a pintar todo
            repaint();
            try	{
                // El thread se duerme.
                Thread.sleep (20);
            }
            catch (InterruptedException iexError) {
                System.out.println("Hubo un error en el juego " + 
                        iexError.toString());
            }
	}
    }
	
    /** 
     * actualiza
     * 
     * Metodo que actualiza la posicion de los objetos en el
     * <code>JFrame</code>. 
     * 
     */
    public void actualiza(){
        /* Determina el tiempo que ha transcurrido desde que el Applet inicio
         * su ejecucion. */
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;

        // Guarda el tiempo actual.
        tiempoActual += tiempoTranscurrido;

        // Actualiza las animaciones en base al tiempo transcurrido.
        barBarra.getAnimacion().actualiza(tiempoTranscurrido);  // En Barra.
        proPelota.getAnimacion().actualiza(tiempoTranscurrido);  // En Pelota.
        
        // En Bloques.  
        for(int iI = 0; iI < lnkBloques.size(); iI ++) {
            /* Se guarda el objeto Bloque que esta en el punto actual
             * de la colección en una variable temporal. */
            Bloques bloBloque = (Bloques) lnkBloques.get(iI);
            
            // Actualiza la animacion de bloque actual.
            bloBloque.getAnimacion().actualiza(tiempoTranscurrido);
        }
        
        // En Poderes se actualiza animacion y posicion.
        for(Object objPoder:lnkPoderes) {
            /* Se guarda el objeto Poder que esta en el punto actual
             * de la colección en una variable temporal. */
            Poder podPoder = (Poder) objPoder;
            
            // Actualiza la animacion de bloque actual.
            podPoder.getAnimacion().actualiza(tiempoTranscurrido);
            
            // Se actualiza posicion.
            podPoder.abajo();
        }

        // Se revisa si la barra se puede mover.
        if(bMovimientoBarra) {
            // Se revisa la direccion de la barra.
            // Si va a la derecha.
            if(bDireccionBarra) {
                barBarra.derecha();  // La barra se mueve a la derecha.
                // Si apenas se inicio el juego.
                if(bApenasIniciado) {
                    proPelota.derecha();  // También se mueve la pelota.
                }
            }
            // Si va a la izquierda.
            else {
                barBarra.izquierda();  // La barra se mueve a la izquierda.
                // Si apenas se inicio el juego.
                if(bApenasIniciado) {
                    proPelota.izquierda();  // También se mueve la pelota.
                }
            }
        }
        
        // Si ya se empezo el juego.
        if(!bApenasIniciado) {
            // Se revisa la direccion de la pelota.
            switch(iDireccionPelota) {
                // Si la direccion es arriba/derecha.
                case 1: {
                    proPelota.arriba();  // La pelota se mueve arriba.
                    proPelota.derecha();  // La pelota se mueve a la derecha.
                    break;
                }
                // Si la direccion es arriba/izquierda.
                case 2: {
                    proPelota.arriba();  // La pelota se mueve arriba.
                    proPelota.izquierda();  // La pelota se mueve a la izquierda.
                    break;
                }
                // Si la direccion es abajo/izquierda.
                case 3: {
                    proPelota.abajo();  // La pelota se mueve abajo.
                    proPelota.izquierda();  // La pelota se mueve a la izquierda.
                    break;
                }
                // Si la direccion es abajo/derecha.
                case 4: {
                    proPelota.abajo();  // La pelota se mueve abajo.
                    proPelota.derecha();  // La pelota se mueve a la derecha.
                    break;
                }
            }
        }
    }
	
    /**
     * checaColision
     * 
     * Metodo usado para checar la colision de los objetos
     * con las orillas del <code>JFrame</code> y entre si.
     * 
     */
    public void checaColision(){
        // Se revisa si la barra esta chocando con los bordes del JFrame.
        /* Si esta chocando con el borde izquierdo y su direccion es hacia la
         * izquierda. */
        if(barBarra.colisiona(0, barBarra.getY()) && !bDireccionBarra) {
            barBarra.derecha();  // Se mueve la barra un poco hacia la derecha.
            
            // La barra se deja de mover para no salirse del JFrame.
            barBarra.setVelocidad(0);
            
            // Si la pelota se esta moviendo con la barra.
            if(bApenasIniciado) {
                // Se mueve la pelota un poco hacia la derecha.
                proPelota.derecha();
            
                // La pelota se deja de mover para no salirse del JFrame.
                proPelota.setVelocidad(0);
            }
        }
        /* Si esta chocando con el borde derecho y su direccion es hacia la
         * derecha. */
        else if(barBarra.colisiona(getWidth(), barBarra.getY()) &&
                bDireccionBarra) {
            // Se mueve la barra un poco hacia la izquierda.
            barBarra.izquierda();
            
            // La barra se deja de mover para no salirse del JFrame.
            barBarra.setVelocidad(0);
            
            // Si la pelota se esta moviendo con la barra.
            if(bApenasIniciado) {
                // Se mueve la pelota un poco hacia la izquierda.
                proPelota.izquierda();
            
                // La pelota se deja de mover para no salirse del JFrame.
                proPelota.setVelocidad(0);
            }
        }
        // Si no esta chocando.
        else {
            barBarra.setVelocidad(5);  // La barra se mueve con velocidad 5.
            
            // Si la pelota se esta moviendo con la barra.
            if(bApenasIniciado) {
                // La barra se mueve con velocidad 4.
                barBarra.setVelocidad(4);
                
                // La pelota se mueve con velocidad 4.
                proPelota.setVelocidad(4);
            }
        }
        
        // Se revisa si la pelota esta chocando con los bordes del JFrame.
        // Si esta chocando con el borde inferior.
        if(proPelota.colisiona(proPelota.getX(), getHeight())) {
            // Se resta una vida.
            iVidas --;
            
            // Se reinician posiciones de barra y pelota.
            barBarra.setX(getWidth() / 2 - barBarra.getAncho() / 2);
            barBarra.setY(450);
            proPelota.setX(getWidth() / 2 - proPelota.getAncho() / 2);
            proPelota.setY(449 - barBarra.getAlto());
            
            // Se reinicia direccion de pelota hacia arriba/derecha.
            iDireccionPelota = 1;
            
            // Se pone que el juego acaba de iniciar de nuevo (barra y pelota).
            bApenasIniciado = true;
            
            /* Se cargan las imágenes(cuadros) para la
             * animacion del bloque. */
            Image imaBarra = Toolkit.getDefaultToolkit().
                getImage(this.getClass().
                    getResource("Barras/Barra Normal.png"));

            // Se inicializa una variable animacion temporal.
            Animacion aniAnimacion = new Animacion();

            // Se introducen las imagenes a la animacion del bloque.
            aniAnimacion.sumaCuadro(imaBarra, 200);
            
            // Se cambia animacion de barra.
            barBarra.setAnimacion(aniAnimacion);
            
            // Se reinicializa velocidad de pelota.
            proPelota.setVelocidad(4);
            
            // Se eliminan poderes cayendo
            // Se itera en la lista de Poderes.
            for(int iI=0; iI < lnkPoderes.size(); iI ++) {
                /* Se guarda el objeto Poder que esta en el punto actual
                 * de la colección en una variable temporal. */
                Poder podPoder = (Poder) lnkPoderes.get(iI);

                // Se borra el poder.
                lnkPoderes.remove(podPoder);
                
                // Se reinicializa el contador en la lista.
                iI = 0;
            }
        }
        /* Si esta chocando con el borde derecho y su direccion es
         * arriba/derecha. */
        else if(proPelota.colisiona(getWidth(), proPelota.getY()) &&
                iDireccionPelota == 1) {
            // Se cambia la direccion de la barra hacia arriba/izquierda.
            iDireccionPelota = 2;
        }
        /* Si esta chocando con el borde derecho y su direccion es
         * abajo/derecha. */
        else if(proPelota.colisiona(getWidth(), proPelota.getY()) &&
                iDireccionPelota == 4) {
            // Se cambia la direccion de la barra hacia abajo/izquierda.
            iDireccionPelota = 3;
        }
        /* Si esta chocando con el borde izquierdo y su direccion es
         * arriba/izquierda. */
        else if(proPelota.colisiona(0, proPelota.getY()) &&
                iDireccionPelota == 2) {
            // Se cambia la direccion de la barra hacia arriba/derecha.
            iDireccionPelota = 1;
        }
        /* Si esta chocando con el borde izquierdo y su direccion es
         * abajo/izquierda. */
        else if(proPelota.colisiona(0, proPelota.getY()) &&
                iDireccionPelota == 3) {
            // Se cambia la direccion de la barra hacia abajo/derecha.
            iDireccionPelota = 4;
        }
        /* Si esta chocando con el borde superior y su direccion es
         * arriba/izquierda. */
        else if(proPelota.colisiona(proPelota.getX(), 25) &&
                iDireccionPelota == 2) {
            // Se cambia la direccion de la barra hacia abajo/izquierda.
            iDireccionPelota = 3;
        }
        /* Si esta chocando con el borde superior y su direccion es
         * arriba/derecha. */
        else if(proPelota.colisiona(proPelota.getX(), 25) &&
                iDireccionPelota == 1) {
            // Se cambia la direccion de la barra hacia abajo/derecha.
            iDireccionPelota = 4;
        }
        
        // Se revisa si la barra y el proyectil chocan.
        if(barBarra.colisiona(proPelota)) {
            // Si la pelota rebota del lado izquierdo de la barra.
            if(proPelota.getX() + proPelota.getAncho() / 2 < barBarra.getX() +
                    barBarra.getAncho() / 2) {
                // La pelota se comienza a mover hacia arriba/izquierda.
                iDireccionPelota = 2;
            }
            // Si la pelota rebota del lado izquierdo de la barra.
            else {
                // La pelota se comienza a mover hacia arriba/derecha.
                iDireccionPelota = 1;
            }
        }
        
        // Se revisa colision entre proyectil y bloques.
        // Se itera en la lista de Bloques.
        for(int iI=0; iI < lnkBloques.size(); iI ++) {
            /* Se guarda el objeto Bloque que esta en el punto actual
             * de la colección en una variable temporal. */
            Bloques bloBloque = (Bloques) lnkBloques.get(iI);
            
            // Se revisa colision entre proyectil y bloque actual.
            if(bloBloque.colisiona(proPelota)) {
                // Si la pelota iba arriba/derecha.
                if(iDireccionPelota == 1) {
                    // Si la pelota pego por el borde izquierdo.
                    if(proPelota.colisiona(bloBloque.getX(), proPelota.getY())) {
                        // La pelota cambia su direccion a arriba/izquierda.
                        iDireccionPelota = 2;
                    }
                    // Si la pelota pego por el borde inferior.
                    if(proPelota.colisiona(proPelota.getX(), 
                            bloBloque.getY() + bloBloque.getAlto())) {
                        // La pelota cambia su direccion a abajo/derecha.
                        iDireccionPelota = 4;
                    }
                }
                // Si la pelota iba arriba/izquierda.
                else if(iDireccionPelota == 2) {
                    // Si la pelota pego por el borde derecho.
                    if(proPelota.colisiona(bloBloque.getX() +
                            bloBloque.getAncho(), proPelota.getY())) {
                        // La pelota cambia su direccion a arriba/derecha.
                        iDireccionPelota = 1;
                    }
                    // Si la pelota pego por el borde inferior.
                    if(proPelota.colisiona(proPelota.getX(), 
                            bloBloque.getY() + bloBloque.getAlto())) {
                        // La pelota cambia su direccion a abajo/izquierda.
                        iDireccionPelota = 3;
                    }
                }
                // Si la pelota iba abajo/izquierda.
                else if(iDireccionPelota == 3) {
                    // Si la pelota pego por el borde derecho.
                    if(proPelota.colisiona(bloBloque.getX() +
                            bloBloque.getAncho(), proPelota.getY())) {
                        // La pelota cambia su direccion a abajo/derecha.
                        iDireccionPelota = 4;
                    }
                    // Si la pelota pego por el borde superior.
                    if(proPelota.colisiona(proPelota.getX(), 
                            bloBloque.getY())) {
                        // La pelota cambia su direccion a arriba/izquierda.
                        iDireccionPelota = 2;
                    }
                }
                // Si la pelota iba abajo/derecha.
                else if(iDireccionPelota == 4) {
                    // Si la pelota pego por el borde izquierdo.
                    if(proPelota.colisiona(bloBloque.getX(), proPelota.getY())) {
                        // La pelota cambia su direccion a abajo/izquierda.
                        iDireccionPelota = 3;
                    }
                    // Si la pelota pego por el borde superior.
                    if(proPelota.colisiona(proPelota.getX(), 
                            bloBloque.getY())) {
                        // La pelota cambia su direccion a arriba/derecha.
                        iDireccionPelota = 1;
                    }
                }
                
                // Si el bloque contenia un poder.
                if(bloBloque.getPoder() > 0) {
                    // Se declaran variables tipo imagen.
                    Image imaPoder1;
                    Image imaPoder2;
                    
                    // Se declara variable indicador de si poder es bueno o no.
                    boolean bBueno;
                    
                    /* Se revisa que poder fue y se agregan imagenes
                     * correspondientes. */
                    if(bloBloque.getPoder() == 1) {
                        /* Se cargan las imágenes(cuadros) para la
                         * animacion del bloque. */
                        imaPoder1 = Toolkit.getDefaultToolkit().
                            getImage(this.getClass().
                                getResource("Bloques/Poder 1-1.png"));
                        imaPoder2 = Toolkit.getDefaultToolkit().
                            getImage(this.getClass().
                                getResource("Bloques/Poder 1-2.png"));
                        
                        // Se indica que el poder es bueno.
                        bBueno = true;
                    }
                    else {
                        /* Se cargan las imágenes(cuadros) para la
                         * animacion del bloque. */
                        imaPoder1 = Toolkit.getDefaultToolkit().
                            getImage(this.getClass().
                                getResource("Bloques/Poder 2-1.png"));
                        imaPoder2 = Toolkit.getDefaultToolkit().
                            getImage(this.getClass().
                                getResource("Bloques/Poder 2-2.png"));
                        
                        // Se indica que el poder es malo.
                        bBueno = false;
                    }
                    
                    // Se crea una variable animacion temporal.
                    Animacion aniAnimacion = new Animacion();
                    
                    /* Se introducen las imagenes a la variable animacion
                     * temporal. */
                    aniAnimacion.sumaCuadro(imaPoder1, 200);
                    aniAnimacion.sumaCuadro(imaPoder2, 200);
                    
                    // Se crea el poder.
                    Poder podPoder = new Poder(
                            bloBloque.getX() + bloBloque.getAncho() / 2 - 12,
                            bloBloque.getY(), aniAnimacion, bBueno);
                    
                    // Se agrega poder a coleccion de Poderes.
                    lnkPoderes.add(podPoder);
                }
                
                // Si ya se debe destruir el bloque.
                if(bloBloque.getDano() > 2) {
                    // Se borra el bloque.
                    lnkBloques.remove(bloBloque);
                }
            }
        }
        
        /* Se revisa colision entre poder y borde inferior de JFrame y entre
         * poder y barra. */
        // Se itera en la lista de Poderes.
        for(int iI=0; iI < lnkPoderes.size(); iI ++) {
            /* Se guarda el objeto Poder que esta en el punto actual
             * de la colección en una variable temporal. */
            Poder podPoder = (Poder) lnkPoderes.get(iI);
            
            // Si colisiono con borde inferior.
            if(podPoder.colisiona(podPoder.getX(), getHeight() +
                    podPoder.getAlto())) {
                // Se borra el poder.
                lnkPoderes.remove(podPoder);
            }
            
            // Si colisiono con barra.
            if(podPoder.colisiona(barBarra)) {
                // Si el poder es bueno.
                if(podPoder.getBueno()) {
                    // Si la barra se hace más larga.
                    if(podPoder.getPoder() == 0) {
                        // Se establece velocidad de pelota en 4.
                        proPelota.setVelocidad(4);
                    }
                    // Si el poder reduce velocidad de pelota.
                    else if(podPoder.getPoder() == 1) {
                        // Se reduce velocidad de pelota.
                        proPelota.setVelocidad(3);
                    }
                    // Si se aumentan vidas.
                    else if(podPoder.getPoder() == 2) {
                        // Se aumenta en 1 la vida.
                        iVidas ++;

                        // Se establece velocidad de pelota en 4.
                        proPelota.setVelocidad(4);
                    }
                }
                // Si el poder es malo.
                else {
                    // Si la barra se hace más corta.
                    if(podPoder.getPoder() == 0) {
                        // Se establece velocidad de pelota en 4.
                        proPelota.setVelocidad(4);
                    }
                    // Si el poder aumenta velocidad de pelota.
                    else if(podPoder.getPoder() == 1) {
                        // Se aumenta velocidad de pelota.
                        proPelota.setVelocidad(5);
                    }
                    // Si se reducen vidas.
                    else if(podPoder.getPoder() == 2) {
                        // Se reduce en 1 la vida.
                        iVidas --;

                        // Se establece velocidad de pelota en 4.
                        proPelota.setVelocidad(4);
                    }
                }
                
                // Se borra el poder.
                lnkPoderes.remove(podPoder);
            }
        }
        
    }
    
    /**
     * keyPressed
     * 
     * Metodo sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al dejar presionada
     * alguna tecla.
     * 
     * @param keyEvent es el <code>evento</code> generado al presionar.
     * 
     */
    public void keyPressed(KeyEvent keyEvent) {
        // Si se presiono la tecla de flecha a la izquierda.
        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            bMovimientoBarra = true;  // La barra se puede mover.
            bDireccionBarra = false;  // La direccion de la barra es izquierda.
        }
        // Si se presiono la tecla de flecha a la derecha.
        else if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            bMovimientoBarra = true;  // La barra se puede mover.
            bDireccionBarra = true;  // La direccion de la barra es derecha.
        }
    }
    
    /**
     * keyTyped
     * 
     * Metodo sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una 
     * tecla que no es de accion.
     * 
     * @param keyEvent es el <code>evento</code> que se genera en al presionar.
     * 
     */
    public void keyTyped(KeyEvent keyEvent){
    	// No hay codigo pero se debe escribir el metodo
    }
    
    /**
     * keyReleased
     * 
     * Metodo sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla.
     * 
     * @param keyEvent es el <code>evento</code> que se genera al soltar las
     * teclas.
     * 
     */
    public void keyReleased(KeyEvent keyEvent) {
        // Si se deja de presionar la tecla de flecha a la izquierda.
        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            bMovimientoBarra = false;  // La barra ya no se puede mover.
        }
        // Si se deja de presionar la tecla de flecha a la derecha.
        else if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            bMovimientoBarra = false;  // La barra no se puede mover.
        }
        // Si se presiono la tecla 'P'
        else if(keyEvent.getKeyCode() == KeyEvent.VK_P) {
            bPausado = ! bPausado;  // se cambia el estado de pausa.
        }
        // Si el juego apenas inicio y se presiona la barra espaciadora.
        else if(bNoIniciado && keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
            bNoIniciado = !bNoIniciado;  // Se inicia el juego.
        }
        // Si no se ha lanzado pelota y se presiona la barra espaciadora.
        else if(bApenasIniciado && keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
            bApenasIniciado = false;  // Se indica que ya se comienza a jugar.
        }
        // Si se termino juego y se presiona barra espaciadora.
        else if((lnkBloques.isEmpty() || iVidas == 0) && 
                keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
            init();  // Se mandan llamar condiciones iniciales del juego.
            removeKeyListener(this);  // Se elimina key listener extra creado.
        }
    }
    
    /**
     * grabaArchivo
     * 
     * Metodo grabaArchivo que sirve para guardar el estado actual del juego
     * y poder cargarlo despues.
     * 
     */
    public void grabaArchivo() {
        // Se pausa el juego
        bPausado = true;

        /* Se crea variable que indica si se guarda el archivo o no,
         * inicializandola como si indicara que si */
        int iGuardar = JOptionPane.YES_OPTION;

        // Se crea variable tipo archivo con la direccion del archivo
        File filArchivoDatos = new File("Datos.txt");

        // Si el archivo existe
        if(filArchivoDatos.isFile()) {
            /* Aparece un mensaje diciendo que todos los datos guardados se
             * borraran al realizar la accion, preguntando si se desea
             * continuar */
            iGuardar = JOptionPane.showConfirmDialog(this, "Si se realiza "
                + " esta operación se borraran los datos guardados. "
                + "¿Desea continuar?",
                "Advertencia", JOptionPane.YES_NO_OPTION);
        }

        /* Se guardan los datos si se indico que asi se quiere o si no
         * existe el archivo todavia */
        if(iGuardar == JOptionPane.YES_OPTION) {
            /* Se atrapan excepciones que pudiera ocurrir por manejo de
             * archivos */
            try {
                // Se declara variable para grabar en archivo Datos.txt
                PrintWriter prwOut = new PrintWriter(new
                    FileWriter("Datos.txt"));

                // Se cierra archivo Datos.txt
                prwOut.close();
            }
            catch (IOException ioeError) {
                System.out.println("Error en " + ioeError.toString());
            }

            /* Aparece un mensaje indicando que se ha finalizado el guardado
             * del juego */
            JOptionPane.showMessageDialog(this,
                    "El juego se ha guardado.", "Guardar Datos",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    /**
     * 
     * leeArchivo
     * 
     * Metodo leeArchivo que sirve para cargar los datos del juego guardado en
     * el juego actual para proseguir desde donde se dejo el juego anterior.
     * 
     */
    public void leeArchivo() {
        // Se pausa el juego
        bPausado = true;

        /* Se crea variable que indica si se carga el archivo o no,
         * inicializandola como si indicara que si */
        int iCargar = JOptionPane.NO_OPTION;

        // Se crea variable tipo archivo con la direccion del archivo
        File filArchivoDatos = new File("Datos.txt");

        // Si el archivo no existe
        if(!filArchivoDatos.isFile()) {
            // Aparece un mensaje diciendo que no hay datos guardados
            JOptionPane.showMessageDialog(this,
                    "No hay datos guardados todavía", "Advertencia",
                    JOptionPane.PLAIN_MESSAGE);
        }

        // Si el archivo existe
        else {
            /* Aparece un mensaje indicando que si se carga el juego se
             * perdera el proceso no guardado */
            iCargar = JOptionPane.showConfirmDialog(this, "Si se carga el"
                    + " juego se perderá el proceso no guardado. ¿Desea"
                    + " continuar?",
                    "Advertencia", JOptionPane.YES_NO_OPTION);
        }

        /* Se cargan los datos si se indico que asi se quiere o si no
         * existe el archivo todavia */
        if(iCargar == JOptionPane.YES_OPTION) {
            /* Se atrapan excepciones que pudiera ocurrir por manejo de
             * archivos */
            try {
                // Se declara variable para leer del archivo Datos.txt
                BufferedReader bfrIn = new BufferedReader(new
                    FileReader("Datos.txt"));

                // Se cierra archivo Datos.txt
                bfrIn.close();
            }
            catch (IOException ioeError) {
                System.out.println("Error en " + ioeError.toString());
            }

            /* Aparece un mensaje indicando que se ha finalizado el guardado
             * del juego */
            JOptionPane.showMessageDialog(this,
                    "El juego se ha cargado correctamente", 
                    "Carga completa",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    /**
     * paint
     * 
     * Metodo sobrescrito de la clase <code>JFrame</code>,
     * heredado de la clase Container.<P>
     * 
     * En este metodo lo que se hace es actualizar el contenedor y 
     * define cuando usar ahora el paint1.
     * 
     * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
     * 
     */
    public void paint (Graphics graGrafico) {
        // Inicializan el DoubleBuffer
        if (imaImagenJFrame == null) {
                imaImagenJFrame = createImage (this.getSize().width, 
                        this.getSize().height);
                graGraficaJFrame = imaImagenJFrame.getGraphics ();
        }

        // Se crea imagen para el background
        URL urlImagenBackground = this.getClass().getResource("Background.jpg");
        Image imaImagenBackground =
                Toolkit.getDefaultToolkit().getImage(urlImagenBackground);

        // Despliego la imagen
        graGraficaJFrame.drawImage(imaImagenBackground, 0, 0, 
                getWidth(), getHeight(), this);

        // Actualiza el Foreground.
        graGraficaJFrame.setColor (getForeground());
        paint1(graGraficaJFrame);

        // Dibuja la imagen actualizada
        graGrafico.drawImage (imaImagenJFrame, 0, 0, this);
    }
    
    /**
     * paint1
     * 
     * En este metodo se dibuja la imagen con la posicion actualizada,
     * ademas que cuando la imagen es cargada te despliega una advertencia.
     * 
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     * 
     */
    public void paint1(Graphics g) {
        // Si la imagen ya se cargo.
        if (barBarra != null && proPelota != null && lnkBloques != null
                && lnkPoderes != null) {
            // Si no ha empezado el juego.
            if(bNoIniciado) {
                
            }
            // Si ya empezo el juego.
            else {
                // Si sigue el juego.
                if(!lnkBloques.isEmpty() && iVidas > 0) {
                    // Se itera en la coleccion de Bloques.    
                    for(Object objBloque:lnkBloques) {
                        /* Se guarda el objeto Bloque que esta en el punto actual
                         * de la colección en una variable temporal. */
                        Bloques bloBloque = (Bloques) objBloque;

                        /* Dibuja la imagen de cada Bloque en la posicion
                         * debida. */
                        g.drawImage(bloBloque.getAnimacion().getImagen(),
                                bloBloque.getX(), bloBloque.getY(), this);
                    }

                    // Se itera en la coleccion de Poderes.    
                    for(Object objPoder:lnkPoderes) {
                        /* Se guarda el objeto Poder que esta en el punto actual
                         * de la colección en una variable temporal. */
                        Poder podPoder = (Poder) objPoder;

                        /* Dibuja la imagen de cada Bloque en la posicion
                         * debida. */
                        g.drawImage(podPoder.getAnimacion().getImagen(),
                                podPoder.getX(), podPoder.getY(), this);
                    }

                    // Se dibuja la imagen de la barra en la posicion actualizada.
                    g.drawImage(barBarra.getAnimacion().getImagen(),
                            barBarra.getX(), barBarra.getY(), this);

                    // Se dibuja la imagen de la pelota en la posicion actualizada.
                    g.drawImage(proPelota.getAnimacion().getImagen(),
                            proPelota.getX(), proPelota.getY(), this);


                    // Se dibujan las vidas.
                    // Se especifica font para el mensaje.
                    g.setFont(new Font("Arial", Font.BOLD, 14));
                    g.setColor(Color.RED);

                    // Aparece mensaje de vidas.
                    g.drawString("Lives: " + Integer.toString(iVidas), 10, 45);

                    // Se dibuja mensaje si esta pausado.
                    if(bPausado) {
                        // Se especifica font para el mensaje.
                        g.setFont(new Font("Arial", Font.BOLD, 16));
                        g.setColor(Color.RED);

                        // Aparece mensaje de pausado.
                        g.drawString("Paused", getWidth() - 70, 50);
                    }
                }
                // Si se perdio el juego.
                else if(iVidas == 0) {
                    // Se obtiene imagen de Game Over.
                    Image imaGameOver = Toolkit.getDefaultToolkit().getImage(
                            this.getClass().getResource("Game Over.jpg"));

                    // Se dibuja la imagen en el centro.
                    g.drawImage(imaGameOver, 0, 0, this);
                }
                // Si se gano el juego.
                else {
                    // Se obtiene imagen de que se gano.
                    Image imaWin = Toolkit.getDefaultToolkit().getImage(
                            this.getClass().getResource("You Won.jpg"));

                    // Se dibuja la imagen en el centro.
                    g.drawImage(imaWin, 0, 0, this);
                }
            }
        }

        // Si no se ha cargado se dibuja un mensaje.
        else {
                // Se a un mensaje mientras se carga el dibujo.	
                g.drawString("No se cargo la imagen..", 20, 20);
        }
    }
    
}
