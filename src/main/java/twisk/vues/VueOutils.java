package twisk.vues;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import twisk.mondeIG.MondeIG;

import javafx.scene.control.Button;
import twisk.mondeIG.SimulationIG;

/**
 * Classe qui gère le bouton pour ajouter une activité
 */
public class VueOutils extends TilePane implements Observateur {
    private MondeIG monde;
    private SimulationIG simulation;
    private Button ajouterActivite, ajouterGuichet, simuler;

    /**
     * Constructeur de la classe VueOutils
     * @param monde Le monde dans lequel on crée les outils
     */
    public VueOutils(MondeIG monde, SimulationIG simulation) {
        assert(monde != null);

        this.monde = monde;
        this.monde.ajouterObservateur(this);

        this.simulation = simulation;

        // Création des boutons pour ajouter des activités / guichets
        createButtonAjouterActivite();
        createButtonAjouterGuichet();
        createButtonSimulation();

        this.ajouterActivite.setOnAction(actionEvent -> {this.monde.ajouter("Activité");});
        this.ajouterGuichet.setOnAction(e -> this.monde.ajouter("Guichet"));
        this.simuler.setOnAction(e -> this.simulation.simuler());

        this.getChildren().addAll(this.ajouterActivite, this.ajouterGuichet, this.simuler);

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
     * Méthode qui crée un bouton pour ajouter une activité
     */
    public void createButtonAjouterGuichet() {
        this.ajouterGuichet = new Button("");
        this.ajouterGuichet.setTooltip(new Tooltip("Ajoute un guichet"));
        Image imAjouterGuichet = new Image(getClass().getResourceAsStream("/images/addGui.png"), 50, 50, true, true);
        ImageView iconAjouterGuichet = new ImageView(imAjouterGuichet);
        this.ajouterGuichet.setGraphic(iconAjouterGuichet);
        this.ajouterGuichet.getStyleClass().add("buttonGuichet");   // Style différent pour le bouton du guichet
    }

    public void createButtonSimulation() {
        this.simuler = new Button("");
        this.simuler.setTooltip(new Tooltip("Simu"));
        Image imAjouterGuichet = new Image(getClass().getResourceAsStream("/images/addGui.png"), 50, 50, true, true);
        ImageView iconAjouterGuichet = new ImageView(imAjouterGuichet);
        this.simuler.setGraphic(iconAjouterGuichet);
        this.simuler.getStyleClass().add("buttonGuichet");   // Style différent pour le bouton du guichet
    }

    /**
     * Méthode qui met à jour le dessin des activités dans le monde
     */
    public void reagir() {

    }
}
