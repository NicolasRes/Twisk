package twisk.monde;

import java.util.ArrayList;
import java.util.Iterator;

public class GestionnaireEtapes implements Iterable<Etape> {

    private ArrayList<Etape> etapes;

    public GestionnaireEtapes() {
        etapes = new ArrayList<Etape>();
    }

    void ajouterEtape(Etape etape) {
        etapes.add(etape);
    }

    public int nbEtapes() {
        return etapes.size();
    }

    public Iterator<Etape> iterator() {
        return etapes.iterator();
    }

}
