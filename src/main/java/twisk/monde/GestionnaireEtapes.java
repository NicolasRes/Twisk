package twisk.monde;

import java.util.ArrayList;
import java.util.Iterator;

public class GestionnaireEtapes implements Iterable<Etape> {

    private ArrayList<Etape> etapes;

    public GestionnaireEtapes() {
        etapes = new ArrayList<Etape>();
    }

    void ajouterEtape(Etape ... etape) {
        for(Etape e : etape){
            assert (e != null) : "Erreur : Etape null";
            etapes.add(e);
        }
    }

    public int nbEtapes() {
        return etapes.size();
    }

    public Etape getEtape(int index) {
        return etapes.get(index);
    }

    public Iterator<Etape> iterator() {
        return etapes.iterator();
    }

}
