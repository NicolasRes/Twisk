package twisk.monde;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe de type GestionnaireEtapes qui gère les étapes
 */
public class GestionnaireEtapes implements Iterable<Etape> {
    private ArrayList<Etape> etapes;

    /**
     * Constructeur de la classe GestionnaireEtapes
     */
    public GestionnaireEtapes() {
        this.etapes = new ArrayList<Etape>();
    }

    /**
     * Méthode qui ajoute une étape au gestionnaire d'étapes
     * @param etape Les étapes à ajouter à la liste d'étapes
     */
    void ajouter(Etape ... etape) {
        for(Etape e : etape){
            //assert (e != null) : "Erreur : Etape null";
            this.etapes.add(e);
        }
    }

    /**
     * Méthode qui renvoie le nombre d'étapes dans le gestionnaire d'étapes
     * @return Le nombre d'étapes
     */
    public int nbEtapes() {
        return this.etapes.size();
    }

    /**
     * Méthode qui récupère une étape dans le gestionnaire d'étapes
     * @param index L'indice de l'étape à récupérer
     * @return L'étape à récupérer
     */
    public Etape getEtape(int index) {
        return this.etapes.get(index);
    }

    /**
     * Méthode qui permet d'itérer sur un gestionnaire d'étapes
     * @return L'itérateur de gestionnaire d'étapes
     */
    public Iterator<Etape> iterator() {
        return this.etapes.iterator();
    }
}
