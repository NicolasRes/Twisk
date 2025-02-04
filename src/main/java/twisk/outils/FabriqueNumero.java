package twisk.outils;

/**
 * Classe FabriqueNumero qui gère la numérotation des étapes
 */
public class FabriqueNumero {
    private static FabriqueNumero instance;
    private int cptEtape;
    private int cptSemaphore;

    /**
     * Constructeur de la classe FabriqueNumero
     */
    private FabriqueNumero() {
        this.cptEtape = 0;
        this.cptSemaphore = 1;
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
     * Méthode qui remet à 0 le compteur d'étapes
     */
    public void reset() {
        this.cptEtape = 0;
    }
}
