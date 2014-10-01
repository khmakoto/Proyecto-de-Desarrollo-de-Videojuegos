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
    
    private Barra barBarra;  // Barra de juego que golpeara proyectil.
    private boolean bPausado;  // Variable que indica si el juego esta pausado.
    /* Variable que indica la direccion de la barra
     * (false-izquierda, true-derecha). */
    private boolean bDireccionBarra;
    // Variable para controlar el movimiento de la barra.
    private boolean bMovimientoBarra;
    
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
        setSize(800, 600);
        
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
                getImage(this.getClass().getResource("Barra Normal.png"));
        
        /* Se inicializa la variable de animacion con la animacion de la barra y
         * se introducen las imagenes como parte de ella */
        aniAnimacionTemporal = new Animacion();
        aniAnimacionTemporal.sumaCuadro(imaBarra, 100);
        
        // Se inicializa objeto de barra.
        barBarra = new Barra(0, 550, aniAnimacionTemporal);
        
        // Se posiciona a la barra en el centro horizontalmente.
        barBarra.setX(getWidth() / 2 - barBarra.getAncho() / 2);
        
        /* se le añade la opcion al applet de ser escuchado por los eventos
           del teclado  */
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
            if(! bPausado) {
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
        barBarra.getAnimacion().actualiza(tiempoTranscurrido);
        
        // Se revisa si la barra se puede mover.
        if(bMovimientoBarra) {
            // Se revisa la direccion de la barra.
            // Si va a la derecha.
            if(bDireccionBarra) {
                barBarra.derecha();  // La barra se mueve a la derecha.
            }
            // Si va a la izquierda.
            else {
                barBarra.izquierda();  // La barra se mueve a la izquierda.
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
        }
        /* Si esta chocando con el borde derecho y su direccion es hacia la
         * derecha. */
        else if(barBarra.colisiona(getWidth(), barBarra.getY()) &&
                bDireccionBarra) {
            // Se mueve la barra un poco hacia la izquierda.
            barBarra.izquierda();
            
            // La barra se deja de mover para no salirse del JFrame.
            barBarra.setVelocidad(0);
        }
        // Si no esta chocando.
        else {
            barBarra.setVelocidad(3);  // Nena se mueve con velocidad 3.
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
            bPausado = ! bPausado;  // se cambia el estado de pausa
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
        URL urlImagenBackground = this.getClass().getResource("espacio.jpg");
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
        // Si sigue el juego
        if(true) {
            // Si la imagen ya se cargo
            if (barBarra != null) {
                
                // Se dibuja la imagen de la barra en la posicion actualizada.
                g.drawImage(barBarra.getAnimacion().getImagen(),
                        barBarra.getX(), barBarra.getY(), this);

            }

            // Si no se ha cargado se dibuja un mensaje 
            else {
                    // Se a un mensaje mientras se carga el dibujo	
                    g.drawString("No se cargo la imagen..", 20, 20);
            }
            
            // Se dibuja mensaje si esta pausado
            if(bPausado) {
                g.drawString("Pausado", getWidth() - 80, 50);
            }
        }
    }
    
}
