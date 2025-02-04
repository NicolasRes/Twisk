package twisk;

import twisk.monde.*;
import twisk.simulation.Simulation;

public class ClientTwisk {

    public static void main(String[] args) {

        Monde monde = new Monde() ;

        Guichet guichet = new Guichet("ticket", 2) ;
        Activite act1 = new ActiviteRestreinte("toboggan", 2, 1) ;

        guichet.ajouterSuccesseur(act1);

        monde.ajouter(act1) ;
        monde.ajouter(guichet) ;

        monde.aCommeEntree(guichet);
        monde.aCommeSortie(act1) ;

        Simulation simulation = new Simulation();
        simulation.simuler(monde);
    }
}
