package twisk.vues;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import twisk.exceptions.TwiskJetonsException;
import twisk.exceptions.TwiskMenuException;
import twisk.mondeIG.MondeIG;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.util.Duration.seconds;


/**
 * Classe VueMenu qui gère un menu bar dans la fenêtre de l'application
 */
public class VueMenu extends MenuBar implements Observateur {
    private MondeIG monde;
    private Stage stage;
    private Menu fichier;
    private Menu edition;
    private Menu menuMonde;
    private Menu parametres;
    private Menu style;
    private MenuItem delai, ecart, renommer, jetons;

    /**
     * Constructeur de la classe VueMenu
     *
     * @param monde Le monde dans lequel se trouve le menu
     * @param stage Fenêtre du jeu
     */
    public VueMenu(MondeIG monde, Stage stage) throws IOException {
        this.monde = monde;
        this.stage = stage;
        this.monde.ajouterObservateur(this);

        this.fichier = new Menu("Fichier");
        this.edition = new Menu("Édition");
        this.menuMonde = new Menu("Monde");
        this.parametres = new Menu("Paramètres");
        this.style = new Menu("Style");

        this.getMenus().addAll(this.fichier, this.edition, this.menuMonde, this.parametres, this.style);

        MenuItem quitter = new MenuItem("Quitter");
        MenuItem supprimer = new MenuItem("Supprimer");
        this.renommer = new MenuItem("Renommer");
        MenuItem desactiverSelection = new MenuItem("Désactiver la sélection");
        MenuItem entree = new MenuItem("Entrée");
        MenuItem sortie = new MenuItem("Sortie");
        this.delai = new MenuItem("Délai");
        this.ecart = new MenuItem("Écart");
        this.jetons = new MenuItem("Jetons");
        MenuItem pastel = new MenuItem("Pastel");
        MenuItem vanille = new MenuItem("Vanille");

        initialiserImages(quitter, supprimer, this.renommer, desactiverSelection, entree, sortie, this.delai, this.ecart, this.jetons,
                pastel, vanille);
        initialiserEvenements(quitter, supprimer, this.renommer, desactiverSelection, entree, sortie, this.delai, this.ecart, this.jetons,
                pastel, vanille);

        updateMenuItems();
        supprimerFichierTemp();
    }

    /**
     * Méthode qui initialise les images relatives items du menu fichier
     *
     * @param quitter             Le MenuItem qui permet de quitter le jeu
     * @param supprimer           Le MenuItem qui permet de recommencer la partie
     * @param renommer            Le MenuItem qui permet de renommer une activité
     * @param desactiverSelection Le MenuItem qui permet de désactiver la sélection
     * @param entree              Le MenuItem qui permet de désigner les activités sélectionnées comme entrées du monde
     * @param sortie              Le MenuItem qui permet de désigner les activités sélectionnées comme sorties du monde
     * @param delai               Le MenuItem qui permet d'assigner un délai à une activité
     * @param ecart               Le MenuItem qui permet d'assigner un écart de temps à une activité
     * @param jetons              Le MenuItem qui permet de modifier le nombre de jetons d'un guichet
     * @param pastel              Le MenuItem qui permet de choisir le style pastel
     * @param vanille             Le MenuItem qui permet de choisir le style vanille
     */
    private void initialiserImages(MenuItem quitter, MenuItem supprimer, MenuItem renommer,
                                   MenuItem desactiverSelection, MenuItem entree, MenuItem sortie, MenuItem delai,
                                   MenuItem ecart, MenuItem jetons, MenuItem pastel, MenuItem vanille) {
        // Quitter
        Image imQuitter = new Image(getClass().getResourceAsStream("/images/leave.png"), 30, 30, true, true);
        quitter.setGraphic(new ImageView(imQuitter));

        // Supprimer
        Image imSupprimer = new Image(getClass().getResourceAsStream("/images/delete.png"), 30, 30, true, true);
        supprimer.setGraphic(new ImageView(imSupprimer));

        // Renommer
        Image imRenommer = new Image(getClass().getResourceAsStream("/images/rename.png"), 30, 30, true, true);
        renommer.setGraphic(new ImageView(imRenommer));

        // Désactiver la sélection
        Image imDesactiverSelection = new Image(getClass().getResourceAsStream("/images/deselect.png"), 30, 30, true, true);
        desactiverSelection.setGraphic(new ImageView(imDesactiverSelection));

        // Entrée
        Image imEntree = new Image(getClass().getResourceAsStream("/images/entrance.png"), 30, 30, true, true);
        entree.setGraphic(new ImageView(imEntree));

        // Sortie
        Image imSortie = new Image(getClass().getResourceAsStream("/images/exit.png"), 30, 30, true, true);
        sortie.setGraphic(new ImageView(imSortie));

        // Délai
        Image imDelai = new Image(getClass().getResourceAsStream("/images/delay.png"), 30, 30, true, true);
        delai.setGraphic(new ImageView(imDelai));

        // Écart
        Image imEcart = new Image(getClass().getResourceAsStream("/images/gap.png"), 30, 30, true, true);
        ecart.setGraphic(new ImageView(imEcart));

        // Jetons
        Image imJetons = new Image(getClass().getResourceAsStream("/images/jeton.png"), 30, 30, true, true);
        jetons.setGraphic(new ImageView(imJetons));

        // On ajoute les items aux menus
        this.fichier.getItems().add(quitter);
        this.edition.getItems().addAll(supprimer, renommer, desactiverSelection);
        this.menuMonde.getItems().addAll(entree, sortie);
        this.parametres.getItems().addAll(delai, ecart, jetons);
        this.style.getItems().addAll(pastel, vanille);
    }

