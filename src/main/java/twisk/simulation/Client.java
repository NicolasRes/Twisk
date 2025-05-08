package twisk.simulation;

import twisk.monde.Etape;

/**
 * Classe qui gère un objet de type Client
 */
public class Client {
    private int numeroClient;
    private int rang;
    private Etape etape;

    /**
     * Constructeur de la classe Client
     * @param numero Le numéro à attribuer au client
     */
    public Client(int numero) {
        this.numeroClient = numero;
        this.rang = 0;
        this.etape = null;
    }

    /**
     * Méthode qui définit où déplacer le client
     * @param etape L'étape dans laquelle déplacer le client
     * @param rang Le rang de l'étape où se déplace le client
     */
    void allerA(Etape etape, int rang) {
        this.etape = etape;
        this.rang = rang;
    }

    /**
     * Méthode qui renvoie le numéro d'un client
     * @return Le numéro du client
     */
    public int getNumeroClient () {
        return this.numeroClient;
    }

    /**
     * Méthode qui renvoie le rang d'un client
     * @return Le rang du client
     */
    public int getRang() {
        return this.rang;
    }

    /**
     * Méthode qui renvoie l'étape du client
     * @return L'étape du client
     */
    public Etape getEtape() {
        return this.etape;
    }
}
