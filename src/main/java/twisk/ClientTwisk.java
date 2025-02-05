package twisk;

import twisk.monde.*;
import twisk.simulation.Simulation;

import java.util.Iterator;

public class ClientTwisk {

    public static void main(String[] args) {
        Monde monde = new Monde();

        Activite act1 = new Activite("1", 2, 1) ;
        Activite act2 = new Activite("2", 2, 1) ;
        Activite act3 = new Activite("3", 2, 1) ;
        Activite act4 = new Activite("4", 2, 1);
        Activite act5 = new Activite("5", 2, 1);

        Guichet guich = new Guichet("ticket", 2) ;

        act2.ajouterSuccesseur(act3);
        act4.ajouterSuccesseur(guich);
        guich.ajouterSuccesseur(act5);

        monde.aCommeEntree(act1, act2, act4);
        monde.aCommeSortie(act1, act3, act5);

        monde.ajouter(act1, act2, act3, act4, act5);
        monde.ajouter(guich);

        Simulation sim = new Simulation();
        sim.simuler(monde);
    }
}