    /**
     * Méthode qui initialise les évènements liés aux items du menu fichier
     *
     * @param quitter             Le MenuItem qui permet de quitter le jeu
     * @param supprimer           Le MenuItem qui permet de recommencer la partie
     * @param renommer            Le MenuItem qui permet de renommer une activité
     * @param desactiverSelection Le MenuItem qui permet de désactiver la sélection
     * @param entree              Le MenuItem qui permet de désigner les activités sélectionnées comme entrées du monde
     * @param sortie              Le MenuItem qui permet de désigner les activités sélectionnées comme sorties du monde
     * @param delai               Le MenuItem qui permet d'assigner un délai à une activité
     * @param ecart               Le MenuItem qui permet d'assigner un écart de temps à une activité
     * @param jetons              Le MenuItem qui permet de modifier le nombre de jetons d'un guichet
     */
    private void initialiserEvenements(MenuItem quitter, MenuItem supprimer, MenuItem renommer,
                                       MenuItem desactiverSelection, MenuItem entree, MenuItem sortie, MenuItem delai,
                                       MenuItem ecart, MenuItem jetons, MenuItem pastel, MenuItem vanille) {
        supprimer.setOnAction(e -> {
            this.monde.supprimerEtapesArcs();
        });

        quitter.setOnAction(e -> {
            Platform.exit();
        });

        renommer.setOnAction(e -> {
            String nouveauNom = dialogueRenommerActivite();
            if (nouveauNom == null || nouveauNom.isEmpty()) {
                return;
            }
            this.monde.renommerEtape(nouveauNom);
        });

        desactiverSelection.setOnAction(e -> {
            this.monde.deselectionnerTout();
        });

        entree.setOnAction(e -> {
            this.monde.choisirEntrees();
        });

        sortie.setOnAction(e -> {
            this.monde.choisirSorties();
        });

        delai.setOnAction(e -> {
            try {
                this.monde.definirDelai(dialogueDelaiEcart());
            } catch (TwiskMenuException exception) {
                afficherErreur(exception.getMessage());
            }
        });

        ecart.setOnAction(e -> {
            try {
                this.monde.definirEcart(dialogueDelaiEcart());
            } catch (TwiskMenuException exception) {
                afficherErreur(exception.getMessage());
            }
        });

        jetons.setOnAction(e -> {
            try {
                this.monde.definirJetons(dialogueJetons());
            } catch (TwiskJetonsException exception) {
                afficherErreur(exception.getMessage());
            }
        });

        pastel.setOnAction(e -> {
            appliquerStyle(this.stage.getScene(), "/css/pastel.css");
        });

        vanille.setOnAction(e -> {
            appliquerStyle(this.stage.getScene(), "/css/vanille.css");
        });
    }

    /**
     * Méthode menu dialogue qui attend un nouveau nom pour une activité de la part de l'utilisateur
     *
     * @return Le nouveau nom de l'activité
     */
    private String dialogueRenommerActivite() {
        TextInputDialog dialog = new TextInputDialog("Nouveau nom");
        dialog.setTitle("Renommer");
        dialog.setHeaderText("Renommer l'activité");
        dialog.setContentText("Entrez le nouveau nom de l'activité :");
        return dialog.showAndWait().orElse(null);   // Renvoie le texte entré ou null si action annulée
    }

