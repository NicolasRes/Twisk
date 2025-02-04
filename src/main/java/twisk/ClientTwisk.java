package twisk;

import twisk.monde.*;
import twisk.simulation.Simulation;

public class ClientTwisk {

    public static void main(String[] args) {

        Monde monde = new Monde() ;

        Guichet guichet = new Guichet("guiché", 2) ;
        Activite act1 = new ActiviteRestreinte("toboggan", 2, 1) ;

        Etape etape1 = new Activite("musee") ;
        Etape etape2 = new Activite("boutique") ;

        etape1.ajouterSuccesseur(etape2) ;
        etape2.ajouterSuccesseur(guichet) ;
        guichet.ajouterSuccesseur(act1);

        monde.ajouter(etape1, etape2) ;
        monde.ajouter(guichet) ;
        monde.ajouter(act1) ;

        monde.aCommeEntree(etape1);
        monde.aCommeSortie(act1) ;

        Simulation simulation = new Simulation();
        simulation.simuler(monde);
    }
}
