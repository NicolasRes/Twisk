package twisk.outil;

/**
 * Classe FabriqueNumero qui gère la numérotation des étapes
 */
public class FabriqueNumero {
    private static FabriqueNumero instance;
    private int cptEtape;
    private int cptSemaphore;
    private int cptSimulation;

    /**
     * Constructeur de la classe FabriqueNumero
     */
    private FabriqueNumero() {
        this.cptEtape = 0;
        this.cptSemaphore = 1;
        this.cptSimulation = 0;
    }

    /**
     * Méthode qui récupère une instance de FabriqueNumero
     * @return Une instance de FabriqueNumero
     */
    public static FabriqueNumero getInstance() {
        if (instance == null) {
            instance = new FabriqueNumero();
        }
        return instance;
    }

    /**
     * Méthode qui récupère le numéro d'une étape
     * @return Le numéro d'une étape
     */
    public int getNumeroEtape() {
        this.cptEtape++;
        return this.cptEtape -1 ;
    }

    /**
     * Méthode qui récupère le numéro sémaphore d'une étape
     * @return Le numéro sémaphore d'une étape
     */
    public int getNumeroSemaphore() {
        this.cptSemaphore++;
        return this.cptSemaphore -1 ;
    }

    /**
     * Méthode qui renvoie le numéro de la simulation
     * @return Le numéro de la simulation
     */
    public int getNumeroSimulation() {
        this.cptSimulation++;
        return this.cptSimulation;
    }

    /**
     * Méthode qui remet à 0 le compteur d'étapes
     */
    public void reset() {
        this.cptEtape = 0;
        this.cptSemaphore = 1;
        this.cptSimulation = 0;
    }
}
