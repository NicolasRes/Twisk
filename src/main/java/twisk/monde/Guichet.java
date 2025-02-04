package twisk.monde;

/**
 * Sous-classe d'Etape de type Guichet
 */
public class Guichet extends Etape {
    private int nbJetons;

    /**
     * Constructeur de classe Guichet nom en seul paramètre
     * @param nom Le nom du guichet
     */
    public Guichet(String nom) {
        super(nom);
    }

    /**
     * Constructeur de la classe Guichet avec deux paramètres
     * @param nom Le nom du guichet
     * @param nbJetons Nombres de jetons dans le guichet
     */
    public Guichet(String nom, int nbJetons) {
        super(nom);
        this.nbJetons = nbJetons;
    }

    /**
     * Méthode qui renvoie détermine si une étape est de type guichet
     * @return Renvoie vrai pour une guichet, faux sinon
     */
    public boolean estUnGuichet() {
        return true;
    }

    /**
     * Méthode qui renvoie les informations du guichet sous forme de String
     * @return Le guichet sous forme de String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getNom()).append(" : ").append(this.nbSuccesseur()).append(" successeur -> ");

        for (Etape etape : this.getEtapes()) {
            sb.append(etape.getNom()).append(", ");
        }

        if (this.nbSuccesseur() > 0) {
            sb.setLength(sb.length() - 2);
        }

        return sb.toString();
    }
}