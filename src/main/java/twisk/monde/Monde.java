package twisk.monde;

import java.util.Iterator;

/**
 * Classe Monde qui gère le monde du Twisk
 */
public class Monde implements Iterable<Etape> {
    private GestionnaireEtapes lesEtapes;
    private SasEntree entree;
    private SasSortie sortie;
    private int nbClients;
    private int[] tabJetonsGuichets;
    /**
     * Constructeur de la classe Monde
     */
    public Monde() {
        this.lesEtapes = new GestionnaireEtapes();
        this.entree = new SasEntree();
        this.sortie = new SasSortie();
        this.lesEtapes.ajouter(this.entree);
        this.lesEtapes.ajouter(this.sortie);
        this.tabJetonsGuichets = new int[1];
    }

    /**
     * Méthode qui définie l'entrée d'une liste d'étapes
     * @param etape Les étapes
     */
    public void aCommeEntree(Etape... etape) {
        for(Etape e : etape){
            assert (e != null) : "Erreur : Etape null";
            this.entree.ajouterSuccesseur(e);
        }
    }

    /**
     * Méthode qui définie la sortie d'une liste d'étapes
     * @param etape Les étapes
     */
    public void aCommeSortie(Etape... etape) {
        for(Etape e : etape){
            assert (e != null) : "Erreur : Etape null";
            e.ajouterSuccesseur(sortie);
        }
    }

    /**
     * Méthode qui ajoute un nombre indéfinie d'étapes au gestionnaire d'étapes
     * @param etape Les étapes à ajouter
     */
    public void ajouter(Etape... etape) {
        for(Etape e : etape){
            assert (e != null) : "Erreur : Etape null";
            this.lesEtapes.ajouter(e);

            if(e.estUnGuichet()){
                int numeroSemaphore = e.getNumeroSemaphore() -1;

                if (numeroSemaphore >= this.tabJetonsGuichets.length) {

                    int[] nouveauTab = new int[numeroSemaphore + 1];
                    System.arraycopy(this.tabJetonsGuichets, 0, nouveauTab, 0, this.tabJetonsGuichets.length);
                    this.tabJetonsGuichets = nouveauTab;
                }
                this.tabJetonsGuichets[numeroSemaphore] = e.getNbJetons();

                // System.out.println("Ajout de " + e.getNbJetons() + " Jetons sur la Semaphore " + e.getNumeroSemaphore());
            }

        }
    }

    /**
     * Méthode qui renvoie le nombre d'étapes du gestionnaire d'étapes
     * @return Le nombre d'étapes
     */
    public int nbEtapes() {
        return this.lesEtapes.nbEtapes();
    }

    /**
     * Méthode qui renvoie le nombre de guichets d'une liste d'étapes
     * @return Le nombre de guichets
     */
    public int nbGuichets() {
        int nbGuichets = 0;
        for(Etape e : this.lesEtapes){
            if(e.estUnGuichet()){
                nbGuichets++;
            }
        }
        return nbGuichets;
    }

    public int []getTabJetonsGuichets(){
        return tabJetonsGuichets;
    }

    /**
     * Méthode qui permet de récupérer l'Etape qui succède un sas d'entrée à l'indice i
     * @param i L'indice auquel récupérer l'entrée
     * @return Un successeur du sas d'entrée
     */
    public Etape getEntree(int i) {
        return this.entree.getSuccesseur(i);
    }

    /* À implanter pour tester aCommeSortie
    public Etape getSortie(int i) {
        return this.entree.getSuccesseur(i);
    }*/

    /**
     * Méthode qui rend un gestionnaire d'étapes itérable
     * @return Un itérateur sur les étapes
     */
    public Iterator<Etape> iterator() {
        return this.lesEtapes.iterator();
    }

    /**
     * Méthode qui renvoie une version String du monde
     * @return Une version String du monde
     */
    @Override
    public String toString() {
        int capacite = this.nbEtapes();
        StringBuilder sb = new StringBuilder(capacite);

        for(Etape e : this.lesEtapes) {
            sb.append(e.toString()).append("\n");
        }

        return sb.toString();
    }

    /**
     * Méthode qui renvoie le code C du parcours du monde par un client
     * @return Le parcours du monde d'un client sous forme de String
     */
    public String toC(){
        StringBuilder sb = new StringBuilder();

        sb.append("#include \"def.h\"\n");
        sb.append("#include \"client.h\"\n");
        sb.append("#include \"stdlib.h\"\n");
        sb.append("#include \"time.h\"\n\n");
        sb.append("#include <sys/types.h>\n\n");

        for(Etape e : this.lesEtapes){
            String nom = e.getNom();
            nom =replaceCarac(nom);

            sb.append("#define ").append(nom).append(" ").append(e.getNumero()).append("\n");

            if (e.estUnGuichet()){
                sb.append("#define SEM_").append(nom).append(" ").append(e.getNumeroSemaphore()).append("\n");
            }
        }

        sb.append("\n");

        sb.append("void simulation(int ids) {\n");
        sb.append(" entrer(Entree);\n");
        sb.append(this.entree.toC()+"\n");
        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Méthode qui récupère le nombre de jetons
     * @return Le nombre de jetons
     */
    public int nbClients(){
        return this.nbClients;
    }

    /**
     * Méthode qui définie le nombre de clients
     * @param nbClients Le nombre de clients à définir
     */
    public void setNbClients(int nbClients) {
        this.nbClients = nbClients;
    }

    /**
     * Méthode qui remplace les caractères spéciaux par les caractères correspondant sans accents
     * @param chaine La chaine de caractères sur laquelle appliquer les transformations
     * @return La chaine de caractères modifiée
     */
    public String replaceCarac(String chaine) {
        chaine = chaine.replace(" ", "_");
        chaine = chaine.replace("é", "e");
        chaine = chaine.replace("è", "e");
        chaine = chaine.replace("É", "E");
        chaine = chaine.replace("È", "E");
        chaine = chaine.replace("à", "a");
        chaine = chaine.replace("ù", "u");
        chaine = chaine.replace("î", "i");
        chaine = chaine.replace("ô", "o");
        chaine = chaine.replace("â", "a");
        chaine = chaine.replace("ç", "c");

        return chaine;
    }
}