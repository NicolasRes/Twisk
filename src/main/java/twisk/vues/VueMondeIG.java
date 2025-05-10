package twisk.vues;

import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import twisk.mondeIG.*;

/**
 * Classe qui affiche toutes les activités répertoriées dans MondeIG
 */
public class VueMondeIG extends Pane implements Observateur {
    private MondeIG monde;

    /**
     * Constructeur de la classe VueMondeIG
     * @param monde Le monde dans lequel afficher les activités
     */
    public VueMondeIG(MondeIG monde) {
        assert(monde!=null);

        this.monde = monde;
        this.monde.ajouterObservateur(this);

        this.getStyleClass().add("Pane");

        reagir();

        // Destination qui accepte le drag'n drop
        this.setOnDragOver(dragEvent -> {
            final Dragboard dragboard = dragEvent.getDragboard();
            if(dragEvent.getGestureSource() != this && dragboard.hasString()) { // On identifie la source du drag
                dragEvent.acceptTransferModes(TransferMode.MOVE);
            }
            dragEvent.consume();
        });

        this.setOnDragDropped(dragEvent -> {
            boolean success = false;
            try {
                final Dragboard dragboard = dragEvent.getDragboard();
                String identifiant = dragboard.getString();
                double x = dragEvent.getX();
                double y = dragEvent.getY();

                this.monde.deplacerEtape(identifiant, x, y);
                success = true;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            finally {
                dragEvent.setDropCompleted(success);
                dragEvent.consume();
            }
        });
    }

    /**
     * Méthode qui affiche les points de contrôle d'une étape
     * @param etape L'étape dont on doit afficher les points de contrôle
     */
    private void afficherPointDeControle(EtapeIG etape) {
        for (PointDeControleIG p : etape) {
            VuePointDeControleIG vPointControle = new VuePointDeControleIG(p, this.monde);
            this.getChildren().add(vPointControle);
            vPointControle.relocate(p.getX(), p.getY());
        }
    }

    /**
     * Méthode qui affiche les arcs du monde
     */
    private void afficherArcs() {
        for (ArcIG arc : this.monde.getArcsIterable()) {
            VueArcIG vueArc = new VueArcIG(arc, this.monde);
            this.getChildren().add(vueArc);
        }
    }

    /**
     * Méthode qui affiche les étapes du monde (avec ses points de contrôle)
     */
    private void afficherEtapes() {
        for(EtapeIG e : this.monde) {
            if(e.getType().equals("Activite")) {
                VueEtapeIG act = new VueActiviteIG(this.monde, e);
                this.getChildren().add(act);
                act.relocate(e.getPosX(), e.getPosY());
            }
            else if (e.getType().equals("Guichet")) {
                VueEtapeIG gui = new VueGuichetIG(this.monde, e);
                this.getChildren().add(gui);
                gui.relocate(e.getPosX(), e.getPosY());
            }

            afficherPointDeControle(e);
        }
    }

    /**
     * Méthode qui affiche les clients dans les étapes du monde
     */
    private void afficherClients() {
        for(ClientIG c : this.monde.getClientsIG()) {
            System.out.println("→ Affichage des cercles...");
            System.out.println("    Ajout du client " + c.getNumero() + " aux coordonnées : " + c.getX() + "," + c.getY());
            Circle cercle = new Circle(5);
            cercle.getStyleClass().add("client");
            cercle.setCenterX(c.getX());
            cercle.setCenterY(c.getY());
            cercle.setId("client" + c.getNumero());  // pour debugger si besoin
            this.getChildren().add(cercle);
        }
    }

    /**
     * Méthode qui met à jour le dessin des activités dans le monde
     */
    @Override
    public void reagir() {
        this.getChildren().clear();

        afficherArcs();
        afficherEtapes();
        System.out.println("Nb de clientsIG dans monde : " + monde.getClientsIG().size());
        afficherClients();
    }
}