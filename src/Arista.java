/**
 * Clase que modela una arista de una grafica
 */
public class Arista implements Comparable<Arista> {
    // Atributos de la clase Arista
    private Vertice v1;
    private Vertice v2;
    private int peso;

    /**
     * Constructor de arista que recibe los dos vertices y el peso de la arista
     * 
     * @param v1
     * @param v2
     * @param peso
     */
    public Arista(Vertice v1, Vertice v2, int peso) {
        this.v1 = v1;
        this.v2 = v2;
        this.peso = peso;
    }

    /**
     * Metodo que devuelve el vertice 1 de la arista
     * 
     * @return
     */
    public Vertice getV1() {
        return this.v1;
    }

    /**
     * Metodo que modifica el vertice 1 de la arista
     * 
     * @param v1
     */
    public void setV1(Vertice v1) {
        this.v1 = v1;
    }

    /**
     * Metodo que devuelve el vertice 2 de la arista
     * 
     * @return
     */
    public Vertice getV2() {
        return this.v2;
    }

    /**
     * Metodo que modifica el vertice 2 de la arista
     * 
     * @param v2
     */
    public void setV2() {
        this.v2 = v2;
    }

    /**
     * Metodo que devuelve el peso de la arista
     * 
     * @return
     */
    public int getPeso() {
        return this.peso;
    }

    /**
     * Metodo que modifica el peso de la arista
     * 
     * @param peso
     */
    public void setPeso(int peso) {
        this.peso = peso;
    }

    /**
     * Metodo que devuelve la representacion en cadena de una arista
     * 
     * @return
     */
    @Override
    public String toString() {
        String s = "(" + v1.getNombre() + ", " + v2.getNombre() + "): " + peso;
        return s;
    }

    /**
     * Metodo que compara dos aristas por su peso
     * 
     * @return
     */
    public int compareTo(Arista o) {
        if (peso < o.peso) {
            return -1;
        } else if (peso > o.peso) {
            return 1;
        }
        return 0;
    }
}
