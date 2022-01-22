import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase abstracta para modelar montículos. Las clases concretas pueden ser un
 * montículo mínimo
 * o máximo.
 */
public abstract class Heap<T extends Comparable<T>> implements Coleccionable<T> {

    /**
     * Clase interna para modelar el iterador
     */
    private class Iterador implements Iterator<T> {

        private int siguiente;

        public Iterador() {
            siguiente = 0;
        }

        @Override
        public boolean hasNext() {
            return siguiente != tamanio;
        }

        @Override
        public T next() {
            T aux = arreglo[siguiente];
            siguiente += 1;
            return aux;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    /**
     * Arreglo donde se almacenarán los elementos del montículo.
     **/
    public T[] arreglo;
    /**
     * Cantidad de elementos almacenados en el montículo.
     **/
    private int tamanio;

    /*
     * Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
     * Java implementa sus genéricos; de otra forma obtenemos advertencias del
     * compilador.
     */
    @SuppressWarnings("unchecked")
    private T[] creaArregloGenerico(int n) {
        return (T[]) (new Comparable[n]);
    }

    /**
     * Constructor que no recibe parámetros, crea un arreglo de un tamaño
     * arbitrario.
     * Se recomienda que sea de un tamaño que sea una potencia de 2.
     **/
    public Heap() {
        arreglo = creaArregloGenerico(32);
        tamanio = 0;
    }

    /**
     * Constructor que recibe una estructura iterable como parámetro.
     * Agrega todos los elementos en el orden en que se recorre la estructura dada.
     **/
    public Heap(Iterable<T> it) {
        arreglo = creaArregloGenerico(32);
        Iterator<T> ti = it.iterator();
        while (ti.hasNext()) {
            this.agregar(ti.next());
        }
    }

    /**
     * Método abstracto que se va a usar para comparar dos elementos del heap.
     * Se deja la implementación a las clases concretas, pues dependiendo de éstas
     * el orden es
     * uno o el inverso, según sea el caso.
     * 
     * @param elemento1
     * @param elemento2
     * @return true si elemento1 tiene mayor prioridad que elemento2, false en otro
     *         caso
     */
    abstract protected boolean comparador(T elemento1, T elemento2);

    /**
     * Método que nos da la posición del padre del índice dado
     **/
    private int padre(int indiceElemento) {
        if (indiceElemento % 2 == 0) {
            return indiceElemento / 2 - 1;
        } else {
            return indiceElemento / 2;
        }
    }

    /**
     * Método que nos da la posición del hijo izquierdo del índice dado
     **/
    private int izquierdo(int indiceElemento) {
        return 2 * indiceElemento + 1;
    }

    /**
     * Método que nos da la posición del hijo derecho del índice dado
     **/
    private int derecho(int indiceElemento) {
        return 2 * indiceElemento + 2;
    }

    @Override
    public void agregar(T elemento) {
        if (tamanio == arreglo.length) {
            T[] arr = creaArregloGenerico(tamanio * 2);
            for (int i = 0; i < arreglo.length; i++) {
                arr[i] = arreglo[i];
            }
            arreglo = arr;
        }
        arreglo[tamanio] = elemento;
        rebalanceaHaciaArriba(tamanio);
        tamanio += 1;
    }

    /**
     * Metodo para eliminar el elemento que se encuentra en el tope del heap.
     * El método devuelve el valor eliminado.
     */
    public T eliminarTope() {
        T aux = obtenerPrioritario();
        tamanio -= 1;
        intercambia(0, tamanio);
        rebalanceaHaciaAbajo(0);
        return aux;
    }

    /**
     *
     */
    public void buscaYCambia(T elemento, T cambio) {
        for (int i = 0; i < tamanio; i++) {
            if (arreglo[i].equals(elemento)) {
                arreglo[i] = cambio;
                rebalanceaHaciaArriba(i);
                rebalanceaHaciaAbajo(i);
            }
        }
    }

    public T busca(T elemento) {
        for (int i = 0; i < tamanio; i++) {
            if (arreglo[i].equals(elemento)) {
                return arreglo[i];
            }
        }
        return null;
    }

    /**
     * Método para intercambiar dos elementos en los índices i y j.
     * Antes de usarse debemos asegurarnos de que los índices sean válidos.
     **/
    private void intercambia(int i, int j) {
        T temp = arreglo[i];
        arreglo[i] = arreglo[j];
        arreglo[j] = temp;
    }

    /**
     * Metodo que se encarga de hacer el rebalanceo cuando agregamos un elemento.
     * 
     * @param indiceElemento
     */
    private void rebalanceaHaciaArriba(int copy) {
        while (true) {
            int padre = padre(copy);
            if (padre == -1 || comparador(arreglo[padre], arreglo[copy])) {
                return;
            }
            intercambia(copy, padre);
            copy = padre;
        }
        /*
         * //opcion recursiva para usar el parametro
         * int padre = padre(indiceElemento);
         * if(padre < 0){
         * return;
         * }
         * if(comparador(arreglo[indiceElemento] , arreglo[padre])){
         * intercambia(indiceElemento , padre);
         * rebalanceaHaciaArriba(padre);
         * }else{
         * return;
         * }
         */
    }

    /**
     * Metodo que se encarga de hacer el rebalanceo cuando eliminamos un elemento.
     * 
     * @param indiceElemento
     */
    private void rebalanceaHaciaAbajo(int copy) {
        // int copy = 0;
        while (true) {
            int win = indiceIntercambiable(copy);
            if (win == -1 || comparador(arreglo[copy], arreglo[win])) {
                return;
            }
            intercambia(copy, win);
            copy = win;
        }
    }

    /**
     * Método que nos dice cuál es el índice del elemento que tenemos que
     * intercambiar con el padre.
     * Se utiliza en rebalanceaHaciaAbajo.
     * Si no hay que hacer intercambios porque ya no hay hijos, debe devolver -1.
     **/
    private int indiceIntercambiable(int i) {
        int izq = izquierdo(i);
        int der = derecho(i);
        if (izq >= tamanio) {
            return -1;
        }
        if (der >= tamanio || comparador(arreglo[izq], arreglo[der])) {
            return izq;
        }
        return der;
    }

    /**
     * Metodo para obtener el elemento que se encuentra en el tope del heap
     * 
     * @return
     * @throws NoSuchElementException
     */
    public T obtenerPrioritario() throws NoSuchElementException {
        if (esVacia()) {
            throw new NoSuchElementException();
        }
        return arreglo[0];
    }

    @Override
    public void eliminar(T elemento) throws NoSuchElementException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean contiene(T elemento) {
        for (int i = 0; i < tamanio; i++) {
            if (arreglo[i].equals(elemento)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean esVacia() {
        return tamanio == 0;
    }

    @Override
    public int getTamanio() {
        return tamanio;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        Heap<T> heap = (Heap<T>) o;

        return this.toString().equals(heap.toString());
    }

    @Override
    public String toString() {
        if (esVacia()) {
            return "[]";
        }
        String s = "[";
        for (int i = 0; i < tamanio - 1; i++) {
            s += arreglo[i] + ", ";
        }
        s += arreglo[tamanio - 1] + "]";

        return s;
    }

}
