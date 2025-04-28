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
            this.etapes.ajouter(etape);
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
     * Méthode qui retourne le numéro de sémaphore associé à l'étape
     * @return Le numéro de sémaphore
     */
    public int getNumeroSemaphore(){
        return 0;
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

    /**
     * Méthode abstraite qui renvoie le code C du parcours d'une étape par un client
     * @return Le parcours du monde d'un client sous forme de String
     */
    public abstract String toC();

    /**
     * Méthode abstraite qui renvoie la durée d'une étape
     * @return La durée d'une étape
     */
    public abstract int getTemps();

    /**
     * Méthode abstraite qui renvoie la partie variable de la durée d'une étape
     * @return La partie variable de la durée d'une étape
     */
    public abstract int getEcartTemps();

    /**
     * Méthode abstraite qui récupère le nombre de jetons
     * @return Le nombre de jetons
     */
    public abstract int getNbJetons();

    /**
     * Méthode qui remplace les caractères spéciaux par les caractères correspondant sans accents
     * @param chaine La chaine de caractères sur laquelle appliquer les transformations
     * @return La chaine de caractères modifiée
     */
    public String replaceCarac(String chaine) {
        chaine = chaine.replace(" ", "_");

        chaine = chaine.replace("é", "e");
        chaine = chaine.replace("è", "e");
        chaine = chaine.replace("É", "E");
        chaine = chaine.replace("È", "E");
        chaine = chaine.replace("à", "a");
        chaine = chaine.replace("ù", "u");
        chaine = chaine.replace("î", "i");
        chaine = chaine.replace("ô", "o");
        chaine = chaine.replace("â", "a");
        chaine = chaine.replace("ç", "c");

        return chaine;
    }
}