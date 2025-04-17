package twisk.vues;

import javafx.scene.layout.HBox;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

/**
 * Classe qui gère l'affichage des activités graphiques
 */
public class VueActiviteIG extends VueEtapeIG {
    private HBox hbox;

    /**
     * Constructeur de la classe VueActivitéIG
     * @param monde Le monde
     * @param etape L'étape
     */
    public VueActiviteIG(MondeIG monde, EtapeIG etape) {
        super(monde, etape);

        this.hbox = new HBox();
        this.hbox.setMinHeight(TailleComposants.HAUTEUR_MINI_ACT);

        this.getChildren().add(this.hbox);

        // Ajout style CSS
        this.hbox.getStyleClass().add("hbox");
    }
}