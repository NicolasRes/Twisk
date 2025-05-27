package twisk.mondeIG;

import twisk.exceptions.TwiskMenuException;
import twisk.outils.FabriqueIdentifiant;

/**
 * Classe du modèle qui décrit une activité
 */
public class ActiviteIG extends EtapeIG {
    private int delai;
    private int ecart;
    private boolean activiteRestreinte;
    private String identifiant;

    /**
     * Constructeur de la classe ActiviteIG
     * @param nom Nom de l'activité
     * @param larg Largeur du rectangle
     * @param haut Hauteur du rectangle
     */
    public ActiviteIG(String nom, int larg, int haut) {
        super(nom, larg, haut);

        FabriqueIdentifiant id = FabriqueIdentifiant.getInstance();
        this.identifiant = id.getIdentifiantActivite();
        this.delai = 3;
        this.ecart = 2;
        this.setNom(nom);
        this.activiteRestreinte = false;
    }

    /**
     * Méthode qui donne une valeur au délai d'une activité et met à jour son nom
     * @param delai Le délai d'une activité
     */
    public void setDelai(int delai) {
        this.delai = delai;
    }

    /**
     * Méthode qui donne une valeur à l'écart d'une activité et met à jour son nom
     * @param ecart L'écart d'une activité
     */
    public void setEcart(int ecart) throws TwiskMenuException {
        if(ecart > this.delai) {
            throw new TwiskMenuException("L'écart ne peut pas être supérieur au délai");
        }
        this.ecart = ecart;
    }

    /**
     * Méthode qui renvoie le délai d'une activité
     * @return Le délai de l'activité
     */
    public int getDelai() {
        return this.delai;
    }

    /**
     * Méthode qui renvoie l'écart d'une activité
     * @return L'écart de l'activité
     */
    public int getEcart() {
        return this.ecart;
    }

    /**
     * Méthode qui renvoie le nombre de jetons
     * @return Le délai de l'activité
     */
    public int getNbJetons() {
        return 0;
    }   // Remplacer par exception ?

    /**
     * Méthode qui renvoie les attributs délai et écart de l'activité
     * @return String avec le délai et l'écart de l'activité
     */
    public String getAttributs() {
        return this.delai + " +/- " + this.ecart;
    }

    /**
     * Méthode qui initialise le nom de l'activité
     * @param nom Le nom choisi pour l'activité par l'utilisateur
     */
    @Override
    public void setNom(String nom) {
        this.nom = nom + "_"  + getIdentifiant();
    }

    /**
     * Méthode qui inverse l'état du booléen activiteRestreinte
     */
    public void switchActiviteRestreinte() {
        this.activiteRestreinte = !this.activiteRestreinte;
    }

    /**
     * Méthode qui renvoie si l'activité est une activité restreinte ou non
     * @return Vrai si l'activité est une activité restreinte, faux sinon
     */
    public boolean estActiviteRestreinte() {
        return this.activiteRestreinte;
    }

    /**
     * Méthode qui renvoie l'identifiant de l'activité
     * @return L'identifiant de l'activité sous forme de String
     */
    public String getIdentifiant() {
        return this.identifiant;
    }

    /**
     * Méthode qui renvoie le type d'étape, ici activité
     * @return Le type d'étape "Activite"
     */
    public String getType() {
        return "Activite";
    }

    /**
     * Méthode qui renvoie une version String d'une activité
     * @return Les informations d'une activité sous forme de String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getNom()).append( " : ");
        sb.append("Identifiant : ").append(getIdentifiant());
        return sb.toString();
    }
}