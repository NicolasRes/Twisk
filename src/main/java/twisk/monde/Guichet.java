package twisk.monde;

import twisk.outil.FabriqueNumero;

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

    /**
     * Méthode qui renvoie le parcours d'un client dans un guichet sous forme de String
     * @return Le parcours d'un client dans un guichet sous forme de String
     */
    public String toC(){
        StringBuilder sb = new StringBuilder();
        String nom = this.getNom();
        nom =this.replaceCarac(nom);

        String nomSuccesseur = this.getSuccesseur(0).getNom();
        nomSuccesseur =this.replaceCarac(nomSuccesseur);

        String nomSuccesseurSuccesseur = this.getSuccesseur(0).getSuccesseur(0).getNom();
        nomSuccesseurSuccesseur =this.replaceCarac(nomSuccesseurSuccesseur);


        sb.append(" P(ids,").append("SEM_").append(nom).append("); \n");

        sb.append(" transfert(").append(nom).append(", ").append(nomSuccesseur).append("); \n");

        sb.append(" delai(").append(this.getSuccesseur(0).getTemps()).append(", ").append(this.getSuccesseur(0).getEcartTemps()).append("); \n");

        sb.append(" V(ids,").append("SEM_").append(nom).append("); \n");

        sb.append(" transfert(").append(nomSuccesseur).append(", ").append(nomSuccesseurSuccesseur).append("); \n");

        sb.append(this.getSuccesseur(0).getSuccesseur(0).toC());

        return sb.toString();
    }

    /**
     * Méthode qui renvoie une erreur si on essaie d'accéder d'utiliser getTemps sur un guichet
     * @return Le code d'erreur -1
     */
    public int getTemps() {
        return -1;
    }

    /**
     * Méthode qui renvoie une erreur si on essaie d'accéder d'utiliser getEcartTemps sur un guichet
     * @return Le code d'erreur -1
     */
    public int getEcartTemps() {
        return -1;
    }

    /**
     * Méthode abstraite qui récupère le nombre de jetons
     * @return Le nombre de jetons
     */
    public int getNbJetons(){
        return nbJetons;
    }
}