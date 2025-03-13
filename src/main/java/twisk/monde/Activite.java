package twisk.monde;

/**
 * Sous-classe d'Etape de type Activite
 */
public class Activite extends Etape {
    private int temps;
    private int ecartTemps;

    /**
     * Constructeur de la classe Activite avec nom pour seul paramètre
     * @param nom Le nom de l'activité
     */
    public Activite(String nom) {
        super(nom);
        this.temps = 3;
        this.ecartTemps = 1;
    }

    /**
     * Constructeur de la classe Activite avec multiples paramètres
     * @param nom Le nom de l'activité
     * @param temps Le temps qu'on passe dans l'activité
     * @param ecartTemps L'écart de temps possible dans l'activité
     */
    public Activite(String nom, int temps, int ecartTemps) {
        super(nom);
        this.temps = temps;
        this.ecartTemps = ecartTemps;
    }

    /**
     * Méthode qui renvoie détermine si une étape est de type activité
     * @return Renvoie vrai pour une activité, faux sinon
     */
    public boolean estUneActivite() {
        return true;
    }

    /**
     * Méthode qui vérifie si une activité est une entrée
     * @return Faux de base, vrai si l'activité est une entrée
     */
    public boolean estUneEntree() {
        return false;
    }

    /**
     * Méthode qui vérifie si une activité est une sortie
     * @return Faux de base, vrai si l'activité est une sortie
     */
    public boolean estUneSortie() {
        return false;
    }

    /**
     * Méthode qui crée une version String d'une étape de type Activite
     * @return Une activité sous forme de String
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
     * Méthode qui renvoie le parcours d'un client dans une activité
     * @return Le parcours d'un client dans une activité sous forme de String
     */
    public String toC(){
        StringBuilder sb = new StringBuilder();

        sb.append("delai(").append(this.temps).append(", ").append(this.ecartTemps).append("); \n");
        sb.append("transfert(").append(getNom()).append(", ").append(this.getSuccesseur(0).getNom()).append(");\n");
        sb.append(this.getSuccesseur(0).toC());

        return sb.toString();
    }

    /**
     * Méthode qui renvoie la durée d'une activité
     * @return La durée d'une activité
     */
    public int getTemps() {
        return this.temps;
    }

    /**
     * Méthode qui renvoie la partie variable de la durée d'une activité
     * @return La partie variable de la durée d'une activité
     */
    public int getEcartTemps() {
        return this.ecartTemps;
    }

    public int getNbJetons(){
        return 0;
    }
}