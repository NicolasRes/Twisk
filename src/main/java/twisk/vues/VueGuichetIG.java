package twisk.vues;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import twisk.mondeIG.ClientIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.GuichetIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

import java.util.ArrayList;

/**
 * Classe qui gère l'affichage des guichets
 */
public class VueGuichetIG extends VueEtapeIG {
    private HBox hboxClients;
    private ArrayList<StackPane> casesClients;
    private GuichetIG guichet;

    /**
     * Constructeur de la classe VueGuichetIG
     * @param monde Le monde
     * @param etape L'étape
     */
    public VueGuichetIG(MondeIG monde, EtapeIG etape) {
        super(monde, etape);

        this.guichet = (GuichetIG) etape;
        this.hboxClients = new HBox();
        this.hboxClients.setMinHeight(TailleComposants.HAUTEUR_MINI_GUI);
        this.casesClients = new ArrayList<>();

        this.initialiserCases();
        this.getChildren().add(this.hboxClients);

        // Ajout style CSS
        this.hboxClients.getStyleClass().add("hboxGuichet");
    }

    /**
     * Méthode qui initialise les cases régulières à l'intérieur de la HBox du guichet
     */
    private void initialiserCases() {
        this.hboxClients.setSpacing(5);
        int nbCases = 6;

        for (int i = 0; i < nbCases; i++) {
            StackPane casePane = new StackPane();
            casePane.getStyleClass().add("caseClient");

            // Styles différents selon la case (pour les arrondis)
            if(i == 0) {
                casePane.getStyleClass().add("caseClient-gauche");
            }
            else if(i == nbCases - 1) {
                casePane.getStyleClass().add("caseClient-droite");
            }
            else {
                casePane.getStyleClass().add("caseClient-milieu");
            }

            HBox.setHgrow(casePane, Priority.ALWAYS);   // les cases prennent une part équitable dans l'espace horizontal dispo

            this.casesClients.add(casePane);
            this.hboxClients.getChildren().add(casePane);
        }
    }
}