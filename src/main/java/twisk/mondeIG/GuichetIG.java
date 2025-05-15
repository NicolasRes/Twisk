package twisk.mondeIG;

import twisk.outils.FabriqueIdentifiant;

/**
 * Classe du modèle qui décrit un guichet
 */
public class GuichetIG extends EtapeIG {
    private int nbJetons;
    private String identifiant;
    private Sens sens;

    /**
     * Énumération des sens de circulations possibles pour un guichet
     */
    public enum Sens {
        AUCUN("aucun"),
        GAUCHE_DROITE("gauche_droite"),
        DROITE_GAUCHE("droite_gauche");

        private final String sens;

        /**
         * Constructeur de sens de circulation
         * @param sens Le sens de circulation à attribuer
         */
        Sens(String sens) {
            this.sens = sens;
        }
    }

    /**
     * Constructeur de la classe GuichetIG
     * @param nom Nom du guichet
     * @param larg Largeur du rectangle
     * @param haut Hauteur du rectangle
     */
    public GuichetIG (String nom, int larg, int haut) {
        super(nom, larg, haut);

        FabriqueIdentifiant id = FabriqueIdentifiant.getInstance();
        this.identifiant = id.getIdentifiantGuichet();
        this.nbJetons = 1;
        this.setNom(nom);
        this.sens = Sens.AUCUN;
    }

    /**
     * Méthode qui définit le nombre de jetons du guichet
     * @param nbJetons Le nombre de jetons à attribuer au guichet
     */
    public void setNbJetons(int nbJetons) {
        this.nbJetons = nbJetons;
    }

    /**
     * Méthode  qui renvoie le nombre de jetons d'un guichet
     * @return Le nombre de jetons d'un guichet
     */
    public int getNbJetons() {
        return this.nbJetons;
    }

    /**
     * Méthode qui initialise le sens du guichet
     * @param nom Le sens choisi pour le guichet par l'utilisateur
     */
    @Override
    public void setNom(String nom) {
        this.nom = nom + "_" + getIdentifiant();
    }

    /**
     * Méthode qui renvoie l'identifiant du guichet
     * @return L'identifiant du guichet sous forme de String
     */
    public String getIdentifiant() {
        return this.identifiant;
    }

    /**
     * Méthode qui renvoie si le guichet est une activité restreinte ou non
     * @return Vrai si l'activité est une activité restreinte, faux sinon
     */
    public boolean estActiviteRestreinte() {
        return false;
    }

    /**
     * Méthode qui renvoie le délai d'une étape
     * @return Le délai d'une étape (0 pour guichet)
     */
    public int getDelai() { // Remplacer par exception ?
        return 0;
    }

    /**
     * Méthode qui renvoie l'écart d'une étape
     * @return L'écart d'une étape (0 pour guichet)
     */
    public int getEcart() { // Remplacer par exception ?
        return 0;
    }

    /**
     * Méthode abstraite qui renvoie les attributs d'un guichet
     * @return Les attributs d'un guichet (nombre de jetons)
     */
    public String getAttributs() {
        return " : " + this.nbJetons + " jetons";
    }

    /**
     * Méthode qui renvoie le type d'étape, ici guichet
     * @return Le type d'étape "Guichet"
     */
    public String getType() {
        return "Guichet";
    }

    /**
     * Méthode qui renvoie le sens du sens de circulation du guichet
     * @return Le sens de circulation du guichet
     */
    public Sens getSens() {
        return this.sens;
    }

    /**
     * Méthode qui permet d'attribuer un sens de circulation à un guichet
     * @param sens Le sens de circulation à attribuer au guichet
     */
    public void setSensGuichet(Sens sens) {
        this.sens = sens;
    }

    /**
     * Méthode qui renvoie une version String d'un guichet
     * @return Les informations d'une activité sous forme de String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getNom()).append( " : ");
        sb.append("Identifiant : ").append(getIdentifiant()).append("\n");
        sb.append("Nombre de jetons : ").append(this.nbJetons);
        return sb.toString();
    }
}