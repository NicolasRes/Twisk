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
        Activite act1 = new Activite("A1",8,3);
        Guichet g1 = new Guichet("G1",3);
        Activite act2 = new Activite("A2",4,2);
        Guichet g2 = new Guichet("G2",6);
        Activite act3 = new Activite("A3",2,1);

        act1.ajouterSuccesseur(g1);
        g1.ajouterSuccesseur(act2);
        act2.ajouterSuccesseur(g2);
        g2.ajouterSuccesseur(act3);

        monde.ajouter(act1,g1,act2, g2, act3);

        monde.aCommeEntree(act1);
        monde.aCommeSortie(act3);

        return monde;
    }
}
