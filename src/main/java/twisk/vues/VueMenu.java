package twisk.vues;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import twisk.exceptions.TwiskJetonsException;
import twisk.exceptions.TwiskMenuException;
import twisk.mondeIG.MondeIG;
import twisk.sauvegarde.GestionnaireSauvegarde;

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
    private MenuItem delai, ecart, renommer, jetons, sauvegarder, charger, lois,nbClients;

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

        MenuItem sauvegarder = new MenuItem("Sauvegarder");
        MenuItem charger = new MenuItem("Charger");
        MenuItem quitter = new MenuItem("Quitter");
        MenuItem supprimer = new MenuItem("Supprimer");
        this.renommer = new MenuItem("Renommer");
        MenuItem desactiverSelection = new MenuItem("Désactiver la sélection");
        MenuItem entree = new MenuItem("Entrée");
        MenuItem sortie = new MenuItem("Sortie");
        this.delai = new MenuItem("Délai");
        this.ecart = new MenuItem("Écart");
        this.jetons = new MenuItem("Jetons");
        this.lois = new MenuItem("Lois");
        this.nbClients = new MenuItem("Nombre de clients");
        MenuItem pastel = new MenuItem("Pastel");
        MenuItem vanille = new MenuItem("Vanille");

        initialiserImages(sauvegarder, charger, quitter, supprimer, this.renommer, desactiverSelection, entree, sortie, this.delai, this.ecart, this.jetons, this.lois,nbClients,
                pastel, vanille);
        initialiserEvenements(sauvegarder, charger, quitter, supprimer, this.renommer, desactiverSelection, entree, sortie, this.delai, this.ecart, this.jetons, this.lois,nbClients ,
                pastel, vanille);

        updateMenuItems();
        supprimerFichierTemp();
    }

    /**
     * Méthode qui initialise les images relatives items du menu fichier
     *
     * @param sauvegarder         Le MenuItem qui permet de sauvegarder une configuration du monde
     * @param charger             Le MenuItem qui permet de charger une configuration du monde
     * @param quitter             Le MenuItem qui permet de quitter l'application
     * @param supprimer           Le MenuItem qui permet de supprimer la sélection
     * @param renommer            Le MenuItem qui permet de renommer une activité
     * @param desactiverSelection Le MenuItem qui permet de désactiver la sélection
     * @param entree              Le MenuItem qui permet de désigner les activités sélectionnées comme entrées du monde
     * @param sortie              Le MenuItem qui permet de désigner les activités sélectionnées comme sorties du monde
     * @param delai               Le MenuItem qui permet d'assigner un délai à une activité
     * @param ecart               Le MenuItem qui permet d'assigner un écart de delai à une activité
     * @param jetons              Le MenuItem qui permet de modifier le nombre de jetons d'un guichet
     * @param pastel              Le MenuItem qui permet de choisir le style pastel
     * @param vanille             Le MenuItem qui permet de choisir le style vanille
     */
    private void initialiserImages(MenuItem sauvegarder, MenuItem charger, MenuItem quitter, MenuItem supprimer, MenuItem renommer,
                                   MenuItem desactiverSelection, MenuItem entree, MenuItem sortie, MenuItem delai,
                                   MenuItem ecart, MenuItem jetons, MenuItem lois, MenuItem nbClients,MenuItem pastel, MenuItem vanille) {

        // Sauvegarder
        Image imSauvegarder = new Image(getClass().getResourceAsStream("/images/save.png"), 30, 30, true, true);
        sauvegarder.setGraphic(new ImageView(imSauvegarder));

        // Charger
        Image imCharger = new Image(getClass().getResourceAsStream("/images/load.png"), 30, 30, true, true);
        charger.setGraphic(new ImageView(imCharger));

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

        // Lois
        Image imLois = new Image(getClass().getResourceAsStream("/images/courbe.png"), 30, 30, true, true);
        lois.setGraphic(new ImageView(imLois));

        // NBClients
        Image imNbCLient = new Image(getClass().getResourceAsStream("/images/clients.png"), 30, 30, true, true);
        nbClients.setGraphic(new ImageView(imNbCLient));

        // On ajoute les items aux menus
        this.fichier.getItems().addAll(sauvegarder, charger, quitter);
        this.edition.getItems().addAll(supprimer, renommer, desactiverSelection);
        this.menuMonde.getItems().addAll(entree, sortie);
        this.parametres.getItems().addAll(delai, ecart, jetons,lois, nbClients);
        this.style.getItems().addAll(pastel, vanille);
    }

    /**
     * Méthode qui initialise les évènements liés aux items du menu fichier
     *
     * @param sauvegarder         Le MenuItem qui permet de sauvegarder une configuration du monde
     * @param charger             Le MenuItem qui permet de charger une configuration du monde
     * @param quitter             Le MenuItem qui permet de quitter le jeu
     * @param supprimer           Le MenuItem qui permet de recommencer la partie
     * @param renommer            Le MenuItem qui permet de renommer une activité
     * @param desactiverSelection Le MenuItem qui permet de désactiver la sélection
     * @param entree              Le MenuItem qui permet de désigner les activités sélectionnées comme entrées du monde
     * @param sortie              Le MenuItem qui permet de désigner les activités sélectionnées comme sorties du monde
     * @param delai               Le MenuItem qui permet d'assigner un délai à une activité
     * @param ecart               Le MenuItem qui permet d'assigner un écart de delai à une activité
     * @param jetons              Le MenuItem qui permet de modifier le nombre de jetons d'un guichet
     */
    private void initialiserEvenements(MenuItem sauvegarder, MenuItem charger, MenuItem quitter, MenuItem supprimer, MenuItem renommer,
                                       MenuItem desactiverSelection, MenuItem entree, MenuItem sortie, MenuItem delai,
                                       MenuItem ecart, MenuItem jetons, MenuItem lois, MenuItem nbClients,MenuItem pastel, MenuItem vanille){

        //rajouter les entrée peuvent être expo, griser si autre chose que entrée
        //sinon loi uni ou gauss selon choix.

        sauvegarder.setOnAction(e -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Sauvegarder le monde");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier JSON", "*.json"));   // On affiche uniquement les .json
                fileChooser.setInitialFileName("monde.json");

                File dossier = new File("sauvegardes"); // dossier ouvert par défaut (s'il existe)
                if (dossier.exists()) {
                    fileChooser.setInitialDirectory(dossier);
                }


                File fichier = fileChooser.showSaveDialog(this.getScene().getWindow());

                if (fichier != null) {  // En cas d'annulation de la save
                    GestionnaireSauvegarde.sauvegarderMonde(this.monde, fichier.getAbsolutePath());
                    afficherConfirmationSauvegarde();
                }
            }
            catch(IOException exception) {
                afficherErreurSauvegarde(exception.getMessage());
            }
        });

        charger.setOnAction(e -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Importer un monde");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier JSON", "*.json"));   // On affiche uniquement les .json

                File dossier = new File("sauvegardes");
                if (dossier.exists()) {
                    fileChooser.setInitialDirectory(dossier);
                }

                File fichier = fileChooser.showOpenDialog(this.getScene().getWindow());

                if(fichier != null) {
                    MondeIG mondeCharge = GestionnaireSauvegarde.chargerMonde(fichier.getAbsolutePath());
                    this.monde.remplacerMonde(mondeCharge);
                }
            }
            catch (IOException exception) {
                afficherErreurChargement(exception.getMessage());
            }
        });

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

        lois.setOnAction(e -> {
            try {
                String loiChoisie = dialogueChoisirLoi();
                double param = -1;

                if (loiChoisie != null) {
                    if (loiChoisie.equals("exponentielle")) {
                        Double lambda = dialogueLambda();
                        if (lambda != null) {
                            param = lambda;
                        } else {
                            return;
                        }
                    }
                    this.monde.changerLois(loiChoisie, param);
                }
            } catch (TwiskMenuException exception) {
                afficherErreur(exception.getMessage());
            }
        });

        nbClients.setOnAction(e -> {
            try {
                this.monde.setnbClients(dialogueNbClients());
            } catch (TwiskMenuException exception) {
                afficherErreur(exception.getMessage());
            }
        });
    }

    private int dialogueNbClients() {
        TextInputDialog dialog = new TextInputDialog("6");
        dialog.setTitle("Nombre de clients");
        dialog.setHeaderText("Définir le nombre de clients");
        dialog.setContentText("Entrez le nombre de clients :");

        Optional<String> choix = dialog.showAndWait();

        if (choix.isPresent()) {
            String saisie = choix.get().trim();
            if (saisie.isEmpty()) {
                throw new TwiskMenuException("Aucune valeur saisie");
            }

            try {
                int val = Integer.parseInt(saisie);
                if (val <= 0) {
                    throw new TwiskMenuException("Le nombre de clients doit être un entier positif");
                }
                return val;
            } catch (NumberFormatException exception) {
                throw new TwiskMenuException("Le nombre de clients doit être un entier positif");
            }
        } else {
            throw new TwiskMenuException("Aucune saisie");
        }
    }

    private Double dialogueLambda() throws TwiskMenuException {
        TextInputDialog dialog = new TextInputDialog("1.0");
        dialog.setTitle("Paramètre λ");
        dialog.setHeaderText("Définir λ pour la loi exponentielle");
        dialog.setContentText("Entrez une valeur de λ (entre 0.1 et 5) :");

        Optional<String> choix = dialog.showAndWait();

        if (choix.isPresent()) {
            String saisie = choix.get().trim();
            if (saisie.isEmpty()) {
                throw new TwiskMenuException("Aucune valeur saisie");
            }

            try {
                double lambda = Double.parseDouble(saisie);
                if (lambda < 0.1 || lambda > 5.0) {
                    throw new TwiskMenuException("λ doit être compris entre 0.1 et 5");
                }
                return lambda;
            } catch (NumberFormatException e) {
                throw new TwiskMenuException("λ doit être un nombre décimal");
            }
        } else {
            return null; // utilisateur a annulé
        }
    }

    /**
     * Méthode menu dialogue qui permet de choisir le type de loi pour une étape
     *
     * @return Le type de loi choisi (String) ou null si annulé
     */
    private String dialogueChoisirLoi() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("gaussienne",
                "gaussienne", "uniforme", "exponentielle");
        dialog.setTitle("Choix de la loi");
        dialog.setHeaderText("Choisir le type de loi");
        dialog.setContentText("Sélectionnez le type de loi pour l'étape :");

        Optional<String> choix = dialog.showAndWait();
        return choix.orElse(null);
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
            this.lois.setDisable(true);
        } else {
            this.delai.setDisable(false);
            this.ecart.setDisable(false);
            this.renommer.setDisable(false);
            this.jetons.setDisable(false);
            this.lois.setDisable(false);
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
     * Méthode qui permet de supprimer les fichiers temporaires dans le dossier tmp
     * @throws IOException
     */
    private void supprimerFichierTemp() throws IOException {
        try {
            File index = new File("/tmp/twisk/");
                String[] entries = index.list();
                if(entries != null) {
                    for (String s : entries) {
                        File currentFile = new File(index.getPath(), s);
                        currentFile.delete();
                    }
                }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode pour confirmer à l'utilisateur qu'il a bien sauvegardé
     */
    private void afficherConfirmationSauvegarde() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sauvegarde");
        alert.setHeaderText(null);
        alert.setContentText("Sauvegarde du monde réussie !");
        alert.showAndWait();
    }

    /**
     * Méthode qui affiche une erreur en cas d'échec de la sauvegarde
     * @param message Le message d'erreur
     */
    private void afficherErreurSauvegarde(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Echec sauvegarde");
        alert.setHeaderText(null);
        alert.setContentText(message);

        PauseTransition pause = new PauseTransition(seconds(3));
        pause.setOnFinished(event -> alert.close());
        pause.play();
        alert.showAndWait();
    }

    /**
     * Méthode qui affiche une erreur en cas d'échec du chargement
     * @param message Le message d'erreur
     */
    private void afficherErreurChargement(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Échec du chargement");
        alert.setHeaderText(null);
        alert.setContentText(message);

        PauseTransition pause = new PauseTransition(seconds(3));
        pause.setOnFinished(event -> alert.close());
        pause.play();

        alert.showAndWait();
    }

    /**
     * Méthode qui met à jour l'affichage du menu dans le monde
     */
    public void reagir() {
        updateMenuItems();
    }
}