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

        int nbSuccesseur = this.nbSuccesseur();

        String nom = this.getNom();
        nom = this.replaceCarac(nom);
        /*
        */
        sb.append("srand(getpid() * time(NULL));\n");
        sb.append("int nb").append(this.getNumero()).append("= (int) ((rand() / (float)RAND_MAX) * ").append(nbSuccesseur).append(");\n");
        sb.append("switch (nb").append(this.getNumero()).append("){ \n");

        for (int i = 0; i < nbSuccesseur; i++) {
            sb.append(" case ").append(i).append(":{\n");

            String type = this.getLoi(); //vérif ortho

            if(type.equals("uniforme")) {
                sb.append(" lois_unif(").append((this.temps)).append(", ").append(this.ecartTemps).append(");\n");
            } else if (type.equals("gaussienne")) {
                sb.append(" lois_gauss(").append((this.temps)).append(", ").append(this.ecartTemps).append(");\n");
            }

            String nomSuccesseur = this.getSuccesseur(i).getNom();
            nomSuccesseur =this.replaceCarac(nomSuccesseur);
            sb.append("    transfert(").append(nom).append(", ").append(nomSuccesseur).append(");\n");
            sb.append(this.getSuccesseur(i).toC());
            sb.append(" break;\n");
            sb.append("}\n");

        }
        sb.append("}\n");
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

    /**
     * Méthode qui renvoie le nombre de jetons
     * @return Le nombre de jetons
     */
    public int getNbJetons(){
        return 0;
    }
}