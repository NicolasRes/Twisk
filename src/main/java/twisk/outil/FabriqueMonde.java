package twisk.outil;

import twisk.monde.Activite;
import twisk.monde.ActiviteRestreinte;
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
        Activite act1 = new Activite("étape 1 tobogan",8,3);
        Guichet g1 = new Guichet("ètape 2 g1",3);
        Activite act2 = new ActiviteRestreinte("etape 3 piscine",4,2);
        Guichet g2 = new Guichet("ETAPE 4 g2",6);
        Activite act3 = new ActiviteRestreinte("A3",2,1);

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
