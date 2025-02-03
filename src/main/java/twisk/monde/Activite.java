package twisk.monde;

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

    /**
     * Méthode qui renvoie les informations de l'activité sous forme de String
     * @return L'activité sous forme de String
     */
    public String toString() {
        return "Temps de l'activité : " + temps + "\n"
                + "Écart temps : " + ecartTemps;
    }
}