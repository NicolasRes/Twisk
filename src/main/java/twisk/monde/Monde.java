package twisk.monde;

import java.util.Iterator;

public class Monde implements Iterable<Etape> {
    private GestionnaireEtapes LesEtapes;
    private SasSortie sortie;
    private SasEntree entree;

    public Monde() {
        LesEtapes = new GestionnaireEtapes();
    }

    public void aCommeEntree(Etape ... etape) {
        for(Etape e : etape){
            assert (e != null) : "Erreur : Etape null";
            SasEntree sasEntree = new SasEntree();
            this.entree = sasEntree;
        }
    }

    public void aCommeSortie(Etape ... etape) {
        for(Etape e : etape){
            assert (e != null) : "Erreur : Etape null";
            SasSortie sasSortie = new SasSortie();
            this.sortie = sasSortie;
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
