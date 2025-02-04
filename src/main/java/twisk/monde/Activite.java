package twisk.monde;

import java.util.Iterator;

/**
 * Sous-classe d'Etape de type Activite
 */
public class Activite extends Etape {
    private int temps;
    private int ecartTemps;

    /**
     * Constructeur de la classe Activite avec nom pour seul paramètre
     * @param nom Le nom de l'activité
     */
    public Activite(String nom) {
        super(nom);
    }

    /**
     * Constructeur de la classe Activite avec multiples paramètres
     * @param nom Le nom de l'activité
     * @param temps Le temps qu'on passe dans l'activité
     * @param ecartTemps L'écart de temps possible dans l'activité
     */
    public Activite(String nom, int temps, int ecartTemps) {
        super(nom);
        this.temps = temps;
        this.ecartTemps = ecartTemps;
    }

    /**
     * Méthode qui renvoie détermine si une étape est de type activité
     * @return Renvoie vrai pour une activité, faux sinon
     */
    public boolean estUneActivite() {
        return true;
    }

    public boolean estUneEntree() {
        return false;
    }

    public boolean estUneSortie() {
        return false;
    }

    /**
     * Méthode qui crée une version String de d'Etape
     * @return Des étapes sous forme de String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getNom()).append(" : ").append(this.nbSuccesseur()).append(" successeur(s) -> ");

        for (Etape etape : this.getEtapes()) {
            sb.append(etape.getNom()).append(", ");
        }

        // Supprime la dernière virgule et l'espace en trop si nécessaire
        if (this.nbSuccesseur() > 0) {
            sb.setLength(sb.length() - 2);
        }

        return sb.toString();
    }

}