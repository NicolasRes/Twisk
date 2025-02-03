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
    public String toString() {
        return "Guichet \n" +
                "Nom du guichet : " + this.getNom() + "\n" +
                "Nombre de jetons : " + nbJetons + "\n";
    }
}