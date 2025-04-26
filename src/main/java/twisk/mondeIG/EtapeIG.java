package twisk.mondeIG;

import twisk.outils.FabriqueIdentifiant;
import twisk.outils.TailleComposants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Classe du modèle qui décrit une étape graphique
 */
public abstract class EtapeIG implements Iterable<PointDeControleIG> {
    protected String nom;
    private int identifiant;
    private double posX;
    private double posY;
    private int largeur;
    private int hauteur;
    protected ArrayList<PointDeControleIG> pdc;
    private boolean selection;
    private boolean estEntree;
    private boolean estSortie;
    private ArrayList<EtapeIG> successeurs;
    private ArrayList<EtapeIG> predecesseurs;

    /**
     * Constructeur de la classe EtapeIG
     * @param nom Nom de l'étape
     * @param larg Largeur du rectangle
     * @param haut Hauteur du rectangle
     */
    public EtapeIG(String nom, int larg, int haut) {
        assert(nom != null);
        assert(larg >= 0);
        assert(haut >= 0);

        this.nom = nom;
        this.identifiant = FabriqueIdentifiant.getInstance().getIdentifiantEtape();
        this.largeur = larg;
        this.hauteur = haut;
        this.selection = false;
        this.estEntree = false;
        this.estSortie = false;
        this.predecesseurs = new ArrayList<>();
        this.successeurs = new ArrayList<>();

        Random rand = new Random();
        this.posX = rand.nextDouble(0, TailleComposants.LARGEUR_FENETRE - this.largeur - TailleComposants.PADDING_X - TailleComposants.PADDING_SPACING - TailleComposants.MARGE_X_SUP);
        this.posY = rand.nextDouble(0, TailleComposants.HAUTEUR_FENETRE - this.hauteur - TailleComposants.MARGE_BAS -TailleComposants.HAUTEUR_MENU);

        this.pdc = new ArrayList<>();
        this.pdc.add(new PointDeControleIG((larg + TailleComposants.PADDING_X)/2, -TailleComposants.PADDING_Y/2, this));   // Haut
        this.pdc.add(new PointDeControleIG(0, haut/2, this));   // Gauche
        this.pdc.add(new PointDeControleIG(larg + TailleComposants.PADDING_SPACING, haut/2, this));   // Droite
        this.pdc.add(new PointDeControleIG((larg + TailleComposants.PADDING_X)/2, haut - TailleComposants.PADDING_Y, this));   // Bas
    }

    /**
     * Méthode qui renvoie l'identifiant d'une étape
     * @return L'identifiant d'une étape
     */
    public int getIdentifiantEtape() {
        return this.identifiant;
    }

    /**
     * Méthode qui renvoie le nom d'une étape
     * @return Le nom d'une étape sous forme de String
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Méthode qui renvoie la position de l'étape sur l'axe des abscisses
     * @return L'axe x de l'étape
     */
    public double getPosX() {
        return this.posX;
    }

    /**
     * Méthode qui renvoie la position de l'étape sur l'axe des ordonnées
     * @return L'axe y de l'étape
     */
    public double getPosY() {
        return this.posY;
    }

    /**
     * Méthode qui modifie la position x d'une étape
     * @param x Le nouvel axe x de l'étape
     */
    public void setPosX(double x) {
        this.posX = x;
    }

    /**
     * Méthode qui modifie la position y d'une étape
     * @param y Le nouvel axe y de l'étape
     */
    public void setPosY(double y) {
        this.posY = y;
    }

    /**
     * Méthode qui permet d'itérer sur les points de contrôle
     * @return Un itérateur
     */
    public Iterator<PointDeControleIG> iterator() {
        return this.pdc.iterator();
    }

    /**
     * Méthode qui indique si une étape est sélectionnée
     * @return Vrai si l'étape est sélectionnée, faux sinon
     */
    public boolean estSelection() {
        return this.selection;
    }

    /**
     * Méthode qui permet d'inverser l'état de sélection de l'étape
     */
    public void switchSelection() {
        this.selection = !this.selection;
    }

