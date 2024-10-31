public class Arista {
    int origen, fin;

    public Arista(int origen, int fin) {
        this.origen = origen;
        this.fin = fin;
    }

    public String toString() {
        return origen + " -> " + fin;
    }
}
