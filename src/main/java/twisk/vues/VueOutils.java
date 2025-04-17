package twisk.vues;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import twisk.mondeIG.MondeIG;

import javafx.scene.control.Button;

/**
 * Classe qui gère le bouton pour ajouter une activité
 */
public class VueOutils extends TilePane implements Observateur {
    private MondeIG monde;
    private Button ajouterActivite;

    /**
     * Constructeur de la classe VueOutils
     * @param monde Le monde dans lequel on crée les outils
     */
    public VueOutils(MondeIG monde) {
        assert(monde != null);

        this.monde = monde;
        this.monde.ajouterObservateur(this);

        // Création du bouton pour ajouter une activité
        createButtonAjouterActivite();

        this.ajouterActivite.setOnAction(actionEvent -> {
            this.monde.ajouter("Activité");
        });

        this.getChildren().add(this.ajouterActivite);

        // Ajout style CSS
        this.getStyleClass().add("TilePane");
    }

    /**
     * Méthode qui crée un bouton pour ajouter une activité
     */
    public void createButtonAjouterActivite() {
        this.ajouterActivite = new Button("");
        this.ajouterActivite.setTooltip(new Tooltip("Ajoute une activité"));
        Image imAjouterActivite = new Image(getClass().getResourceAsStream("/images/add.png"), 50, 50, true, true);
        ImageView iconAjouterActivite = new ImageView(imAjouterActivite);
        this.ajouterActivite.setGraphic(iconAjouterActivite);
    }

    /**
     * Méthode qui met à jour le dessin des activités dans le monde
     */
    public void reagir() {

    }
}
