package twisk.monde;

/**
 * Sous-classe d'Activite de type SasEntree
 */
public class SasEntree extends Activite {
    /**
     * Constructeur de la classe SasEntree
     */
    public SasEntree() {
        super("SasEntree");
    }

    /**
     * Méthode qui vérifie si un SasEntre est une entrée
     * @return Vrai pour un SasEntree, faux sinon
     */
    public boolean estUneEntree() {
        return true;
    }

    /**
     * Méthode qui crée une version String de SasEntree
     * @return Une SasEntree sous forme de String
     */
    public String toString() {
        return "Entrée : " + super.toString();
    }
}