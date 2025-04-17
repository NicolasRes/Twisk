package twisk.outils;

/**
 * Classe qui fabrique des identifiants pour les étapes et les points de contrôle
 */
public class FabriqueIdentifiant {
    private static FabriqueIdentifiant instance;
    private int noEtape;
    private int noIdentifiantPDC;

    /**
     * Constructeur de la classe FabriqueIdentifiant
     */
    public FabriqueIdentifiant() {
        this.noEtape = 0;
        this.noIdentifiantPDC = 0;
    }

    /**
     * Méthode qui fabrique l'unique instance de FabriqueIdentifiant et le renvoie. Se contente de le renvoyer s'il existe déjà
     * @return L'unique instance de FabriqueIdentifiant
     */
    public static FabriqueIdentifiant getInstance() {
        if(instance == null) {
            instance = new FabriqueIdentifiant();
        }
        return instance;
    }

    /**
     * Méthode qui produit des identifiants basés sur le compteur noEtape
     * @return Un String d'un nouvel identifiant d'étape
     */
    public String getIdentifiantEtape() {
        String identifiant = "N°" + this.noEtape;
        this.noEtape++;
        return identifiant;
    }

    /**
     * Méthode qui génère un identifiant unique
     * @return Un identifiant unique pour un point de contrôle
     */
    public String getIdentifiantPDC() {
        return "P" + noIdentifiantPDC++;
    }

    /**
     * Méthode qui réinitialise le compteur d'identifiants des points de contrôle
     */
    public void reset() {
        noEtape = 0;
        noIdentifiantPDC = 0;
    }
}