package twisk.outils;

import twisk.exceptions.TwiskArcException;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;

/**
 * Classe fabrique de mondes
 */
public class FabriqueMondeIG {

    /**
     * Monde basique avec deux activités et 2 arcs
     * @return Un monde basique
     */
    public static MondeIG FabriqueMondeBase() {
        MondeIG monde = new MondeIG();

        monde.ajouter("Activité");
        monde.ajouter("Activité");

        PointDeControleIG pt1 = new PointDeControleIG(1, 1, monde.getEtapes().get(0));
        PointDeControleIG pt2 = new PointDeControleIG(2, 2, monde.getEtapes().get(0));
        PointDeControleIG pt3 = new PointDeControleIG(3, 3, monde.getEtapes().get(1));
        PointDeControleIG pt4 = new PointDeControleIG(4, 4, monde.getEtapes().get(2));

        try {
            monde.ajouter(pt1, pt2);
            monde.ajouter(pt3, pt4);
        }
        catch (TwiskArcException e) {
            e.printStackTrace();
        }

        return monde;
    }
}