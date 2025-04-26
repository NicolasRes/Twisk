package twisk.outils;

/**
 * Classe qui fabrique des identifiants pour les étapes et les points de contrôle
 */
public class FabriqueIdentifiant {
    private static FabriqueIdentifiant instance;
    private int noEtape;
    private int noActivite;
    private int noIdentifiantPDC;
    private int noIdentifiantGuichet;

    /**
     * Constructeur de la classe FabriqueIdentifiant
     */
    public FabriqueIdentifiant() {
        this.noEtape = 0;
        this.noActivite = 0;
        this.noIdentifiantPDC = 0;
        this.noIdentifiantGuichet = 1;
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
     * @return Un entier d'un nouvel identifiant d'étape
     */
    public int getIdentifiantEtape() {
        return this.noEtape++;
    }


    /**
     * Méthode qui produit des identifiants basés sur le compteur noActivite
     * @return Un String d'un nouvel identifiant d'activité
     */
    public String getIdentifiantActivite() {
        String identifiant = "A" + this.noActivite;
        this.noActivite++;
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
     * Méthode qui produit des identifiants basés sur le compteur noIdentifiantGuichet
     * @return Un String d'un nouvel identifiant de guichet
     */
    public String getIdentifiantGuichet() {
        String identifiant = "G" + this.noIdentifiantGuichet;
        this.noIdentifiantGuichet++;
        return identifiant;
    }

    /**
     * Méthode qui réinitialise le compteur d'identifiants des points de contrôle
     */
    public void reset() {
        this.noEtape = 0;
        this.noActivite = 0;
        this.noIdentifiantPDC = 0;
        this.noIdentifiantGuichet = 0;
    }
}