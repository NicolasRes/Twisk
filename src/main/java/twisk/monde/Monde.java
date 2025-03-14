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
        this.lesEtapes.ajouterEtape(this.entree);
        this.lesEtapes.ajouterEtape(this.sortie);
        this.nbClients = 6;
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
            this.lesEtapes.ajouterEtape(e);

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
        sb.append("#include \"client.h\"\n\n");

        for(Etape e : this.lesEtapes){
            sb.append("#define ").append(e.getNom()).append(" ").append(e.getNumero()).append("\n");

            if (e.estUnGuichet()){
                sb.append("#define SEM_").append(e.getNom()).append(" ").append(e.getNumeroSemaphore()).append("\n");

            }
        }

        sb.append("\n");

        sb.append("void simulation(int ids) {\n");
        sb.append(this.entree.toC()+"\n");
        sb.append("}\n");

        /*
        sb.append("int get_nb_etapes(){\n");
        sb.append(" return "+this.nbEtapes()+";\n");
        sb.append("}\n");

        sb.append("int get_nb_guichets(){\n");
        sb.append(" return "+this.nbGuichets()+";\n");
        sb.append("}\n");

        sb.append("int get_nb_clients(){\n");
        sb.append(" return "+6+";\n");
        sb.append("}\n");

        sb.append("int getTabJetonsGuichets() []{\n");
        sb.append("return tabjetons;\n");
        sb.append("}\n");

         */


        return sb.toString();
    }


    public int nbClients(){
        return this.nbClients;
    }
}