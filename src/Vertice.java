import java.util.List;
import java.util.ArrayList;

/**
 * Clase Vertice
 */
public class Vertice implements Comparable<Vertice> {
    // Atributos de la clase Vertice
    private List<Vertice> vecinos;
    private String nombre;

    /**
     * Constructor de la clase Vertice
     * 
     * @param nombre
     */
    public Vertice(String nombre) {
        this.nombre = nombre;
        this.vecinos = new ArrayList<Vertice>();
    }

    /**
     * Constructor de la clase Vertice
     * 
     * @param vecinos
     * @param nombre
     */
    public Vertice(List<Vertice> vecinos, String nombre) {
        this.vecinos = vecinos;
        this.nombre = nombre;
    }

    /**
     * Metodo para obtener el nombre del vertice
     * 
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo para obtener los vecinos del vertice
     * 
     * @return
     */
    public List<Vertice> getVecinos() {
        return vecinos;
    }

    /**
     * Metodo para modificar los vecinos del vertice
     * 
     * @param vecinos
     */
    public void setVecinos(List<Vertice> vecinos) {
        this.vecinos = vecinos;
    }

    /**
     * Metodo para modificar el nombre del vertice
     * 
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo para agregar un vecino al vertice
     * 
     * @param v
     */
    public void agregaVecino(Vertice v) {
        this.vecinos.add(v);
    }

    /**
     * Metodo que representa un vertice en forma de cadena
     * 
     * @return
     */
    @Override
    public String toString() {
        String s = "v";
        s = s + this.nombre + " vecinos: [";
        int tamano = 0;
        for (Vertice x : vecinos) {
            if (tamano != ((vecinos.size()) - 1)) {
                s = s + "(v" + x.getNombre() + "), ";
            } else {
                s = s + "(v" + x.getNombre() + ")";
            }
            tamano++;
        }
        s = s + "]";
        return s;
    }

    /**
     * Metodo que compara dos vertices
     * 
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vertice)) {
            return false;
        }
        Vertice v = (Vertice) obj;
        return this.nombre.equals(v.getNombre());
    }

    /**
     * Metodo que compara dos vertices
     * 
     * @return
     */
    public int compareTo(Vertice v) {
        return this.nombre.compareTo(v.getNombre());
    }
}
