package twisk.vues;

import javafx.scene.shape.Circle;
import twisk.mondeIG.ClientIG;

/**
 * Classe VueClientIG qui donne une représentation circulaire aux clients dans l'interface graphique
 */
public class VueClientIG extends Circle {

    /**
     * Constructeur de la classe VueClientIG
     * @param client Un client
     */
    public VueClientIG(ClientIG client) {
        super(5);
        this.setCenterX(client.getX());
        this.setCenterY(client.getY());
        this.getStyleClass().add("client");
        this.getStyleClass().add("client-couleur-" + client.getCouleurClient());
    }
}

