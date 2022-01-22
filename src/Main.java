import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase principal
 */
public class Main {
    /**
     * Metodo main
     * 
     * @param args
     */
    public static void main(String[] args) {
        Scanner file;
        String filename = "";
        ArrayList<Vertice> vertices = new ArrayList<Vertice>();
        ArrayList<Arista> aristas = new ArrayList<Arista>();

        if (args.length == 1) {
            filename = args[0];
        } else {
            Scanner sc = new Scanner(System.in);
            System.out.print("Ingrese el nombre del archivo: ");
            filename = sc.nextLine();
            sc.close();
        }
        try {
            System.out.println("Creando grafica desde el archivo " + filename + "...");
            File f = new File(filename);
            file = new Scanner(f);
            boolean primeraLinea = true;
            while (file.hasNextLine()) {
                String[] line = file.nextLine().split(",");
                if (primeraLinea) {
                    primeraLinea = false;
                    for (int i = 0; i < line.length; i++) {
                        vertices.add(new Vertice(line[i].trim()));
                    }
                } else if (line.length == 3) {
                    Vertice v1 = getVertice(vertices, line[0].trim());
                    Vertice v2 = getVertice(vertices, line[1].trim());
                    v1.agregaVecino(v2);
                    v2.agregaVecino(v1);
                    int peso = Integer.parseInt(line[2].trim());
                    aristas.add(new Arista(v1, v2, peso));
                } else {
                    System.out.println("Error en el archivo");
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo\nSaliendo...");
            System.exit(0);
        }
        Grafica g = new Grafica(vertices, aristas);
        System.out.println("Grafica creada con exito");
        System.out.println(g);
        Grafica arbolMinimo = g.prim();
        System.out.println("==========================================================");
        System.out.println("Arbol generador de peso minimo");
        System.out.println("==========================================================");
        System.out.println(arbolMinimo);
    }

    /**
     * Metodo para obtener un vertice dado su nombre y una lista de vertices
     * 
     * @param vertices
     * @param nombre
     * @return
     */
    public static Vertice getVertice(ArrayList<Vertice> vertices, String nombre) {
        for (Vertice v : vertices) {
            if (v.getNombre().equals(nombre)) {
                return v;
            }
        }
        return null;
    }
}
