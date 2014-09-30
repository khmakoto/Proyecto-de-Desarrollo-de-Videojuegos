/**
 * JFrameExamen
 *
 * Main para llamar al JFrame del juego e inicializarlo.
 *
 * @author Humberto Makoto Morimoto Burgos     A01280458
 * @author Eduardo Andrade Martínez     A01035059
 * @version 1.00 2014/10/01
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
        JFrameJuego jfeFrame = new JFrameJuego();
        // Se despliega la ventana en pantalla al hacerla visible.
        jfeFrame.setVisible(true);
    }
    
}
