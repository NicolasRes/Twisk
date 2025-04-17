package twisk.mondeIG;

/**
 * Classe du modèle qui décrit un guichet
 */
public class GuichetIG extends EtapeIG {
    private int nbJetons;

    /**
     * Constructeur de la classe GuichetIG
     * @param nom Nom du guichet
     * @param larg Largeur du rectangle
     * @param haut Hauteur du rectangle
     */
    public GuichetIG (String nom, int larg, int haut) {
        super(nom, larg, haut);
        this.nbJetons = 1;
        this.setNom(nom);
    }

    /**
     * Méthode qui définit le nombre de jetons du guichet
     * @param nbJetons Le nombre de jetons à attribuer au guichet
     */
    public void setNbJetons(int nbJetons) {
        this.nbJetons = nbJetons;
        this.setNom(this.nom);
    }

    /**
     * Méthode qui initialise le nom du guichet
     * @param nom Le nom choisi pour le guichet par l'utilisateur
     */
    @Override
    public void setNom(String nom) {
        this.nom = nom + "-" + super.getIdentifiant() + " : " + this.nbJetons + " jetons";
    }

    /**
     * Méthode qui renvoie le type d'étape, ici guichet
     * @return Le type d'étape "Guichet"
     */
    public String getType() {
        return "Guichet";
    }

    /**
     * Méthode qui renvoie une version String d'un guichet
     * @return Les informations d'une activité sous forme de String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getNom()).append( " : ");
        sb.append("Identifiant : ").append(super.getIdentifiant()).append("\n");
        sb.append("Nombre de jetons : ").append(this.nbJetons);
        return sb.toString();
    }
}