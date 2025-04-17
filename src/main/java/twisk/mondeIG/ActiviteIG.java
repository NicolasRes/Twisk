package twisk.mondeIG;

import twisk.exceptions.TwiskMenuException;

/**
 * Classe du modèle qui décrit une activité
 */
public class ActiviteIG extends EtapeIG {
    private int delai;
    private int ecart;

    /**
     * Constructeur de la classe ActiviteIG
     * @param nom Nom de l'activité
     * @param larg Largeur du rectangle
     * @param haut Hauteur du rectangle
     */
    public ActiviteIG(String nom, int larg, int haut) {
        super(nom, larg, haut);
        this.delai = 2;
        this.ecart = 1;
        this.setNom(nom);
    }

    /**
     * Méthode qui donne une valeur au délai d'une activité et met à jour son nom
     * @param delai Le délai d'une activité
     */
    public void setDelai(int delai) {
        this.delai = delai;
        this.setNom("Act-" + super.getIdentifiant() + "   " + this.delai + " +/- " + this.ecart);
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
        this.setNom("Act-" + super.getIdentifiant() + "   " + this.delai + " +/- " + this.ecart);
    }

    /**
     * Méthode qui initialise le nom de l'activité
     * @param nom Le nom choisi pour l'activité par l'utilisateur
     */
    @Override
    public void setNom(String nom) {
        this.nom = nom + "-"  + super.getIdentifiant() + "   " + this.delai + " +/- " + this.ecart;
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
        sb.append("Identifiant : ").append(super.getIdentifiant());
        return sb.toString();
    }
}