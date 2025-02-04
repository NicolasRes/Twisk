package twisk.monde;

/**
 * Sous-classe d'Activite de type SasSortie
 */
public class SasSortie extends Activite {
    public SasSortie() {
        super("SasSortie");
    }

    /**
     * Méthode qui vérifie si un SasSortie est une sortie
     * @return Vrai pour un SasSortie, faux sinon
     */
    public boolean estUneSortie() {
        return true;
    }

    /**
     * Méthode qui crée une version String de SasSortie
     * @return Une SasSortie sous forme de String
     */
    public String toString() {
        return "Sortie : " + super.toString();
    }
}