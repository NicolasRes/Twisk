package twisk.monde;

/**
 * Sous-classe d'Activite de type SasSortie
 */
public class SasSortie extends Activite {
    public SasSortie() {
        super("Sortie");    // Nom par défaut
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
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sortie : ").append(this.nbSuccesseur()).append(" successeur(s)");
        for(Etape e : this.getEtapes()) {
            sb.append(" - ").append(e.getNom());
        }
        return sb.toString();
    }

    public String toC(){

        return "";
    }

}