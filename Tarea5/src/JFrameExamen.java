/**
 * JFrameExamen
 *
 * Primer Examen parcial del curso de videojuegos pasado a JFrame. Consiste en
 * un juego en el que se trata de colisionar con cierto tipo de personajes para
 * ganar puntos mientras se esquiva a otros para no perder vidas.
 *
 * @author Humberto Makoto Morimoto Burgos     A01280458
 * @author Eduardo Andrade Martínez     A01035059
 * @version 1.00 2014/09/17
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

public class JFrameExamen extends JFrame implements Runnable, KeyListener {
    
    // Objeto de la clase Personaje con el que se jugará
    private Personaje perNena;
    private LinkedList lnkListaCaminadores;  // Coleccion de Caminadores
    private LinkedList lnkListaCorredores;  // Coleccion de Corredores
    // Sonido de colision entre Nena y Caminador
    private SoundClip socSonidoGana;
    // Sonido de colision entre Nena y Corredor
    private SoundClip socSonidoPierde;
    // Direccion de Nena (1-arriba, 2-abajo, 3-izquierda, 4-derecha)
    private int iDireccionNena;
    // Indicador para controlar velocidad de Corredores
    private int iControlVelocidad;
    // Indicador para controlar cuando se pierden vidas
    private int iControlVidas;
    private int iVidas;  // Vidas del jugador
    private int iScore;  // Score del jugador
    private int iNumCaminadores;  // Numero de Caminadores que se crean
    private int iNumCorredores;  // Numero de Corredores que se crean
    private boolean bPausado;  // Variable que indica si el juego esta pausado
    private URL urlImagenCaminador;  // Direccion de la imagen del Caminador
    private URL urlImagenCorredor;  // Direccion de la imagen del Corredor
    
    /* objetos para manejar el buffer del Applet y este no parpadee */
    private Image    imaImagenJFrame;   // Imagen a proyectar en Applet	
    private Graphics graGraficaJFrame;  // Objeto grafico de la Imagen
    
    /**
     * JFrameExamen
     * 
     * Metodo constructor de la clase <code>JFrameExamen</code> en donde se
     * definen las caracteristicas de la ventana.
     * 
     */
    public JFrameExamen() {
        //Se define el título de la ventana.
        setTitle("JFrame Examen");
        
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
        // se inicializan las vidas entre 3 y 5 aleatoriamente
        iVidas = ((int)(Math.random() * 3)) + 3;
        
        // se inicializa el control de velocidad de Corredores como iVidas + 3
        iControlVelocidad = iVidas + 3;
        
        // se inicializa el control de vidas con 0 choques con Corredores
        iControlVidas = 0;
        
        // se establece la direccion inicial de Nena hacia la derecha
        iDireccionNena = 4;
        
        // el juego en un inicio no esta pausado
        bPausado = false;
        
        // se crea imagen de Nena
        URL urlImagenNena = this.getClass().getResource("nena.gif");
        
        // se crea a Nena
	perNena = new Personaje(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenNena));
        
        // se posiciona a Nena justo en el centro del Applet
        perNena.setX(getWidth() / 2 - perNena.getAncho() / 2);
        perNena.setY(getHeight() / 2 - perNena.getAlto() / 2);
        
        // se establece la velocidad inicial de Nena en 4
        perNena.setVelocidad(4);
        
        // se define lista de Caminadores
        lnkListaCaminadores = new LinkedList();
        
        // se obtiene URL de imagen de Caminadores
        urlImagenCaminador = this.getClass().getResource("alien1Camina.gif");
        
        /* se crean entre 8 y 10 Caminadores al azar que entran desde la orilla
         * izquierda del Applet y se agregan a la coleccion */
        iNumCaminadores = (int) ((Math.random() * 3) + 8);
        for(int iCont = iNumCaminadores; iCont > 0; iCont --) {
            /* se genera un Caminador (objeto tipo personaje) afuera del Applet
             * (izquierda) con posicion vertical aleatoria. */
            Personaje perCaminador = new Personaje(
                -1 * (int) (Math.random() * getWidth()),
                (int) (Math.random() * getHeight()),
                Toolkit.getDefaultToolkit().getImage(urlImagenCaminador));
            
            // se revisa si el Caminador se creo fuera del Applet verticalmente
            while(perCaminador.colisiona(perCaminador.getX(), getHeight())) {
                // se cambia de posicion vertical
                perCaminador.setY((int) (Math.random() * getHeight()));
            }
            
            // se agrega a Caminador a la coleccion de Caminadores
            lnkListaCaminadores.add(perCaminador);
        }
        
        // se define lista de Corredores
        lnkListaCorredores = new LinkedList();
        
        // se obtiene URL de imagen de Corredores
        urlImagenCorredor = this.getClass().getResource("alien2Corre.gif");
        
        /* se crean entre 10 y 15 Corredores al azar que entran desde la orilla
         * superior del Applet y se agregan a la coleccion */
        iNumCorredores = (int) ((Math.random() * 6) + 10);
        for(int iCont = iNumCorredores; iCont > 0; iCont --) {
            /* se genera un Corredor (objeto tipo personaje) afuera del Applet
             * (arriba) con posicion horizontal aleatoria. */
            Personaje perCorredor = new Personaje(
                (int) (Math.random() * getWidth()),
                -1 * ((int) (Math.random() * getHeight())),
                Toolkit.getDefaultToolkit().getImage(urlImagenCorredor));
            
            // se revisa si el Corredor se creo fuera del Applet horizontalmente
            while(perCorredor.colisiona(getWidth(), perCorredor.getY())) {
                // se cambia de posicion horizontal
                perCorredor.setX((int) (Math.random() * getWidth()));
            }
            
            // se agrega a Corredor a la coleccion de Corredores
            lnkListaCorredores.add(perCorredor);
        }
        
        // Se crea el sonido de la colision entre Nena y Caminador
        socSonidoGana = new SoundClip("Win.wav");
        
        // Se crea el sonido de la colision entre Nena y Corredor
        socSonidoPierde = new SoundClip("Lose.wav");
        
        /* se le añade la opcion al applet de ser escuchado por los eventos
           del teclado  */
	addKeyListener(this);
    }
	
    /** 
     * start
     * 
     * En este metodo se crea e inicializa el hilo
     * para la animacion. Este metodo es llamado despues del init por
     * el metodo constructor del JFrameExamen.
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
        while (iVidas > 0) {
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
     * Metodo que actualiza la posicion de los objetos en el <code>JFrame</code>. 
     * 
     */
    public void actualiza(){
        // Se revisa la direccion de Nena
        switch(iDireccionNena) {
            case 1: {
                perNena.arriba(); // Nena va hacia arriba
                break;
            }
            case 2: {
                perNena.abajo(); // Nena va hacia abajo
                break;
            }
            case 3: {
                perNena.izquierda(); // Nena va hacia la izquierda
                break;
            }
            case 4: {
                perNena.derecha(); // Nena va hacia la derecha
                break;
            }
        }
        
        // Los Caminadores se mueven a la derecha entre 3 a 5 pixeles al azar
        // Se itera en la coleccion de Caminadores
        for(Object objCaminador:lnkListaCaminadores) {
            /* Se guarda el objeto Caminador que está en el punto actual
             * de la colección en una variable temporal */
            Personaje perCaminador = (Personaje) objCaminador;
            
            // Se calcula la velocidad del Caminador actual entre 3 y 5
            perCaminador.setVelocidad((int) (Math.random() * 2) + 3);
            
            // Se mueve al Caminador actual a la derecha
            perCaminador.derecha();
        }
        
        /* Los Caminadores se mueven hacia abajo con la velocidad dependiendo
         * de las vidas */
        // Se itera en la coleccion de Corredores
        for(Object objCorredor:lnkListaCorredores) {
            /* Se guarda el objeto Corredor que está en el punto actual
             * de la colección en una variable temporal */
            Personaje perCorredor = (Personaje) objCorredor;
            
            /* Se calcula la velocidad del Corredor actual dependiendo de las 
             * vidas, con un minimo de velocidad 3 */
            perCorredor.setVelocidad(iControlVelocidad - iVidas);
            
            // Se mueve al Corredor actual hacia abajo
            perCorredor.abajo();
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
        // Se revisa si Nena esta chocando con los bordes del Applet
        // Si esta chocando con el borde superior y su direccion es hacia arriba
        if(perNena.colisiona(perNena.getX(), 0) && iDireccionNena == 1) {
            perNena.abajo();  // Se mueve a Nena un poco hacia abajo
            
            // Nena se deja de mover para no salirse del Applet
            perNena.setVelocidad(0);
        }
        // Si esta chocando con el borde inferior y su direccion es hacia abajo
        else if(perNena.colisiona(perNena.getX(), getHeight()) &&
                iDireccionNena == 2) {
            perNena.arriba();  // Se mueve a Nena un poco hacia arriba
            
            // Nena se deja de mover para no salirse del Applet
            perNena.setVelocidad(0);
        }
        /* Si esta chocando con el borde izquierdo y su direccion es hacia la
         * izquierda */
        else if(perNena.colisiona(0, perNena.getY()) && iDireccionNena == 3) {
            perNena.derecha();  // Se mueve a Nena un poco hacia la derecha
            
            // Nena se deja de mover para no salirse del Applet
            perNena.setVelocidad(0);
        }
        /* Si esta chocando con el borde derecho y su direccion es hacia la
         * derecha */
        else if(perNena.colisiona(getWidth(), perNena.getY()) &&
                iDireccionNena == 4) {
            perNena.izquierda();  // Se mueve a Nena un poco hacia la izquierda
            
            // Nena se deja de mover para no salirse del Applet
            perNena.setVelocidad(0);
        }
        // Si no esta chocando
        else {
            perNena.setVelocidad(4);  // Nena se mueve con velocidad 4
        }
        
        /* Se revisa si los Caminadores ya llegaron a la orilla para
         * reposicionarse de nuevo en el lado izquierdo o si chocaron
         * con Nena */
        // Se itera en la coleccion de Caminadores
        for(Object objCaminador:lnkListaCaminadores) {
            /* Se guarda el objeto Caminador que está en el punto actual
             * de la colección en una variable temporal */
            Personaje perCaminador = (Personaje) objCaminador;
            
            // Se revisa si el Caminador actual ya llego a la orilla
            if(perCaminador.colisiona(getWidth(), perCaminador.getY())) {
                /* Se reposiciona al Caminador fuera del Applet (izquierda) con
                 * posicion vertical aleatoria */
                perCaminador.setX(-1 * (int) (Math.random() * getWidth()));
                perCaminador.setY((int) (Math.random() * getHeight()));
                
                /* Se revisa si el Caminador se creo fuera del Applet
                 * verticalmente */
                while(perCaminador.colisiona(perCaminador.getX(), getHeight())) {
                    // se cambia de posicion vertical
                    perCaminador.setY((int) (Math.random() * getHeight()));
                }
            }
            
            // Se revisa colision entre Caminador actual y Nena
            if(perCaminador.colisiona(perNena)) {
                /* Se reposiciona al Caminador fuera del Applet (izquierda) con
                 * posicion vertical aleatoria */
                perCaminador.setX(-1 * (int) (Math.random() * getWidth()));
                perCaminador.setY((int) (Math.random() * getHeight()));
                
                /* Se revisa si el Caminador se creo fuera del Applet
                 * verticalmente */
                while(perCaminador.colisiona(perCaminador.getX(), getHeight())) {
                    // Se cambia de posicion vertical
                    perCaminador.setY((int) (Math.random() * getHeight()));
                }
                
                iScore ++;  // Se incrementa el score en 1
                
                socSonidoGana.play();  // se escucha sonido de colision
            }
        }
        
        /* Se revisa si los Corredores ya llegaron a la orilla para
         * reposicionarse de nuevo en arriba o si chocaron con Nena */
        // Se itera en la coleccion de Corredores
        for(Object objCorredor:lnkListaCorredores) {
            /* Se guarda el objeto Corredor que está en el punto actual
             * de la colección en una variable temporal */
            Personaje perCorredor = (Personaje) objCorredor;
            
            // Se revisa si el Corredor actual ya llego a la orilla
            if(perCorredor.colisiona(perCorredor.getX(), getHeight())) {
                /* Se reposiciona al Corredor fuera del Applet (arriba) con
                 * posicion horizontal aleatoria */
                perCorredor.setX((int) (Math.random() * getWidth()));
                perCorredor.setY(-1 * (int) (Math.random() * getHeight()));
                
                /* Se revisa si el Corredor se creo fuera del Applet
                 * horizontalmente */
                while(perCorredor.colisiona(getWidth(), perCorredor.getY())) {
                    // Se cambia de posicion horizontal
                    perCorredor.setX((int) (Math.random() * getWidth()));
                }
            }
            
            // Se revisa colision entre Corredor actual y Nena
            if(perCorredor.colisiona(perNena)) {
                /* Se reposiciona al Corredor fuera del Applet (arriba) con
                 * posicion horizontal aleatoria */
                perCorredor.setX((int) (Math.random() * getWidth()));
                perCorredor.setY(-1 * (int) (Math.random() * getHeight()));
                
                /* Se revisa si el Corredor se creo fuera del Applet
                 * horizontalmente */
                while(perCorredor.colisiona(getWidth(), perCorredor.getY())) {
                    // Se cambia de posicion horizontal
                    perCorredor.setX((int) (Math.random() * getWidth()));
                }
                
                // Se incrementa en uno el numero de choques con Corredores
                iControlVidas ++;
                
                socSonidoPierde.play();  // se escucha sonido de colision
                
                // Si cinco Corredores han chocado con Nena
                if(iControlVidas == 5) {
                    iVidas --;  // Se pierde una vida
                    
                    // Se reinicializa el control de vidas en 0
                    iControlVidas = 0;
                }
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
        // No hay codigo pero se debe escribir el metodo
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
    	// Si se presiono la tecla 'W' y el juego no esta pausado
        if(keyEvent.getKeyCode() == KeyEvent.VK_W && ! bPausado) {    
                iDireccionNena = 1;  // cambio la dirección arriba
        }
        // Si se presiono la tecla 'S' y el juego no esta pausado
        else if(keyEvent.getKeyCode() == KeyEvent.VK_S && ! bPausado) {    
                iDireccionNena = 2;  // cambio la dirección abajo
        }
        // Si se presiono la tecla 'A' y el juego no esta pausado
        else if(keyEvent.getKeyCode() == KeyEvent.VK_A && ! bPausado) {    
                iDireccionNena = 3;  // cambio la dirección a la izquierda
        }
        // Si se presiono la tecla 'D' y el juego no esta pausado
        else if(keyEvent.getKeyCode() == KeyEvent.VK_D && ! bPausado) {    
                iDireccionNena = 4;  // cambio la dirección a la derecha
        }
        // Si se presiono la tecla 'P'
        else if(keyEvent.getKeyCode() == KeyEvent.VK_P) {
            bPausado = ! bPausado;  // se cambia el estado de pausa
        }
        // Si se presiono la tecla 'G'
        else if(keyEvent.getKeyCode() == KeyEvent.VK_G) {
            // Se mandan a guardar los datos en el archivo
            grabaArchivo();
        }
        // Si se presiono la tecla 'C'
        else if(keyEvent.getKeyCode() == KeyEvent.VK_C) {
            // Se mandan a cargar los datos del archivo
            leeArchivo();
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

                // Se guardan datos de vidas, score y control de vidas
                prwOut.println(iVidas);
                prwOut.println(iScore);
                prwOut.println(iControlVidas);

                // Se guardan los valores de las posiciones de Nena
                prwOut.println(perNena.getX());
                prwOut.println(perNena.getY());

                // Se guarda el valor de la direccion de Nena
                prwOut.println(iDireccionNena);

                // Se guarda el numero de Caminadores
                prwOut.println(iNumCaminadores);

                // Se guardan los valores de posiciones de cada Caminador
                for(Object objCaminador:lnkListaCaminadores) {
                    /* Se guarda el objeto Caminador que está en el punto
                     * actual de la colección en una variable temporal */
                    Personaje perCaminador = (Personaje) objCaminador;

                    /* Se imprimen las posiciones y la velocidad del
                     * Caminador actual en el archivo */
                    prwOut.println(perCaminador.getX());
                    prwOut.println(perCaminador.getY());
                    prwOut.println(perCaminador.getVelocidad());
                }

                /* Se guarda el numero de Corredores y el control de su
                 * velocidad */
                prwOut.println(iNumCaminadores);
                prwOut.println(iControlVelocidad);

                // Se guardan los valores de posiciones de cada Corredor
                for(Object objCorredor:lnkListaCorredores) {
                    /* Se guarda el objeto Corredor que está en el punto
                     * actual de la colección en una variable temporal */
                    Personaje perCorredor = (Personaje) objCorredor;

                    /* Se imprimen las posiciones del Corredor actual en el
                     * archivo */
                    prwOut.println(perCorredor.getX());
                    prwOut.println(perCorredor.getY());
                }

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

                // Se cargan datos de vidas, score y control de vidas
                iVidas = Integer.parseInt(bfrIn.readLine());
                iScore = Integer.parseInt(bfrIn.readLine());
                iControlVidas = Integer.parseInt(bfrIn.readLine());

                // Se cargan los valores de las posiciones de Nena
                perNena.setX(Integer.parseInt(bfrIn.readLine()));
                perNena.setY(Integer.parseInt(bfrIn.readLine()));

                // Se carga el valor de la direccion de Nena
                iDireccionNena = Integer.parseInt(bfrIn.readLine());

                // Se carga el numero de Caminadores
                iNumCaminadores = Integer.parseInt(bfrIn.readLine());

                /* Los elementos de las listas de Caminadores y de
                 * Corredores se borran */
                lnkListaCaminadores.clear();
                lnkListaCorredores.clear();

                // Se cargan los valores de posiciones de cada Caminador
                for(int iCont = 0; iCont < iNumCaminadores; iCont ++) {
                    /* se crea un Caminador en las posiciones  */
                    Personaje perCaminador = new Personaje(
                        Integer.parseInt(bfrIn.readLine()),
                        Integer.parseInt(bfrIn.readLine()),
                        Toolkit.getDefaultToolkit().
                                getImage(urlImagenCaminador));

                    // Se actualiza la velocidad del Caminador actual
                    perCaminador.setVelocidad(
                            Integer.parseInt(bfrIn.readLine()));

                    // Se agrega el elemento a la lista de Caminadores
                    lnkListaCaminadores.add(perCaminador);
                }

                /* Se carga el numero de Corredores y el control de su
                 * velocidad */
                iNumCaminadores = Integer.parseInt(bfrIn.readLine());
                iControlVelocidad = Integer.parseInt(bfrIn.readLine());

                // Se guardan los valores de posiciones de cada Caminador
                for(int iCont = 0; iCont < iNumCorredores; iCont ++) {
                    /* se crea un Corredor en las posiciones  */
                    Personaje perCorredor = new Personaje(
                        Integer.parseInt(bfrIn.readLine()),
                        Integer.parseInt(bfrIn.readLine()),
                        Toolkit.getDefaultToolkit().
                                getImage(urlImagenCorredor));

                    // Se actualiza la velocidad del Corredor actual
                    perCorredor.setVelocidad(iControlVelocidad - iVidas);

                    // Se agrega el elemento a la lista de Corredores
                    lnkListaCorredores.add(perCorredor);
                }

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
        if(iVidas > 0) {
            // Si la imagen ya se cargo
            if (perNena != null && lnkListaCaminadores != null
                    && lnkListaCorredores != null) {
                // Se itera en la coleccion de Caminadores.    
                for(Object objCaminador:lnkListaCaminadores) {
                    /* Se guarda el objeto Caminador que está en el punto actual
                     * de la colección en una variable temporal. */
                    Personaje perCaminador = (Personaje) objCaminador;
                    /* Dibuja la imagen de cada Caminador en la posicion
                     * actualizada. */
                    g.drawImage(perCaminador.getImagen(), perCaminador.getX(),
                            perCaminador.getY(), this);
                }

                // Se itera en la coleccion de Corredores.    
                for(Object objCorredor:lnkListaCorredores) {
                    /* Se guarda el objeto Corredor que está en el punto actual
                     * de la colección en una variable temporal. */
                    Personaje perCorredor = (Personaje) objCorredor;
                    /* Dibuja la imagen de cada Corredor en la posicion
                     * actualizada. */
                    g.drawImage(perCorredor.getImagen(), perCorredor.getX(),
                            perCorredor.getY(), this);
                }


                // Se dibuja la imagen de Nena en la posicion actualizada
                g.drawImage(perNena.getImagen(), perNena.getX(),
                        perNena.getY(), this);

            }

            // Si no se ha cargado se dibuja un mensaje 
            else {
                    // Se a un mensaje mientras se carga el dibujo	
                    g.drawString("No se cargo la imagen..", 20, 20);
            }

            // Se especifica font para el score y la vida
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.setColor(Color.RED);

            // Se dibuja el score y la vida
            g.drawString("Vidas: " + Integer.toString(iVidas), 10, 50);
            g.drawString("Score: " + Integer.toString(iScore), 10, 70);
            
            // Se dibuja mensaje si esta pausado
            if(bPausado) {
                g.drawString("Pausado", getWidth() - 80, 50);
            }
        }
        
        // Si ya se perdio el juego 
        else {
            // Se crea imagen para Game Over
            URL urlGameOver = this.getClass().getResource("Game Over.jpg");
            Image imaGameOver =
                    Toolkit.getDefaultToolkit().getImage(urlGameOver);

            // Aparece un mensaje de game over
            g.drawImage(imaGameOver,
                    getWidth() / 2 - 118, getHeight() / 2 - 138, this);
        }
    }
    
}
