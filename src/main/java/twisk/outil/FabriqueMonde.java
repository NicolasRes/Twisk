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

    public static Monde fabriqueMondeBifurc() {

        Monde monde = new Monde();
        Activite act1 = new Activite("étape 1 tobogan",3,1);
        Guichet g1 = new Guichet("ètape 2 g1",1);
        Activite act2 = new ActiviteRestreinte("etape 3 piscine",3,2);
        Guichet g2 = new Guichet("ETAPE 4 g2",6);
        Activite act3 = new ActiviteRestreinte("A3",2,1);
        Activite act4 = new Activite("etapebifurc1",4,2);
        Activite act5 = new Activite("etapebifurc2",3,2);

        act1.ajouterSuccesseur(g1);
        g1.ajouterSuccesseur(act2);
        act2.ajouterSuccesseur(g2);
        act2.ajouterSuccesseur(act5);
        g2.ajouterSuccesseur(act3);
        act3.ajouterSuccesseur(act4);
        act4.ajouterSuccesseur(act5);

        monde.ajouter(act1,g1,act2, g2, act3,act4,act5);

        monde.aCommeEntree(act1);
        monde.aCommeEntree(act4);
        monde.aCommeSortie(act5);

        return monde;

    }

    public static Monde mondeBifurc() {
        Monde monde = new Monde();
        Activite act1 = new Activite("étape 1 tobogan",8,3);
        Guichet g1 = new Guichet("ètape 2 g1",3);
        Activite act2 = new ActiviteRestreinte("etape 3 piscine",4,2);
        Guichet g2 = new Guichet("ETAPE 4 g2",6);
        Activite act3 = new ActiviteRestreinte("A3",2,1);
        Activite act4 = new Activite("etapebifuc1", 4, 2);
        Activite act5 = new Activite("etapebifuc2", 3, 2);

        act1.ajouterSuccesseur(g1);
        g1.ajouterSuccesseur(act2);
        act2.ajouterSuccesseur(act5);
        act2.ajouterSuccesseur(g2);
        g2.ajouterSuccesseur(act3);
        act3.ajouterSuccesseur(act4);
        act4.ajouterSuccesseur(act5);

        monde.ajouter(act1,g1,act2, g2, act3, act4, act5);

        monde.aCommeEntree(act1);
        monde.aCommeEntree(act4);
        monde.aCommeSortie(act5);

        return monde;
    }
}