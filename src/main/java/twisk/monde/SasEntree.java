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

    public boolean estUneEntree() {
        return true;
    }
}