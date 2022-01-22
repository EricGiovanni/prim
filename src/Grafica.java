import java.util.ArrayList;

public class Grafica {
    // Atributos de la clase Grafica
    private ArrayList<Vertice> vertices;
    private ArrayList<Arista> aristas;

    /**
     * Constructor de la clase Grafica
     * 
     * @param vertices
     * @param aristas
     */
    public Grafica(ArrayList<Vertice> vertices, ArrayList<Arista> aristas) {
        this.vertices = vertices;
        this.aristas = aristas;
    }

    /**
     * Metodo que devuelve el arbol generador de peso minimo con el algoritmo de
     * prim
     * 
     * @return Arbol generador de peso minimo
     */
    public Grafica prim() {
        MinHeap<Arista> minHeap = new MinHeap<Arista>();
        for (Arista a : this.aristas) {
            minHeap.agregar(a);
        }
        ArrayList<Arista> aristasArbol = new ArrayList<Arista>();
        ArrayList<Vertice> verticesArbol = new ArrayList<Vertice>();
        verticesArbol.add(this.vertices.get(0));

        while (aristasArbol.size() != this.vertices.size() - 1) {
            MinHeap<Arista> aux = this.aristasIncidentes(verticesArbol);
            for (Arista a : aux) {
                if (!(encuentraArista(aristasArbol, a)) && ciclo(verticesArbol, a)) {
                    aristasArbol.add(a);
                    if (!existeVertice(verticesArbol, a.getV1())) {
                        verticesArbol.add(a.getV1());
                    } else if (!existeVertice(verticesArbol, a.getV2())) {
                        verticesArbol.add(a.getV2());
                    }
                    break;
                }
            }
        }
        Grafica arbolMinimo = new Grafica(verticesArbol, aristasArbol);
        return arbolMinimo;
    }

    /**
     * Metodo que nos indica si se formara un ciclo al añadir una arista
     * 
     * @param verticesArbol
     * @param a
     * @return
     */
    private boolean ciclo(ArrayList<Vertice> lv, Arista a) {
        return !(existeVertice(lv, a.getV1()) && existeVertice(lv, a.getV2()));
    }

    /**
     * Metodo que nos dice si una arista se encuentra en una lista de aristas
     * 
     * @param lv
     * @param v2
     * @return
     */
    private boolean existeVertice(ArrayList<Vertice> lv, Vertice v2) {
        for (Vertice v : lv) {
            if (v.equals(v2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo que devuelve aristas incidentes a un conjunto de vertices
     * 
     * @return Aristas incidentes a un conjunto de vertices
     */
    public MinHeap<Arista> aristasIncidentes(ArrayList<Vertice> vertices) {
        MinHeap<Arista> aristas = new MinHeap<Arista>();
        for (Vertice v : vertices) {
            for (Arista a : this.aristas) {
                if (incidente(a, v) && !encuentraArista(aristas, a)) {
                    aristas.agregar(a);
                }
            }
        }
        return aristas;
    }

    /**
     * Metodo que nos dice si una arista se encuentra en un Heap de aristas
     * 
     * @param aristas
     * @param a
     * @return
     */
    private boolean encuentraArista(MinHeap<Arista> aristas, Arista a) {
        for (Arista ar : aristas) {
            if (a.getV1().equals(ar.getV1()) && a.getV2().equals(ar.getV2())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo que nos dice si una arista se encuentra en un arreglo de aristas
     * 
     * @param aristas
     * @param a
     * @return
     */
    private boolean encuentraArista(ArrayList<Arista> aristas, Arista a) {
        for (Arista ar : aristas) {
            if (a.getV1().equals(ar.getV1()) && a.getV2().equals(ar.getV2())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo que nos dice si una arista incide en un vertice
     * 
     * @return true si la arista incide en el vertice, false en caso contrario
     */
    private boolean incidente(Arista a, Vertice v) {
        return a.getV1().equals(v) || a.getV2().equals(v);
    }

    /**
     * Metodo que devuelve la representacion en cadena de una grafica
     * 
     * @return
     */
    @Override
    public String toString() {
        String s = "";
        for (Arista a : this.aristas) {
            s += a.toString() + "\n";
        }
        return s;
    }
}
