/**
 * SoundClip
 * 
 * Clase utilizada para manejar sonidos dentro del JFrame.
 *
 * @author Humberto Makoto Morimoto Burgos     A01280458
 * @author Eduardo Andrade Martínez     A01035059
 * @version 1.00 2014/09/17
 * 
 */

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.io.IOException;
import java.net.URL;

public class SoundClip {

    // Atributos de la clase SoundClip.
    private AudioInputStream sample;
    private Clip clip; 
    private boolean looping = false;
    private int repeat = 0;
    private String filename = "";

    /**
     * SoundClip
     * 
     * Metodo constructor utilizado para crear un objeto de la clase
     * SoundClip.
     * 
     */
    public SoundClip() {
        try {
            clip = AudioSystem.getClip();
        }catch (LineUnavailableException e) {
            System.out.println("Error en " + e.toString());
        }
    }

    /**
     * SoundClip
     * 
     * Sobrecarga del metodo constructor utilizado para crear un objeto de la
     * clase Soundclip. Este metodo crea un SoundClip con el sonido de un
     * archivo especifico que llega como parametro.
     * 
     * @param filename es el <code>nombre del archivo de sonido</code> con el
     * que se hara el Soundclip.
     * 
     */
    public SoundClip(String filename) {
        this();
        load(filename);
    }

    /**
     * setLooping
     * 
     * Metodo modificador usado para cambiar el indicador sobre si el sonido
     * debe tocarse en loop o no.
     * 
     * @param looping es el <code>estado del indicador de loop</code> del
     * SoundClip.
     * 
     */
    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    /**
     * setRepeat
     * 
     * Metodo modificador usado para cambiar el indicador sobre si el sonido
     * debe repetirse o no.
     * 
     * @param repeat es el <code>estado del indicador de repeticion</code> del
     * SoundClip.
     * 
     */
    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    /**
     * setFilename
     * 
     * Metodo modificador usado para cambiar el nombre del archivo de sonido
     * que se cargara en el SoundClip.
     * 
     * @param filename es el <code>nombre del archivo de sonido</code> que se
     * cargara en el SoundClip.
     * 
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * getClip
     * 
     * Metodo que regresa el clip de sonido del SoundClip.
     * 
     * @return clip es el <code>clip de sonido</code> del SoundClip.
     * 
     */
    public Clip getClip() {
        return clip;
    }

    /**
     * getLooping
     * 
     * Metodo que regresa el estado del indicador sobre si el sonido esta
     * loopeado o no.
     * 
     * @return looping es el <code>estado del indicador de loop</code> del
     * SoundClip.
     * 
     */
    public boolean getLooping() {
        return looping;
    }

    /**
     * getRepeat
     * 
     * Metodo que regresa el estado del indicador sobre si el sonido se esta
     * repitiendo o no.
     * 
     * @return repeat es el <code>estado del indicador de repeticion</code> del
     * SoundClip.
     * 
     */
    public int getRepeat() {
        return repeat;
    }

    /**
     * getFilename
     * 
     * Metodo que regresa el nombre del archivo de sonido del SoundClip.
     * 
     * @return filename es el <code>nombre del archivo de sonido</code> del
     * SoundClip.
     * 
     */
    public String getFilename() {
        return filename;
    }

    /**
     * getURL
     * 
     * Metodo que regresa la direccion URL de un archivo cuyo nombre es indicado
     * como parametro.
     * 
     * @param filename es el <code>nombre del archivo</code> del cual se
     * obtendra su direccion URL.
     * @return url es la <code>direccion URL</code> del archivo indicado.
     */
    private URL getURL(String filename) {
        URL url = null;
        try {
            url = this.getClass().getResource(filename);
        }catch (Exception e) {
            System.out.println("Error en " + e.toString());
        }
        return url;
    }

    /**
     * isLoaded
     * 
     * Metodo que regresa si el sonido se ha cargado o no del archivo.
     * 
     * @return es el <code>indicador</code> acerca de si se ha cargado o no
     * el sonido desde el archivo.
     */
    public boolean isLoaded() {
        return (boolean)(sample != null);
    }

    /**
     * load
     * 
     * Metodo que carga un sonido desde un archivo cuyo nombre se especifica
     * como parametro y regresa si se pudo cargar o no dicho sonido.
     * 
     * @param audiofile es el <code>nombre del archivo de sonido</code> a cargar.
     * @return es el <code>indicador</code> sobre si el sonido se pudo cargar o
     * no del archivo indicado.
     */
    public boolean load(String audiofile) { 
        try {
            setFilename(audiofile);
            sample = AudioSystem.getAudioInputStream(getURL(filename)); 
            clip.open(sample); 
            return true;
        } catch (IOException e) {
            System.out.println("Error en " + e.toString());
            return false;
        }catch (UnsupportedAudioFileException e) {
            System.out.println("Error en " + e.toString());
            return false;
        }catch (LineUnavailableException e) {
            System.out.println("Error en " + e.toString());
            return false;
        }
    }

    /**
     * play
     * 
     * Metodo que hace que se toque el sonido.
     * 
     */
    public void play() {
        if (!isLoaded()) 
            return;
        clip.setFramePosition(0);
        if (looping) 
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        else 
            clip.loop(repeat);
    }

    /**
     * stop
     * 
     * Metodo que hace que se detenga el sonido.
     * 
     */
    public void stop() {
        clip.stop();
    }
}