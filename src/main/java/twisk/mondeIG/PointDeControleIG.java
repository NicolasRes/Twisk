package twisk.mondeIG;

import twisk.outils.FabriqueIdentifiant;

/**
 * Classe qui gère les points de contrôles liés aux étapes
 */
public class PointDeControleIG {
    private double x;
    private double y;
    private double relativeX;
    private double relativeY;
    private String identifiant;
    private EtapeIG etape;

    /**
     * Constructeur de la classe PointDeControleIG
     * @param relativeX Coordonnée x du centre du point de contrôle
     * @param relativeY Coordonnée x du centre du point de contrôle
     * @param etape Étape rattachée au point de contrôle
     */
    public PointDeControleIG(double relativeX, double relativeY, EtapeIG etape) {
        this.relativeX = relativeX;
        this.relativeY = relativeY;

        this.x = relativeX + etape.getPosX();
        this.y = relativeY + etape.getPosY();

        this.etape = etape;
        FabriqueIdentifiant id = FabriqueIdentifiant.getInstance();
        this.identifiant = id.getIdentifiantPDC();
    }

    /**
     * Méthode qui renvoie la position x d'un point de contrôle
     * @return La position x d'un point de contrôle
     */
    public double getX() {
        return this.x;
    }

    /**
     * Méthode qui renvoie la position y d'un point de contrôle
     * @return La position y d'un point de contrôle
     */
    public double getY() {
        return this.y;
    }

    /**
     * Méthode qui renvoie l'étape d'un point de contrôle
     * @return L'étape d'un point de contrôle
     */
    public EtapeIG getEtape() {
        return this.etape;
    }

    /**
     * Méthode qui permet de déplacer un point de contrôle
     * @param deplacementX Facteur de modification x
     * @param deplacementY Facteur de modification y
     */
    public void deplacer(double deplacementX, double deplacementY) {
        this.x += deplacementX;
        this.y += deplacementY;
    }

    /**
     * Méthode qui renvoie l'identifiant du PDC
     * @return L'ientifiant du PDC
     */
    public String getIdentifiant() {
        return this.identifiant;
    }

    /**
     * Méthode qui applique un identifiant au PDC
     * @param id L'identifiant à appliquer au PDC
     */
    public void setIdentifiant(String id) {
        this.identifiant = id;
    }

    /**
     * Méthode qui récupère la coordonnée x relative du PDC
     * @return La coordonnée x relative
     */
    public double getRelativeX() {
        return this.relativeX;
    }

    /**
     * Méthode qui récupère la coordonnée y relative du PDC
     * @return La coordonnée y relative
     */
    public double getRelativeY() {
        return this.relativeY;
    }

    /**
     * Méthode qui définit la coordonnée x relative du PDC
     * @param x coordonnée x relative
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Méthode qui définit la coordonnée y relative du PDC
     * @param y coordonnée y relative
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Méthode qui renvoie une version String d'un point de contrôle
     * @return Les informations d'un point de contrôle sous forme de String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PointDeControleIG [");
        sb.append("x = ").append(this.x).append(", ");
        sb.append("y = ").append(this.y).append(", ");
        sb.append("identifiant = ").append(this.identifiant).append(", ");
        sb.append("etape = ").append(this.etape).append("]");
        return sb.toString();
    }
}
