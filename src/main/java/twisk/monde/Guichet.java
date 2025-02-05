package twisk.monde;

import twisk.outils.FabriqueNumero;

/**
 * Sous-classe d'Etape de type Guichet
 */
public class Guichet extends Etape {
    private int nbJetons;
    private int numeroSemaphore;

    /**
     * Constructeur de classe Guichet nom en seul paramètre
     * @param nom Le nom du guichet
     */
    public Guichet(String nom) {
        super(nom);
        this.numeroSemaphore = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    /**
     * Constructeur de la classe Guichet avec deux paramètres
     * @param nom Le nom du guichet
     * @param nbJetons Nombre de jetons dans le guichet
     */
    public Guichet(String nom, int nbJetons) {
        super(nom);
        this.nbJetons = nbJetons;
        this.numeroSemaphore = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    /**
     * Méthode qui détermine si une étape est de type guichet
     * @return Renvoie vrai pour un guichet, faux sinon
     */
    public boolean estUnGuichet() {
        return true;
    }

    /**
     * Méthode qui renvoie le numéro de semaphore associé au guichet
     * @return Le numéro de semaphore
     */
    public int getNumeroSemaphore() {
        return numeroSemaphore;
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