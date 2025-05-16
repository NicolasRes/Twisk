package twisk.mondeIG;

/**
 * Classe qui gère un ClientIG
 */
public class ClientIG {
    private int numero;
    private double x, y;
    private int couleur;
    private EtapeIG etape;

    /**
     * Constructeur de la classe ClientIG
     * @param numero Le numero du client
     * @param x La coordonnée x du client
     * @param y La coordonnée y du client
     */
    public ClientIG(int numero, double x, double y, int couleur, EtapeIG etape) {
        this.numero = numero;
        this.x = x;
        this.y = y;
        this.couleur = couleur;
        this.etape = etape;
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

    /**
     * Méthode qui renvoie un chiffre aléatoire correspondant à la couleur du client
     * @return La couleur du client (0 : rouge, 1 : bleu, 2 : vert, 3 : orange, 4 : violet)
     */
    public int getCouleurClient() {
        return this.couleur;
    }

    /**
     * Méthode qui renvoie l'EtapeIG liée au ClientIG
     * @return L'EtapeIG du ClientIG
     */
    public EtapeIG getEtape() {
        return this.etape;
    }
}