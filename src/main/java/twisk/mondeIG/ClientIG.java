package twisk.mondeIG;

/**
 * Classe qui gère un ClientIG
 */
public class ClientIG {
    private int numero;
    private double x, y;

    /**
     * Constructeur de la classe ClientIG
     * @param numero Le numero du client
     * @param x La coordonnée x du client
     * @param y La coordonnée y du client
     */
    public ClientIG(int numero, double x, double y) {
        this.numero = numero;
        this.x = x;
        this.y = y;
    }

    /**
     * Méthode qui renvoie le numéro du client
     * @return Le numéro du client
     */
    public int getNumero() {return this.numero;}

    /**
     * Méthode qui renvoie la coordonnée x du client
     * @return La coordonnée x du client
     */
    public double getX() {return this.x;}

    /**
     * Méthode qui renvoie la coordonnée y du client
     * @return La coordonnée y du client
     */
    public double getY() {return this.y;}
}