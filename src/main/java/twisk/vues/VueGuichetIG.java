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
     * Méthode qui permet de récupérer l'EtapeIG associée au guichet
     * @return L'EtapeIG du guichet
     */
    public GuichetIG getEtape() {
        return this.guichet;
    }

    /**
     * Méthode qui affiche tous les clients dans les cases du guichet en fonction de leur position et du sens de circulation
     * @param clients La liste des clients à afficher dans ce guichet
     */
    public void placerClientsGuichet(ArrayList<ClientIG> clients) {
        viderCases();   // Pour remplacer les clients à chaque MAJ / éviter la duplication

        int nbCases = this.casesClients.size();

        for (int i = 0; i < clients.size() && i < nbCases; i++) {  // On ne doit dépasser ni le nombre de jetons du guichet, ni le nombre de cases dispo
            VueClientIG vueClient = new VueClientIG(clients.get(i));
            ajouterClientSelonSens(i, vueClient);
        }
    }

    /**
     * Vide toutes les cases du guichet
     */
    private void viderCases() {
        for (StackPane casePane : this.casesClients) {
            casePane.getChildren().clear();
        }
    }

    /**
     * Méthode qui ajoute un client dans une case du guichet
     * @param position La position du client
     * @param client Le client à ajouter
     */
    private void ajouterClientCase(int position, VueClientIG client) {
        assert(position >= 0 && position < this.casesClients.size());
        StackPane casePane = this.casesClients.get(position);
        casePane.getChildren().clear(); // Au cas où un client est toujours dans la case
        casePane.getChildren().add(client);
    }

    /**
     * Méthode qui gère l'ordre de l'affichage des cliens selon le sens (on calcule l'index -> le placement)
     * @param position La position du client
     * @param client Le client à ajouter
     */
    private void ajouterClientSelonSens(int position, VueClientIG client) {
        int index = position;   // De base 0 donc premier client à gauche

        if(this.guichet.getSens() == GuichetIG.Sens.GAUCHE_DROITE) {    // Si sens gauche_droite -> position 0 = gauche du guichet et inversement si sens droite_gauche
            index = this.casesClients.size() - 1 - position;
        }

        this.ajouterClientCase(index, client);
    }

    /**
     * Méthode qui initialise les cases régulières à l'intérieur de la HBox du guichet
     */
    private void initialiserCases() {
        this.hboxClients.setSpacing(5);
        int nbCases = 6;    // Arbitraire

        for(int i = 0; i < nbCases; i++) {
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