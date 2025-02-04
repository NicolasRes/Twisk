package twisk.monde;

import java.util.Iterator;

/**
 * Classe Monde qui gère le monde du Twisk
 */
public class Monde implements Iterable<Etape> {
    private GestionnaireEtapes LesEtapes;
    private SasEntree entree;
    private SasSortie sortie;

    /**
     * Constructeur de la classe Monde
     */
    public Monde() {
        this.LesEtapes = new GestionnaireEtapes();
        this.entree = new SasEntree();
        this.sortie = new SasSortie();
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
            e.ajouterSuccesseur(this.sortie);
        }
    }

    /**
     * Méthode qui ajoute un nombre indéfinie d'étapes au gestionnaire d'étapes
     * @param etape Les étapes à ajouter
     */
    public void ajouter(Etape... etape) {
        for(Etape e : etape){
            assert (e != null) : "Erreur : Etape null";
            this.LesEtapes.ajouterEtape(e);
        }
    }

    /**
     * Méthode qui renvoie le nombre d'étapes du gestionnaire d'étapes
     * @return Le nombre d'étapes
     */
    public int nbEtapes() {
        return this.LesEtapes.nbEtapes();
    }

    /**
     * Méthode qui renvoie le nombre de guichets d'une liste d'étapes
     * @return Le nombre de guichets
     */
    public int nbGuichets() {
        int nbGuichets = 0;
        for(Etape e : this.LesEtapes){
            if(e.estUnGuichet()){
                nbGuichets++;
            }
        }
        return nbGuichets;
    }

    /**
     * Méthode qui rend un gestionnaire d'étapes itérable
     * @return Un itérateur sur les étapes
     */
    public Iterator<Etape> iterator() {
        return this.LesEtapes.iterator();
    }

    /**
     * Méthode qui renvoie une version String du monde
     * @return Une version String du monde
     */
    @Override
    public String toString() {
        int capacite = this.nbEtapes();
        StringBuilder sb = new StringBuilder(capacite);
        sb.append(this.entree.toString()).append("\n");
        sb.append(this.sortie.toString()).append("\n");
        for(Etape e : this.LesEtapes) {
            sb.append(e.toString()).append("\n");
        }
        return sb.toString();
    }
}