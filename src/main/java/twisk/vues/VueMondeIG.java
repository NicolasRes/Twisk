package twisk.vues;

import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import twisk.mondeIG.*;

import java.util.HashMap;

/**
 * Classe qui affiche toutes les activités répertoriées dans MondeIG
 */
public class VueMondeIG extends Pane implements Observateur {
    private MondeIG monde;
    private HashMap<EtapeIG, VueEtapeIG> vuesEtapes;


    /**
     * Constructeur de la classe VueMondeIG
     * @param monde Le monde dans lequel afficher les activités
     */
    public VueMondeIG(MondeIG monde) {
        assert(monde!=null);

        this.monde = monde;
        this.monde.ajouterObservateur(this);
        this.vuesEtapes = new HashMap<>();

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
                VueEtapeIG vueAct = new VueActiviteIG(this.monde, e);
                this.getChildren().add(vueAct);
                vueAct.relocate(e.getPosX(), e.getPosY());
                this.vuesEtapes.put(e, vueAct); // On associe activités et vues
            }
            else if (e.getType().equals("Guichet")) {
                VueEtapeIG vueGui = new VueGuichetIG(this.monde, e);
                this.getChildren().add(vueGui);
                vueGui.relocate(e.getPosX(), e.getPosY());
                this.vuesEtapes.put(e, vueGui); // On associe guichets et vues
            }

            afficherPointDeControle(e);
        }
    }

    /**
     * Méthode qui affiche les clients dans les activités du monde
     */
    private void afficherClientsActivite() {
        for(ClientIG c : this.monde.getClientsIG()) {
            EtapeIG etapeIG = c.getEtape();
            if(etapeIG.getType().equals("Activite")) {  // On récupère les vues activités
                VueActiviteIG vueActiviteIG = (VueActiviteIG) this.vuesEtapes.get(etapeIG);
                vueActiviteIG.ajouterClientHBox(c);
            }
        }
    }

    /**
     * Méthode qui affiche les clients dans les guichets du monde
     */
    private void afficherClientsGuichets() {
        for (EtapeIG e : this.monde) {
            if (e.getType().equals("Guichet")) {    // On récupère les vues guichets
                VueGuichetIG vueGuichetIG = (VueGuichetIG) this.vuesEtapes.get(e);  // On récupère les vues qu'on a associé aux guichets dans afficherEtapes
                GuichetIG guichetIG = (GuichetIG) e;
                vueGuichetIG.placerClientsGuichet(guichetIG.getClients());  // On positionne les clients dans les cases des guichets
            }
        }
    }

    /**
     * Méthode qui affiche les clients dans tous les types d'étapes
     */
    private void afficherClients() {
        afficherClientsActivite();
        afficherClientsGuichets();
    }

    /**
     * Méthode qui met à jour le dessin des activités dans le monde
     */
    @Override
    public void reagir() {
        this.getChildren().clear();
        afficherArcs();
        afficherEtapes();
        afficherClients();
    }
}