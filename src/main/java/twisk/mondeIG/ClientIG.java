package twisk.mondeIG;

/**
 * Classe qui gère un ClientIG
 */
public class ClientIG {
    int numero;
    private int couleur;
    private EtapeIG etape;

    /**
     * Constructeur de la classe ClientIG
     * @param numero Le numero du client
     * @param couleur La couleur du client
     * @param etape L'étape liée au client
     */
    public ClientIG(int numero, int couleur, EtapeIG etape) {
        this.numero = numero;
        this.couleur = couleur;
        this.etape = etape;
    }

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