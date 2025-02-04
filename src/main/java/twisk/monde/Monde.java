package twisk.monde;

import java.util.Iterator;

public class Monde implements Iterable<Etape> {
    private GestionnaireEtapes LesEtapes;
    private SasEntree entree;
    private SasSortie sortie;

    public Monde() {
        LesEtapes = new GestionnaireEtapes();
        this.entree = new SasEntree();
        this.sortie = new SasSortie();
    }

    public void aCommeEntree(Etape ... etape) { // À modifier
        for(Etape e : etape){
            assert (e != null) : "Erreur : Etape null";
            entree.ajouterSuccesseur(e);
        }
    }

    public void aCommeSortie(Etape ... etape) { // À modifier
        for(Etape e : etape){
            assert (e != null) : "Erreur : Etape null";
            sortie.ajouterSuccesseur(e);
        }
    }

    public void ajouter(Etape ... etape) {
        for(Etape e : etape){
            assert (e != null) : "Erreur : Etape null";
            LesEtapes.ajouterEtape(e);
        }
    }

    public int nbEtapes() {
        return LesEtapes.nbEtapes();
    }

    public int nbGuichets() {
        int nbGuichets = 0;
        for(Etape e : LesEtapes){
            if(e.estUnGuichet()){
                nbGuichets++;
            }
        }
        return nbGuichets;
    }

    public Iterator<Etape> iterator() {
        return LesEtapes.iterator();
    }

    @Override
    public String toString() {
        int capacite = this.nbEtapes();
        StringBuilder sb = new StringBuilder(capacite);
        sb.append(this.entree.toString()).append("\n");
        sb.append(this.sortie.toString()).append("\n");
        for(Etape e : LesEtapes) {
            sb.append(e.toString()).append("\n");
        }
        return sb.toString();
    }
}