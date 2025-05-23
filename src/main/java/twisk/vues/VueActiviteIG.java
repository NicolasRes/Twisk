package twisk.vues;

import javafx.scene.layout.HBox;
import twisk.mondeIG.ClientIG;
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

    /**
     * Méthode qui ajoute les clients dans la HBox de l'activité
     * @param client Le client à ajouter à l'activité
     */
    public void ajouterClientHBox(ClientIG client) {
        VueClientIG vueClient = new VueClientIG(client);
        this.hbox.getChildren().add(vueClient);
    }
}