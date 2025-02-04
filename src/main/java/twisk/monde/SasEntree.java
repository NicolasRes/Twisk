package twisk.monde;

/**
 * Sous-classe d'Activite de type SasEntree
 */
public class SasEntree extends Activite {
    /**
     * Constructeur de la classe SasEntree
     */
    public SasEntree() {
        super("Entrée");    // Nom par défaut
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
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Entrée : ").append(this.nbSuccesseur()).append(" successeur");
        for(Etape e : this.getEtapes()) {
            sb.append(" - ").append(e.getNom());
        }
        return sb.toString();
    }
}