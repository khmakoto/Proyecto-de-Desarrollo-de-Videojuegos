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

public class Main {
    
    /**
     * main
     * 
     * Metodo que inicializa un objeto de la clase JFrameExamen y lo
     * hace visible para que se comience el juego.
     * 
     * @param args
     */
    public static void main(String[] args){
        // Se crea un nuevo objeto JFrameExamen.
        JFrameExamen jfeFrame = new JFrameExamen();
        // Se despliega la ventana en pantalla al hacerla visible.
        jfeFrame.setVisible(true);
    }
    
}
