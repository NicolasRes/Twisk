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
}
