package twisk.monde;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe abstraite Etape qui regroupe les différents types d'étapes
 */
public abstract class Etape implements Iterable<Etape> {
    private String nom;
    private ArrayList<Etape> etapes;

    /**
     * Constructeur de la classe Etape avec nom comme seul paramètre
     * @param nom Le nom de l'étape
     */
    public Etape(String nom) {
        assert(nom != null) : "Erreur nom null";

        this.nom = nom;
        this.etapes = new ArrayList<Etape>();
    }

    /**
     * Méthode qui permet l'ajout d'un nombre indéfini de succésseurs à une Etape
     * @param e Les étapes à ajouter
     */
    public void ajouterSuccesseur(Etape... e) {
        for(Etape etape : e){
            assert (etape != null) : "Erreur : Etape null";
            etapes.add(etape);
        }
    }

    /**
     * Méthode qui renvoie le nombre de successeurs d'une liste d'étapes
     * @return Le nombre de successeurs
     */
    public int nbSuccesseur() {
        return this.etapes.size();  // Sens à discuter. Ajouter successeurs indirects ?
    }

    public Etape getSuccesseur(int index) {
        return this.etapes.get(index);
    }

    /**
     * Méthode qui vérifie si une étape est de type Activite
     * @return Faux de base (redéfinie dans la sous-classe Activite)
     */
    public boolean estUneActivite() {
        return false;
    }

    /**
     * Méthode qui vérifie si une étape est de type Guichet
     * @return Faux de base (redéfinie dans la sous-classe Guichet)
     */
    public boolean estUnGuichet() {
        return false;
    }

    /**
     * Méthode qui permet d'itérer sur la classe Etape
     * @return Un itérateur sur les étapes
     */
    public Iterator<Etape> iterator() {
        return this.etapes.iterator();
    }

    /**
     * Méthode qui renvoie le nom d'une étape
     * @return Le nom d'une étape
     */
    public String getNom() {
        return nom;
    }

    /**
     * Méthode qui renvoie une liste d'étapes
     * @return La liste d'étapes
     */
    public ArrayList<Etape> getEtapes() {
        return etapes;
    }
}