    /**
     * Méthode menu dialogue que attend une valeur pour une activité
     *
     * @return La valeur en entier entrée par l'utilisateur
     */
    private int dialogueDelaiEcart() throws TwiskMenuException {
        TextInputDialog dialog = new TextInputDialog("5");
        dialog.setTitle("Définir valeur");
        dialog.setHeaderText("Choix de la valeur");
        dialog.setContentText("Entrez la valeur voulue :");

        Optional<String> choix = dialog.showAndWait(); // Optional : la variable contient la valeur entrée ou un objet vide si dialogue fermé / annulé

        if (choix.isPresent()) {
            String choixSansEspace = choix.get().trim();    // On enlève les espaces vides s'il y en a

            if (choixSansEspace.isEmpty()) {
                throw new TwiskMenuException("Aucune valeur saisie");
            }

            try {
                int val = Integer.parseInt(choixSansEspace);  // On convertit le résultat en entier
                if (val <= 0) {
                    throw new TwiskMenuException("La valeur doit être un entier positif");
                }
                return val; // Cas fonctionnel
            } catch (NumberFormatException exception) {
                throw new TwiskMenuException("La saisie ne peut pas être autre chose qu'un entier");
            }
        } else {
            throw new TwiskMenuException("Aucune saisie");
        }
    }

    /**
     * Méthode qui affiche une erreur lorsqu'on tente d'attribuer une valeur à une étape sans respecter les conditions requises
     */
    private void afficherErreur(String message) {
        Alert alert = new Alert(ERROR);
        alert.setTitle("Erreur Menu");
        alert.setHeaderText("Valeur entrée incorrecte");
        alert.setContentText(message);
        PauseTransition pause = new PauseTransition(seconds(3));
        pause.setOnFinished(event -> alert.close());
        pause.play();
        alert.showAndWait();
    }

    /**
     * Méthode menu dialogue qui attend un nouveau nombre de jetons pour un guichet
     *
     * @return Le nouveau nombre de jetons du guichet
     */
    private int dialogueJetons() {
        TextInputDialog dialog = new TextInputDialog("5");
        dialog.setTitle("Jetons");
        dialog.setHeaderText("Définir un nombre de jetons");
        dialog.setContentText("Entrez le nouveau nombre de jetons du guichet :");

        Optional<String> choix = dialog.showAndWait();

        if (choix.isPresent()) {
            String choixSansEspace = choix.get().trim();

            if (choixSansEspace.isEmpty()) {
                throw new TwiskJetonsException("Aucune valeur saisie");
            }

            try {
                int val = Integer.parseInt(choixSansEspace);
                if (val <= 0) {
                    throw new TwiskJetonsException("La valeur doit être un entier positif");
                }
                return val;
            } catch (NumberFormatException exception) {
                throw new TwiskJetonsException("La saisie ne peut pas être autre chose qu'un entier");
            }
        } else {
            throw new TwiskJetonsException("Aucune saisie");
        }
    }

    /**
     * Méthode qui offre l'accès aux menu items delai et ecart si une seule étape est sélectionnée
     */
    private void updateMenuItems() {
        if (!this.monde.uneEtapeSelectionnee()) {
            this.delai.setDisable(true);
            this.ecart.setDisable(true);
            this.renommer.setDisable(true);
            this.jetons.setDisable(true);
        } else {
            this.delai.setDisable(false);
            this.ecart.setDisable(false);
            this.renommer.setDisable(false);
            this.jetons.setDisable(false);
        }
    }

    /**
     * Méthode qui permet d'appliquer un style css à une scène
     *
     * @param scene     La scène sur laquelle appliquer le style
     * @param cheminCSS Le chemin d'accès à la feuille de style css
     */
    private void appliquerStyle(Scene scene, String cheminCSS) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource(cheminCSS).toExternalForm());
    }

    /**
     * Méthode qui met à jour l'affichage du menu dans le monde
     */
    public void reagir() {
        updateMenuItems();
    }

    private void supprimerFichierTemp() throws IOException {
        try {
            File index = new File("/tmp/twisk/");
            String[] entries = index.list();
            for (String s : entries) {
                File currentFile = new File(index.getPath(), s);
                currentFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}