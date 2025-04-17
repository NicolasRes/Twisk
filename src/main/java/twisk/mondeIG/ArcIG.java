package twisk.mondeIG;

/**
 * Classe qui gère un arc partant d'un point de contrôle à l'autre
 */
public class ArcIG {
    private PointDeControleIG source, dest;
    private boolean selection;

    /**
     * Constructeur de la classe ArcIG
     * @param source Point de contrôle de départ de l'arc
     * @param dest Point de contrôle d'arrivée de l'arc
     */
    public ArcIG(PointDeControleIG source, PointDeControleIG dest) {
        this.source = source;
        this.dest = dest;
        this.selection = false;
    }

    /**
     * Getter pour obtenir la coordonnée X du point de départ de l'arc
     *
     * @return La coordonnée X du point de contrôle de départ
     */
    public double getDebutX() {
        return this.source.getX();
    }

    /**
     * Getter pour obtenir la coordonnée Y du point de départ de l'arc
     *
     * @return La coordonnée Y du point de contrôle de départ
     */
    public double getDebutY() {
        return this.source.getY();
    }

    /**
     * Getter pour obtenir la coordonnée X du point d'arrivée de l'arc
     *
     * @return La coordonnée X du point de contrôle d'arrivée
     */
    public double getFinX() {
        return this.dest.getX();
    }

    /**
     * Getter pour obtenir la coordonnée Y du point d'arrivée de l'arc
     *
     * @return La coordonnée Y du point de contrôle d'arrivée
     */
    public double getFinY() {
        return this.dest.getY();
    }

    /**
     * Méthode qui renvoie le point de départ d'un arc
     * @return Le point de départ d'un arc
     */
    public PointDeControleIG getSource() {
        return this.source;
    }

    /**
     * Méthode qui renvoie le point de destination d'un arc
     * @return Le point de destination d'un arc
     */
    public PointDeControleIG getDestination() {
        return this.dest;
    }

    /**
     * Méthode qui donne l'état de sélection d'un arc
     * @return Vrai si l'arc est sélectionné, faux sinon
    */
    public boolean estSelection() {
        return this.selection;
    }

    /**
     * Méthode qui permet d'inverser l'état de sélection d'un arc
    */
    public void switchSelection() {
        this.selection = !this.selection;
    }

    /**
     * Méthode qui renvoie une version String d'un arc
     * @return Les informations d'un arc sous forme de String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ArcIG ===\n");
        sb.append("Départ : ").append(this.source).append("\n");
        sb.append("Arrivée : ").append(this.dest).append("\n");
        sb.append("================");
        return sb.toString();
    }
}