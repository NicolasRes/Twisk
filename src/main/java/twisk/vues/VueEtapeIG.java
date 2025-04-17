package twisk.vues;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

/**
 * Classe abstraite qui gère les étapes
 */
public abstract class VueEtapeIG extends VBox {
    private Label titre;
    private MondeIG monde;
    private EtapeIG etape;
    private Label icone;
    private HBox hbox;

    /**
     * Constructeur de la classe VueEtapeIG
     * @param monde Le monde
     * @param etape L'étape
     */
    public VueEtapeIG(MondeIG monde, EtapeIG etape) {
        assert(monde != null);
        assert(etape != null);

        this.monde = monde;
        this.etape = etape;
        this.titre = new Label(this.etape.getNom() + " " );
        this.icone = new Label();
        this.hbox = new HBox();

        // Configuration de la VBox en fonction du type d'activité
        if(this.etape.getType() == "Activite") {
            // Taille VBox activité
            this.setMinWidth(TailleComposants.LARGEUR_ETAPE);
            this.setMinHeight(TailleComposants.HAUTEUR_ETAPE);
            this.setMaxWidth(TailleComposants.LARGEUR_ETAPE + TailleComposants.PADDING_X + TailleComposants.PADDING_SPACING);
            this.setMaxHeight(TailleComposants.HAUTEUR_ETAPE);

            // Taille titre activité
            this.titre.setPrefWidth(TailleComposants.LARGEUR_ETAPE);

            // Style VBox activité
            this.getStyleClass().add("vbox");
        }
        else if(this.etape.getType() == "Guichet") {
            // Taille VBox guichet
            this.setMinWidth(TailleComposants.LARGEUR_GUICHET);
            this.setMinHeight(TailleComposants.HAUTEUR_GUICHET);
            this.setMaxWidth(TailleComposants.LARGEUR_GUICHET + TailleComposants.PADDING_X + TailleComposants.PADDING_SPACING);
            this.setMaxHeight(TailleComposants.HAUTEUR_GUICHET);

            // Taille titre guichet
            this.titre.setPrefWidth(TailleComposants.LARGEUR_GUICHET);

            // Style VBox guichet
            this.getStyleClass().add("vboxGuichet");
        }

        this.titre.setAlignment(Pos.CENTER);

        // Ajout des enfants
        this.hbox.getChildren().addAll(this.icone, this.titre);
        this.getChildren().add(this.hbox);

        this.setOnMouseClicked(event -> {
            this.etape.switchSelection();
            this.monde.etapesSelection(this.etape);
        });

        // Ajout style CSS
        this.titre.getStyleClass().add("Label");


        // Mise à jour dynamique des attributs
        updateCouleurSelection();
        updateIcone();

        // Déclenchement du drag'n drop
        this.setOnDragDetected(mouseEvent -> {
            final Dragboard dragBoard = this.startDragAndDrop(TransferMode.MOVE);
            final ClipboardContent content = new ClipboardContent();
            final WritableImage capture = this.snapshot(null, null);

            content.putString(this.etape.getIdentifiant());
            content.putImage(capture);

            dragBoard.setContent(content);
            dragBoard.setDragView(capture);
            mouseEvent.consume();
        });
    }

    /**
     * Méthode qui modifie la couleur de l'étape en fonction de son état de sélection
     */
    private void updateCouleurSelection() {
        if(this.etape.estSelection()) {
            this.getStyleClass().add("etapeSelection");
        }
        else {
            this.getStyleClass().remove("etapeSelection");
        }
    }

    /**
     * Méthode qui ajoute une icône à une étape en fonction de son type (entrée, sortie ou les deux)
     */
    private void updateIcone() {
        String chemin = null;

        if(this.etape.estEntree() && this.etape.estSortie()) {
            chemin = "/images/entry-exit.png";
        }
        else if(this.etape.estEntree()) {
            chemin = "/images/entrance.png";
        }
        else if(this.etape.estSortie()) {
            chemin = "/images/exit.png";
        }

        if(chemin != null) {
            Image imIcon = new Image(getClass().getResourceAsStream(chemin), 20, 20, true, true);
            ImageView iconeView = new ImageView(imIcon);

            this.icone.setGraphic(iconeView);
        }
    }
}