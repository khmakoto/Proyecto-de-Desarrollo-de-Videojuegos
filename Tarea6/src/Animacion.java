/**
 * Animacion
 * 
 * La clase Animacion maneja una serie de imágenes (cuadros) 
 * y la cantidad de tiempo que se muestra cada cuadro.
 * 
 * @author Humberto Makoto Morimoto Burgos     A01280458
 * @author Eduardo Andrade Martínez     A01035059
 * @version 1.00 2014/10/01
 * 
 */

import java.awt.Image;
import java.util.ArrayList;

public class Animacion {
    // Arreglo de objetos que guardará los cuadros de la animación
    private ArrayList cuadros;
    //Índice del cuadro en el que actualmente se encuentra la animación
    private int indiceCuadroActual;
    //Tiempo que la animación lleva corriendo.
    private long tiempoDeAnimacion;
    //Duración total de la animación
    private long duracionTotal;
    
    /**
     * Animacion
     * 
     * Crea una nueva Animacion vacía.
     * 
     */
    public Animacion(){
       //Crea el arreglo de objetos que guarda los cuadros.
       cuadros = new ArrayList();
       //Inicializa el tiempo total en 0.
       duracionTotal = 0;
       //Llama al método iniciar()
       iniciar();
    }
    
    /**
     * sumaCuadro
     * 
     * Añade un cuadro a la animación con la duración 
     * indicada (tiempo que se muestra la imagen).
     * 
     * @param imagen es la Imagen que llega.
     * @param duracion es la duracion indicada.
     */
    public synchronized void sumaCuadro(Image imagen, long duracion){
        //Agrega la duración del cuadro a la duración de la animación
        duracionTotal += duracion;
        //Agrega el cuadro a la animación
        cuadros.add(new cuadroDeAnimacion(imagen, duracionTotal));
    }
    
    /**
     * iniciar
     * 
     * Inicializa la animación.
     * 
     */
    public synchronized void iniciar(){
             //Inicializa el tiempo de la animación en 0
             tiempoDeAnimacion = 0;
             //Coloca el índice en el primer cuadro de la animación
             indiceCuadroActual = 0;
    }
    
    /**
     * actualiza
     * 
     * Actualiza la imagen (cuadro) actual de la animación, si es necesario.
     * 
     * @param tiempoTranscurrido es el tiempo que transcurre al animar este
     * frame.
     * 
     */
    public synchronized void actualiza(long tiempoTranscurrido){
        //Si la animación tiene más de un cuadro, se actualiza
        if (cuadros.size() > 1){
             //Se suma el tiempo transcurrido al tiempo total
             tiempoDeAnimacion += tiempoTranscurrido;
             /**
              * Si el tiempo transcurrido es mayor al tiempo de la animación.
              */
             if (tiempoDeAnimacion >= duracionTotal){

                 //Resetea el tiempo transcurrido
                 tiempoDeAnimacion = tiempoDeAnimacion % duracionTotal;
                 /**
                  * Posicional el índice en el primer cuadro
                  */                                
                 indiceCuadroActual = 0; 
             }
             /**
              * Cuando el tiempo transcurrido es mayor al tiempo que dura el cuadro, 
              * el índice aumenta y señala al siguiente cuadro
              */
             while (tiempoDeAnimacion > getCuadro(indiceCuadroActual).tiempoFinal){
                     indiceCuadroActual++;
            }
        }
    }
    
    /**
     * getImagen
     * 
     * Captura la imagen actual de la animación. Regresa null si la animación 
     * no tiene imágenes.
     * 
     * @return regresa la imagen actual de la animación.
     * 
     */
    public synchronized Image getImagen(){
        //Si la animación esta vacía
        if (cuadros.size() == 0){

            //Retorna nulo
            return null;
        }
        //De lo contrario
        else {
            /**
             * Llama a método getCuadro para obtener la imagen 
             * del cuadro deseado
             */
            return getCuadro(indiceCuadroActual).imagen;
        }
    }
    
    /**
     * getCuadro
     * 
     * Retorna el cuadro que se encuentra en el índice indicado de la 
     * animación.
     * 
     * @param i es el índice indicado de la animación.
     * @return regresa el cuadro del índice indicado de la animación.
     * 
     */
    private cuadroDeAnimacion getCuadro(int i){
        //Retorna el cuadro deseado
        return (cuadroDeAnimacion)cuadros.get(i);
    }
}