    /**
     * Méthode qui indique si une étape est une entrée du monde
     * @return Vrai si l'étape est une entrée, faux sinon
     */
    public boolean estEntree() {
        return this.estEntree;
    }

    /**
     * Méthode qui permet d'inverser l'état d'entrée de l'étape
     */
    public void switchEntree() {
        this.estEntree = !this.estEntree;
    }

    /**
     * Méthode qui indique si une étape est une entrée du monde
     * @return Vrai si l'étape est une sortie, faux sinon
     */
    public boolean estSortie() {
        return this.estSortie;
    }

    /**
     * Méthode qui permet d'inverser l'état de sortie de l'étape
     */
    public void switchSortie() {
        this.estSortie = !this.estSortie;
    }

    /**
     * Méthode qui permet d'ajouter un successeur à la liste des successeurs de l'étape
     * @param e L'étape à ajouter en tant que successeur
     */
    public void ajouterSuccesseur(EtapeIG e) {
        this.successeurs.add(e);
    }

    /**
     * Méthode qui permet d'ajouter un prédécesseur à la liste des prédécesseurs de l'étape
     * @param e L'étape à ajouter en tant que prédécessseur
     */
    public void ajouterPredecesseur(EtapeIG e) {
        this.predecesseurs.add(e);
    }

    /**
     * Méthode qui permet de récupérer les successeurs de l'étape
     * @return La liste de successeurs de l'étape
     */
    public ArrayList<EtapeIG> getSuccesseurs() {
        return this.successeurs;
    }

    /**
     * Méthode qui permet de récupérer les prédécesseurs de l'étape
     * @return La liste de prédécesseurs de l'étape
     */
    public ArrayList<EtapeIG> getPredecesseurs() {
        return this.predecesseurs;
    }

    /**
     * Méthode qui renvoie le premier successeur d'une étape
     * @param e L'étape dont on veut le premier successeur
     * @return Le premier successeur de l'étape
     */
    public EtapeIG premierSuccesseur(EtapeIG e) {
        if(!e.getSuccesseurs().isEmpty()) {
            return e.getSuccesseurs().getFirst();
        }
        System.out.println("Pas de succ");
        return null;
    }

    /**
     * Méthode qui renvoie une version String d'une étape
     * @return Les informations d'une étape sous forme de String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("nom = ").append(this.nom).append(", ");
        sb.append("largeur = ").append(this.largeur).append(", ");
        sb.append("hauteur = ").append(this.hauteur).append(", ");
        sb.append("posX = ").append(this.posX).append(", ");
        sb.append("posY = ").append(this.posY);
        return sb.toString();
    }

    /// Méthodes abstraites ///

    /**
     * Méthode qui indique si une étape est une activité restreinte
     * @return Vrai si l'étape est une activité restreinte, Faux sinon
     */
    public abstract boolean estActiviteRestreinte();

    /**
     * Méthode abstraite qui renvoie le délai d'une étape
     * @return Le délai d'une étape
     */
    public abstract int getDelai();

    /**
     * Méthode abstraite qui renvoie l'écart d'une étape
     * @return L'écart d'une étape
     */
    public abstract int getEcart();

    /**
     * Méthode abstraite qui renvoie le nombre de jetons d'une étape
     * @return Le nombre de jetons d'une étape
     */
    public abstract int getNbJetons();

    /**
     * Méthode abstraite qui renvoie les attributs d'une étape
     * @return Les attributs d'une étape
     */
    public abstract String getAttributs();

    /**
     * Méthode qui renvoie l'identifiant de l'étape
     * @return L'identifiant de l'étape sous forme de String
     */
    public abstract String getIdentifiant();

    /**
     * Méthode qui modifie le nom d'une étape
     * @param nom Le nom choisi pour l'étape
     */
    public abstract void setNom(String nom);

    /**
     * Méthode abstraite qui renvoie le type d'étape
     */
    public abstract String getType();
}