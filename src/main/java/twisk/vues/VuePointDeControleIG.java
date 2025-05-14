package twisk.vues;

import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.scene.shape.Circle;
import twisk.exceptions.TwiskArcException;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;
import twisk.outils.TailleComposants;

/**
 * Classe qui gère l'affichage des points de contrôle des étapes
 */
public class VuePointDeControleIG extends Circle {
    private PointDeControleIG pointDeControle;
    private MondeIG monde;

    /**
     * Constructeur de VuePointDeControleIG
     * @param pointDeControle Le point de contrôle à afficher
     */
    public VuePointDeControleIG(PointDeControleIG pointDeControle, MondeIG monde) {
        super(pointDeControle.getX(), pointDeControle.getY(), TailleComposants.RAYON_PDC);

        this.pointDeControle = pointDeControle;
        this.monde = monde;

        this.setOnMouseClicked(event -> {
            try {
                this.monde.pointsSelectionne(this.pointDeControle);
            }
            catch(TwiskArcException e) {
                afficherErreur(e.getMessage());
            }
        });

        // Ajout du style CSS
        this.getStyleClass().add("circle");
        this.updateStyle();
    }

    /**
     * Méthode qui affiche une erreur lorsqu'on tente de créer un arc mais qu'il ne respecte pas les conditions requises
     */
    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur Arc");
        alert.setHeaderText("Impossible d'afficher l'arc");
        alert.setContentText(message);
        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(3));
        pause.setOnFinished(event -> alert.close());
        pause.play();
        alert.showAndWait();
    }

    /**
     * Méthode qui met à jour le visuel d'un point de contrôle lorsqu'il est sélectionné
     */
    public void updateStyle() {
        if(this.monde.estSelectionne(this.pointDeControle)) {
            if(!this.getStyleClass().contains("selected")) {
                this.getStyleClass().add("selected");
            }
        }
        else {
            this.getStyleClass().remove("selected");
        }
    }
}