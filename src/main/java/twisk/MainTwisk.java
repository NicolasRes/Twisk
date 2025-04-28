package twisk;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.SimulationIG;
import twisk.outils.TailleComposants;
import twisk.vues.VueMenu;
import twisk.vues.VueMondeIG;
import twisk.vues.VueOutils;

import java.io.IOException;

/**
 * Classe principale de l'application Twisk
 */
public class MainTwisk extends Application {

    /**
     * Méthode qui initialise l'interface graphique
     * @param primaryStage La fenêtre principale de l'application
     */
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("TwiskIG");
        MondeIG monde = new MondeIG();
        BorderPane root = new BorderPane();
        primaryStage.setScene(new Scene(root, TailleComposants.HAUTEUR_FENETRE - TailleComposants.HAUTEUR_MENU, TailleComposants.LARGEUR_FENETRE));

        VueOutils vueOutils = new VueOutils(monde, new SimulationIG(monde));
        VueMondeIG vueMonde = new VueMondeIG(monde);

        VueMenu menuJeu = new VueMenu(monde, primaryStage);

        root.setBottom(vueOutils);
        root.setCenter(vueMonde);
        root.setTop(menuJeu);

        // Feuille de style CSS
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/css/pastel.css").toExternalForm());

        primaryStage.setScene(primaryStage.getScene());

        primaryStage.show();
    }

    /**
     * Méthode qui démarre l'application
     * @param args Les arguments
     */
    public static void main(String[] args) {launch(args);}
}