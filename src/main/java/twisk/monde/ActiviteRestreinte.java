package twisk.monde;

/**
 * Sous-classe d'Activite de type twisk.monde.ActiviteRestreinte
 */
public class ActiviteRestreinte extends Activite {
    /**
     * Constructeur de la classe twisk.monde.ActiviteRestreinte avec nom pour seul paramètre
     * @param nom Le nom de l'activité restreinte
     */
    public ActiviteRestreinte(String nom) {
        super(nom);
    }

    /**
     * Constructeur de la classe twisk.monde.ActiviteRestreinte avec de multiples paramètres
     * @param nom Le nom de la classe
     * @param temps Le temps qu'on passe dans l'activité restreinte
     * @param ecartTemps L'écart de temps possible dans l'activité restreinte
     */
    public ActiviteRestreinte(String nom, int temps, int ecartTemps) {
        super(nom, temps, ecartTemps);
    }
}