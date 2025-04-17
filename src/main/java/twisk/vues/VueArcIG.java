package twisk.vues;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

/**
 * Classe qui affiche les ArcIG
 */
public class VueArcIG extends Pane {
    private Line ligne;
    private Polyline triangle;
    private ArcIG arc;
    private MondeIG monde;

    /**
     * Constructeur de la classe VueArcIG
     * @param arc Un ArcIG
     */
    public VueArcIG(ArcIG arc, MondeIG monde) {
        super();
        assert(arc != null);

        this.arc = arc;
        this.monde = monde;

        double arcDebutX = arc.getDebutX() + TailleComposants.DECAL_ARC;
        double arcDebutY = arc.getDebutY() + TailleComposants.DECAL_ARC;
        double arcFinX = arc.getFinX() + TailleComposants.DECAL_ARC;
        double arcFinY = arc.getFinY() + TailleComposants.DECAL_ARC;

        this.ligne = new Line(arcDebutX, arcDebutY, arcFinX, arcFinY);
        this.ligne.getStyleClass().add("line");

        creerTeteFleche(arcDebutX, arcDebutY, arcFinX, arcFinY);

        this.getChildren().addAll(this.ligne, this.triangle);

        this.setOnMouseClicked(event -> {
            this.arc.switchSelection();
            this.monde.arcsSelection(this.arc);
        });

        this.setPickOnBounds(false);    // Permet de réduire la "hitbox" à la ligne et non à son pane

        updateCouleurSelection();
    }

    /**
     * Méthode qui crée la tête des arc (triangle)
     * @param debutX Coordonnée X du point de départ de l'arc
     * @param debutY Coordonnée Y du point de départ de l'arc
     * @param finX Coordonnée X du point d'arrivée de l'arc
     * @param finY Coordonnée Y du point d'arrivée de l'arc
     */
    public void creerTeteFleche(double debutX, double debutY, double finX, double finY) {
        // Coordonnées du triangle
        double angle = Math.atan2(finY - debutY, finX - debutX);
        double longFleche = TailleComposants.LONG_FLECHE;
        double angleFleche = TailleComposants.ANGLE_FLECHE;

        // Points du triangle
        double x1 = finX - longFleche * Math.cos(angle - angleFleche);
        double y1 = finY - longFleche * Math.sin(angle - angleFleche);
        double x2 = finX - longFleche * Math.cos(angle + angleFleche);
        double y2 = finY - longFleche * Math.sin(angle + angleFleche);

        this.triangle = new Polyline();
        this.triangle.getPoints().addAll(x1, y1, x2, y2, finX, finY);
        this.triangle.getStyleClass().add("polyline");
    }

    /**
     * Méthode qui modifie la couleur de l'arc en fonction de son état de sélection
    */
    private void updateCouleurSelection() {
        if(this.arc.estSelection()) {
            this.ligne.getStyleClass().add("lineSelection");
            this.triangle.getStyleClass().add("polylineSelection");
        }
        else {
            this.ligne.getStyleClass().remove("lineSelection");
            this.triangle.getStyleClass().remove("polylineSelection");
        }
    }
}