package twisk.outils;

import twisk.monde.Activite;
import twisk.monde.Guichet;
import twisk.monde.Monde;

/**
 * Classe qui fabrique différents types de mondes
 */
public class FabriqueMonde {

    /**
     * Méthode qui fabrique un monde basique avec deux activités et un guichet
     * @return Un monde basique
     */
    public static Monde fabriqueMondeBasique() {
        Monde monde = new Monde();
        Activite act1 = new Activite("A1");
        Guichet g1 = new Guichet("G1");
        Activite act2 = new Activite("A2");

        act1.ajouterSuccesseur(g1);
        g1.ajouterSuccesseur(act2);

        monde.aCommeEntree(act1);
        monde.aCommeSortie(act2);

        return monde;
    }
}
