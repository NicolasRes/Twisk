package twisk.monde;

import twisk.outils.FabriqueNumero;

import java.util.Iterator;

/**
 * Classe abstraite Etape qui regroupe les différents types d'étapes
 */
public abstract class Etape implements Iterable<Etape> {
    private String nom;
    private GestionnaireEtapes etapes;
    private int idEtape;

    /**
     * Constructeur de la classe Etape avec nom comme seul paramètre
     * @param nom Le nom de l'étape
     */
    public Etape(String nom) {
        assert(nom != null) : "Erreur nom null";
        this.nom = nom;
        this.etapes = new GestionnaireEtapes();
        this.idEtape = FabriqueNumero.getInstance().getNumeroEtape();
    }

    /**
     * Méthode qui renvoie le numéro de l'étape
     * @return Le numéro de l'étape
     */
    public int getNumero() {
        return idEtape;
    }

    /**
     * Méthode qui permet l'ajout d'un nombre indéfini de succésseurs à une Etape
     * @param e Les étapes à ajouter
     */
    public void ajouterSuccesseur(Etape... e) {
        for(Etape etape : e){
            assert (etape != null) : "Erreur : Etape null";
            this.etapes.ajouterEtape(etape);
        }
    }

    /**
     * Méthode qui renvoie le nombre de successeurs d'une liste d'étapes
     * @return Le nombre de successeurs
     */
    public int nbSuccesseur() {
        return this.etapes.nbEtapes();
    }

    /**
     * Méthode qui renvoie un successeur à l'index indiqué
     * @param index L'index du successeur à renvoyer
     * @return Le successeur
     */
    public Etape getSuccesseur(int index) {
        return this.etapes.getEtape(index);
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
        return this.nom;
    }

    /**
     * Méthode qui renvoie un gestionnaire d'étapes
     * @return Le gestionnaire d'étapes
     */
    public GestionnaireEtapes getEtapes() {
        return this.etapes;
    }

    /**
     * Méthode abstraite toString de la classe Etape
     */
    public abstract String toString();
}