package twisk.vues;

import javafx.scene.layout.HBox;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

/**
 * Classe qui gère l'affichage des guichets
 */
public class VueGuichetIG extends VueEtapeIG {
    private HBox hbox;

    /**
     * Constructeur de la classe VueGuichetIG
     *
     * @param monde Le monde
     * @param etape L'étape
     */
    public VueGuichetIG(MondeIG monde, EtapeIG etape) {
        super(monde, etape);

        this.hbox = new HBox();
        this.hbox.setMinHeight(TailleComposants.HAUTEUR_MINI_GUI);

        this.getChildren().add(this.hbox);

        // Ajout style CSS
        this.hbox.getStyleClass().add("hboxGuichet");
    }
}